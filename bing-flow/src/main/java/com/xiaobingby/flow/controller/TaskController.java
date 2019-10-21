package com.xiaobingby.flow.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaobingby.common.security.util.SecurityUtils;
import com.xiaobingby.flow.utils.TaskToBeanUtils;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/21
 * Time: 12:41
 * Describe:
 */
@RestController
@RequestMapping("/tasks")
public class TaskController extends BaseController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;


    /**
     * 添加报销
     * @param money     报销金额
     * @param descption 描述
     */
    @RequestMapping(value = "add")
    public String addExpense(String money,String descption) {
        String userId = SecurityUtils.getUserDetails().getId().toString();
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        identityService.setAuthenticatedUserId(userId);
        map.put("applyUser", "1996527");
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }


    /**
     * 查询当前用户所有的待办任务
     * 待办任务 可以分为两种
     * 1：已经签收  已经签收 说明了任务的受理人就是当前的用户  接下来就需要当前用户去执行同意或者驳回请求
     * 2：未签收    未签收说明了当前任务还没有受理人 ，如果你能查询到，说明了你有权限成为该任务的受理人
     * 我是这么理解的
     */
    @RequestMapping(value = "/todo")
    @ApiOperation(value = "查询待办任务",notes = "查询待办任务",produces = "application/json" )
    public Object list(Map<String,String> mapPamarm)  {
        String userId = "123";//SecurityUtils.getUserDetails().getId().toString();
        TaskQuery taskQuery = taskService.createTaskQuery();
        //流程定义id
        if (StrUtil.isNotEmpty(mapPamarm.get("processInstanceId"))) {
            taskQuery.processInstanceId(mapPamarm.get("processInstanceId"));
        }
        //业务键
        if (StrUtil.isNotEmpty(mapPamarm.get("processInstanceBusinessKey"))) {
            taskQuery.processInstanceBusinessKeyLike(StrUtil.wrapIfMissing(mapPamarm.get("processInstanceBusinessKey"),"%","%"));
        }
        //流程定义key
        if (StrUtil.isNotEmpty(mapPamarm.get("processDefinitionKey"))) {
            taskQuery.processDefinitionKeyLike(StrUtil.wrapIfMissing(mapPamarm.get("processDefinitionKey"),"%","%"));
        }
        if (StrUtil.isNotEmpty(mapPamarm.get("processDefinitionId"))) {
            taskQuery.processDefinitionId(mapPamarm.get("processDefinitionId"));
        }
        if (StrUtil.isNotEmpty(mapPamarm.get("processDefinitionName"))) {
            taskQuery.processDefinitionNameLike(StrUtil.wrapIfMissing(mapPamarm.get("processDefinitionName"),"%","%"));
        }
        //任务到期时间
        if (StrUtil.isNotEmpty(mapPamarm.get("dueDateAfter"))) {
            taskQuery.taskDueAfter(DateUtil.parseDateTime(mapPamarm.get("dueDateAfter")));
        }
        if (StrUtil.isNotEmpty(mapPamarm.get("dueDateBefore"))) {
            taskQuery.taskDueBefore(DateUtil.parseDateTime(mapPamarm.get("dueDateBefore")));
        }
        //任务开始时间
        if (StrUtil.isNotEmpty(mapPamarm.get("taskCreatedBefore"))) {
            taskQuery.taskCreatedBefore(DateUtil.parseDateTime(mapPamarm.get("taskCreatedBefore")));
        }
        if (StrUtil.isNotEmpty(mapPamarm.get("taskCreatedAfter"))) {
            taskQuery.taskCreatedAfter(DateUtil.parseDateTime(mapPamarm.get("taskCreatedAfter")));
        }

        if (StrUtil.isNotEmpty(mapPamarm.get("tenantId"))) {
            taskQuery.taskTenantId(mapPamarm.get("tenantId"));
        }
        //任务状态：激活 挂起
        if (StrUtil.isNotEmpty(mapPamarm.get("suspended"))) {
            boolean isSuspended = Boolean.getBoolean(mapPamarm.get("suspended"));
            if (isSuspended) {
                taskQuery.suspended();
            } else {
                taskQuery.active();
            }
        }
        List<Task> tasks = taskQuery.taskCandidateOrAssigned(userId).orderByTaskCreateTime().desc().list();
        com.xiaobingby.flow.dto.Task[] tasks1 = TaskToBeanUtils.ListTaskTranste((org.flowable.task.api.Task[])tasks.toArray());
        return tasks1;
    }


    @GetMapping(value = "/done", name = "已办任务查询")
    public Object getTasks(@RequestParam Map<String, String> requestParams) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        TaskQuery taskQuery = taskService.createTaskQuery();
        String userId="123";
        String assignee = taskService.createTaskQuery().singleResult().getAssignee();

        List<HistoricTaskInstance> list = query.finished().or().taskAssignee(userId).taskOwner(userId).list();
        List<Task> taskList = taskQuery.taskAssignee(userId).taskOwner(userId).list();

        return list;
    }

    @GetMapping(value = "/tasks/{taskId}", name = "根据ID任务查询")
    public Object getTaskById(@PathVariable("taskId") String taskId) {
        HistoricTaskInstance historicTaskInstance = getHistoricTaskFromRequest(taskId);
        Task task = null;
        if (historicTaskInstance.getEndTime() == null) {
            task = getTaskFromRequest(taskId);
        }
        return TaskToBeanUtils.ListTaskTranste(task);
    }




    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @RequestMapping(value = "apply")
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "processed ok!";
    }


    /**
     * 拒绝
     */
    @RequestMapping(value = "reject")
    public String reject(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return "reject";
    }






}
