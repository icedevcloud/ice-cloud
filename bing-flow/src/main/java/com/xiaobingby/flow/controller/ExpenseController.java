package com.xiaobingby.flow.controller;

import com.xiaobingby.common.security.util.SecurityUtils;
import com.xiaobingby.flow.utils.TaskToBeanUtils;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
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
public class ExpenseController extends BaseController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    protected FormService formService;

    @Autowired
    protected HistoryService historyService;


    /**
     * 添加报销
     *
     * @param userId    用户Id
     * @param money     报销金额
     * @param descption 描述
     */
    @RequestMapping(value = "add")
    public String addExpense(String userId, Integer money, String descption) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }


    /**
     * 查询当前用户所有的任务
     */
    @RequestMapping(value = "/list")
    @ApiOperation(value = "查询所有任务",notes = "查询所有任务",produces = "application/json" )
    public Object list(Map<String,String> mapPamarm) throws Exception {
        String userId = SecurityUtils.getUserDetails().getId().toString();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        com.xiaobingby.flow.dto.Task[] tasks1 = TaskToBeanUtils.ListTaskTranste((org.flowable.task.api.Task[])tasks.toArray());
        return tasks1;
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
