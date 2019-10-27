package com.xiaobingby.flow;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.xiaobingby.flow.dto.SysWorkFlow;
import com.xiaobingby.flow.dto.SysWorkflowStep;
import com.xiaobingby.flow.service.ISysWorkFlowService;
import org.apache.commons.io.FileUtils;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/10/4
 * Time: 13:52
 * Describe:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class flowAbletest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ISysWorkFlowService sysWorkFlowService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void test1(){
        List<Task> list = taskService.createTaskQuery().taskAssignee("123").active().list();
        System.out.println(list);

        //anonymousUser
        String startUserId = historyService.createHistoricProcessInstanceQuery().processInstanceId("3f67a272-e671-11e9-88c8-0250f2000002").singleResult().getStartUserId();//获取发起人
        List<SysWorkFlow> listSys = sysWorkFlowService.list();
        System.out.println("12121");

        //sysWorkFlowService.saveBatch()
    }
    @Test
    public void test2(){
        SysWorkFlow sysWorkFlow =new SysWorkFlow();
        sysWorkFlow.setContent("测试");
        List<SysWorkflowStep > sysWorkflowSteps=new ArrayList<>();
        SysWorkflowStep sysWorkflowStep3=new SysWorkflowStep();
        sysWorkflowStep3.setId(2L);
        sysWorkflowStep3.setName("申请人申请");
        sysWorkflowStep3.setRoleNo("2");
        sysWorkflowStep3.setExamineType(2);
        SysWorkflowStep sysWorkflowStep=new SysWorkflowStep();
        sysWorkflowStep.setId(1L);
        sysWorkflowStep.setRoleNo("1");
        sysWorkflowStep.setName("团队成员审批");
        sysWorkflowStep.setExamineType(1);
        sysWorkflowStep.setCompletionCondition("${nrOfCompletedInstances +1 >= nrOfInstances}");
        SysWorkflowStep sysWorkflowStep2=new SysWorkflowStep();
        sysWorkflowStep2.setId(2L);
        sysWorkflowStep2.setRoleNo("2");
        sysWorkflowStep2.setName("经理审批");
        sysWorkflowStep2.setExamineType(2);
        sysWorkflowSteps.add(sysWorkflowStep3);
        sysWorkflowSteps.add(sysWorkflowStep);
        sysWorkflowSteps.add(sysWorkflowStep2);
        sysWorkFlowService.dynamicFlow(sysWorkFlow,sysWorkflowSteps);
    }


    @Test
    public void test3() throws IOException {
        //processes/daling.bpmn20.xml
        Map vars =new HashMap();
        String[]v={"shareniu1","shareniu2","shareniu3","shareniu4"};

        vars.put("assigneeList",  Arrays.asList(v));
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("daling",vars);
        //System.out.println(processInstance.getId());
        //b473f966-f222-11e9-94e1-aaaaaa2ca501
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("f7c78a14-f704-11e9-b1a8-0250f2000002", vars);
        System.out.println("processInstance.getId():"+processInstance.getId()+",getProcessDefinitionId:"+processInstance.getProcessDefinitionId());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("taskName1:" + task.getName() + "|assignee:" + task.getAssignee());
        Map<String, Object> leaderTwoAudit12 = new HashMap<String, Object>();
        leaderTwoAudit12.put("flag", "true");

        taskService.complete(task.getId(),leaderTwoAudit12);

        Map<String, Object> leaderOneAudit = new HashMap<String, Object>();
        Scanner scanner = new Scanner(System.in);
        leaderOneAudit.put("flag", "true");
        System.out.println("张三的审批意见为：" + ("true".equals("yes") ? "同意" : "不同意"));
        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("shareniu1").singleResult();
        taskService.complete(task.getId(), leaderOneAudit);
        System.out.println("taskName2:" + task.getName() + "|assignee:" + task.getAssignee());

        System.out.println("--------------------------------------------");
        Map<String, Object> leaderTwoAudit = new HashMap<String, Object>();
        leaderTwoAudit.put("flag", "true");
        System.out.println("李四的审批意见为：" + ("true".equals("yes") ? "同意" : "不同意"));

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("shareniu2").singleResult();
        taskService.complete(task.getId(), leaderTwoAudit);
        System.out.println("taskName3:" + task.getName() + "|assignee:" + task.getAssignee());

        System.out.println("--------------------------------------------");
        Map<String, Object> leaderThreeAudit = new HashMap<String, Object>();
        leaderThreeAudit.put("flag", "true");
        System.out.println("李四的审批意见为：" + ("true".equals("yes") ? "同意" : "不同意"));
        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("shareniu3").singleResult();
        taskService.complete(task.getId(), leaderThreeAudit);
        System.out.println("taskName4:" + task.getName() + "|assignee:" + task.getAssignee());

            task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("shareniu4").singleResult();
            taskService.complete(task.getId());
            System.out.println("taskName5:" + task.getName() + "|assignee:" + task.getAssignee());
    }
    @Test
    public void test4(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId("f78e2052-f7a2-11e9-bfa8-0250f2000002").finished().list();
        System.out.println(list);

    }
}
