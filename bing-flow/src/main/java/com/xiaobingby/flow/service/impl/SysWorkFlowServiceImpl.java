package com.xiaobingby.flow.service.impl;

import com.xiaobingby.flow.enums.Contants;
import com.xiaobingby.flow.exception.WrokFlowException;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.CollectionHandler;
import com.google.common.collect.Maps;
import org.flowable.bpmn.model.MultiInstanceLoopCharacteristics;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.xiaobingby.common.core.api.R;
import com.xiaobingby.flow.dto.SysWorkFlow;
import com.xiaobingby.flow.dto.SysWorkflowStep;
import com.xiaobingby.flow.mapper.SysWorkFlowMapper;
import com.xiaobingby.flow.service.ISysWorkFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.api.feign.IFlowFeign;
import com.xiaobingby.upms.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.*;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiex
 * @since 2019-10-06
 */
@Service
@Slf4j
public class SysWorkFlowServiceImpl extends ServiceImpl<SysWorkFlowMapper, SysWorkFlow> implements ISysWorkFlowService {

    @Autowired(required = false)
    private SysWorkFlowMapper sysWorkFlowMapper;
    @Autowired(required = false)
    private IFlowFeign flowFeign;

    /**
     * 通过角色id，获取该角色下的所有用户
     *
     * @param roleId
     * @return
     */
    private List<String> userList(int roleId, int type) {
        List<String> collect = new ArrayList<>();
        if (type == 2) {
            collect.add("11111");
            collect.add("22222");
            collect.add("33333");
            collect.add("4444");
            //R<List<SysUser>> listR = flowFeign.roleIdByUsers((long) roleId);
            //collect = listR.getData().parallelStream().map(SysUser::getId).collect(Collectors.toList()).stream().map(i -> i.toString()).collect(Collectors.toList());
        } else if (type == 1) {
            collect.add(String.valueOf(roleId));
        }
        return collect;
    }

    /**
     * @param sysWorkFlow
     * @param sysWorkflowSteps
     */
    @Transactional
    @Override
    public void dynamicFlow(SysWorkFlow sysWorkFlow, List<SysWorkflowStep> sysWorkflowSteps) throws Exception {
        // 把数据存入表中
        String jsonString = JSON.toJSONString(sysWorkflowSteps);
        sysWorkFlow.setStep(jsonString);
        //sysWorkFlowMapper.insert(sysWorkFlow);
        //addFlowDeploymentByParallelGateway(sysWorkFlow, sysWorkflowSteps,"new");
        addFlowDeploymentBymultiInstanceLoop(sysWorkFlow, sysWorkflowSteps, "addFlowDeploymentBymultiInstanceLoop123");
    }

    /**
     * 使用multiInstanceLoopCharacteristics 完成会签
     *
     * @param workflow
     * @param stepList
     */
    @Transactional
    public void addFlowDeploymentBymultiInstanceLoop(SysWorkFlow workflow, List<SysWorkflowStep> stepList, String id) throws Exception {
        log.info("准备建立动态模型");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 1. 建立模型
        BpmnModel model = new BpmnModel();
        org.flowable.bpmn.model.Process process = new org.flowable.bpmn.model.Process();
        model.addProcess(process);
        process.setId(id);
        process.setName(workflow.getFlowName());
        process.setDocumentation(workflow.getContent());
        //添加流程
        //开始节点
        process.addFlowElement(createStartEvent(null,null));
        for (int i = 0; i < stepList.size(); i++) {
            SysWorkflowStep step = stepList.get(i);
            MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = null;
            String user = Contants.FLOWABLE_USERTASK_ASSIGNEE;

            if (i == 0) {
                process.addFlowElement(createUserTask("task" + i, step.getName(), null,Contants.FLOWABLE_INITIATOR_USERTASK_APPLICANT, null));
                //回退节点
                //process.addFlowElement(createUserTask("repulse" + i, "回退节点" + i, "${startUserId}"));
            } else {
                if (step.getExamineType() == 1) {
                    multiInstanceLoopCharacteristics = createMultiInstanceLoopCharacteristics(false, step.getLoopCardinality(), step.getCompletionCondition());
                }
                process.addFlowElement(createUserTask("task" + i, step.getName(), null,user, multiInstanceLoopCharacteristics));
                //回退节点
                if (step.getRejectType() == 2) {
                    process.addFlowElement(createUserTask("repulse" + i, "回退节点" + i, "${startUserId}",null,null));
                }
            }


        }
        //结束节点
        process.addFlowElement(createEndEvent());
        //连线
        for (int y = 0; y < stepList.size(); y++) {
            SysWorkflowStep step = stepList.get(y);
            //普通流转
            //第一个节点
            if (y == 0) {
                //开始节点和审核节点1
                process.addFlowElement(createSequenceFlow("startEvent", "task" + y, stepList.get(0).getName() + "到" + stepList.get(1).getName(), ""));
            } else {
                //普通
                process.addFlowElement(createSequenceFlow("task" + (y - 1), "task" + y, stepList.get(y - 1).getName() + "到" + stepList.get(y).getName(), "${flag=='true'}"));

            }
            //是否最后一个节点
            if (y == (stepList.size() - 1)) {
                //审核节点到结束节点
                process.addFlowElement(createSequenceFlow("task" + y, "endEvent", stepList.get(y).getName() + "到结束节点", "${flag=='true'}"));
            }
            //审核节点到回退节点
            if (y != 0) {

                if (step.getRejectType() == 1) {
                    process.addFlowElement(createSequenceFlow("task" + y, "endEvent", "审核不通过-结束", "${flag=='false'}"));
                } else {
                    process.addFlowElement(createSequenceFlow("task" + y, "repulse" + y, "审核不通过-打回" + y, "${flag=='false'}"));
                    process.addFlowElement(createSequenceFlow("repulse" + y, "task" + y, "回退节点到审核节点" + y, ""));
                }
            }

        }

        // 2. 生成的图形信息
        new BpmnAutoLayout(model).execute();

        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        //验证失败信息的封装ValidationError
        List<ValidationError> validate = defaultProcessValidator.validate(model);
        System.out.println(validate.size());
        for (ValidationError validationError : validate) {
            System.out.println(validationError.getProblem());
            System.out.println(validationError.isWarning());
        }
        InputStream input = null;
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

        byte[] convertToXML = bpmnXMLConverter.convertToXML(model);

        String bytes = new String(convertToXML);

        System.out.println(bytes);
        input = new ByteArrayInputStream(convertToXML);


        // 3. 部署流程
        Deployment deployment = processEngine.getRepositoryService().createDeployment().addBpmnModel(process.getId() + ".bpmn20.xml", model).name(process.getId() + "_deployment").deploy();

        // 4. 启动一个流程实例
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(process.getId());

        // 5. 获取流程任务
        //final List<Task> list = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
        try {
            // 6. 将流程图保存到本地文件
            InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
            FileUtils.copyInputStreamToFile(processDiagram, new File("/deployments/" + process.getId() + ".png"));

            // 7. 保存BPMN.xml到本地文件
            InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), process.getId() + ".bpmn20.xml");
            //List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deployment.getId());
            FileUtils.copyInputStreamToFile(processBpmn, new File("/deployments/" + process.getId() + ".bpmn20.xml"));
            //FileUtils.copyInputStreamToFile(input, new File("/deployments/" +"1234"+ process.getId() + ".bpmn20.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用并行网关完成会签
     *
     * @param
     * @param
     */
    /*private void addFlowDeploymentByParallelGateway(SysWorkFlow workflow, List<SysWorkflowStep> stepList, String id) {
        log.info("准备建立动态模型");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 1. 建立模型
        BpmnModel model = new BpmnModel();
        org.flowable.bpmn.model.Process process = new org.flowable.bpmn.model.Process();
        model.addProcess(process);
        process.setId(id);
        process.setName(workflow.getFlowName());
        process.setDocumentation(workflow.getContent());
        //添加流程
        //开始节点
        process.addFlowElement(createStartEvent(null));
        for (int i = 0; i < stepList.size(); i++) {
            SysWorkflowStep step = stepList.get(i);
            //判断是否会签
            if (step.getExamineType() == 1) {
                //会签
                //加入并行网关-分支
                process.addFlowElement(createParallelGateway("parallelGateway-fork" + i, "并行网关-分支" + i));
                //获取角色下所有用户
                List<String> userList = userList(Integer.parseInt(step.getRoleNo()), step.getStepExamineType());
                for (int u = 0; u < userList.size(); u++) {
                    //并行网关分支的审核节点
                    process.addFlowElement(createUserTask("userTask" + i + u, "并行网关分支用户审核节点" + i + u, userList.get(u)));
                }
                //并行网关-汇聚
                process.addFlowElement(createParallelGateway("parallelGateway-join" + i, "并行网关到-汇聚" + i));

            } else {
                //普通流转
                //审核节点
                process.addFlowElement(createGroupTask("task" + i, "组审核节点" + i, step.getRoleNo()));
                //回退节点
                process.addFlowElement(createUserTask("repulse" + i, "回退节点" + i, "${startUserId}"));
            }
        }
        //结束节点
        process.addFlowElement(createEndEvent());

        //连线
        for (int y = 0; y < stepList.size(); y++) {
            SysWorkflowStep step = stepList.get(y);
            //是否会签
            if (step.getExamineType() == 1) {
                //会签
                //判断是否第一个节点
                if (y == 0) {
                    //开始节点和并行网关-分支连线
                    process.addFlowElement(createSequenceFlow("startEvent", "parallelGateway-fork" + y, "开始节点到并行网关-分支" + y, ""));
                } else {
                    //审核节点或者并行网关-汇聚到并行网关-分支
                    //判断上一个节点是否是会签
                    if (stepList.get(y - 1).getExamineType() == 1) {
                        process.addFlowElement(createSequenceFlow("parallelGateway-join" + (y - 1), "parallelGateway-fork" + y, "并行网关-汇聚到并行网关-分支" + y, ""));
                    } else {
                        process.addFlowElement(createSequenceFlow("task" + (y - 1), "parallelGateway-fork" + y, "上一个审核节点到并行网关-分支" + y, ""));
                    }
                }
                //并行网关-分支和会签用户连线，会签用户和并行网关-汇聚连线
                List<String> userList = userList(Integer.parseInt(step.getRoleNo()), step.getStepExamineType());
                for (int u = 0; u < userList.size(); u++) {
                    process.addFlowElement(createSequenceFlow("parallelGateway-fork" + y, "userTask" + y + u, "并行网关-分支到会签用户" + y + u, ""));
                    process.addFlowElement(createSequenceFlow("userTask" + y + u, "parallelGateway-join" + y, "会签用户到并行网关-汇聚", ""));
                }
                //最后一个节点  并行网关-汇聚到结束节点
                if (y == (stepList.size() - 1)) {
                    process.addFlowElement(createSequenceFlow("parallelGateway-join" + y, "endEvent", "并行网关-汇聚到结束节点", ""));
                }
            } else {
                //普通流转
                //第一个节点
                if (y == 0) {
                    //开始节点和审核节点1
                    process.addFlowElement(createSequenceFlow("startEvent", "task" + y, "开始节点到审核节点" + y, ""));
                } else {
                    //判断上一个节点是否会签
                    if (stepList.get(y - 1).getExamineType() == 1) {
                        //会签
                        //并行网关-汇聚到审核节点
                        process.addFlowElement(createSequenceFlow("parallelGateway-join" + (y - 1), "task" + y, "并行网关-汇聚到审核节点" + y, ""));
                    } else {
                        //普通
                        process.addFlowElement(createSequenceFlow("task" + (y - 1), "task" + y, "审核节点" + (y - 1) + "到审核节点" + y, "${flag=='true'}"));
                    }
                }
                //是否最后一个节点
                if (y == (stepList.size() - 1)) {
                    //审核节点到结束节点
                    process.addFlowElement(createSequenceFlow("task" + y, "endEvent", "审核节点" + y + "到结束节点", "${flag=='true'}"));
                }
                //审核节点到回退节点
                process.addFlowElement(createSequenceFlow("task" + y, "repulse" + y, "审核不通过-打回" + y, "${flag=='false'}"));
                process.addFlowElement(createSequenceFlow("repulse" + y, "task" + y, "回退节点到审核节点" + y, ""));
            }
        }

        // 2. 生成的图形信息
        new BpmnAutoLayout(model).execute();

        // 3. 部署流程
        Deployment deployment = processEngine.getRepositoryService().createDeployment().addBpmnModel(process.getId() + ".bpmn20.xml", model).name(process.getId() + "_deployment").deploy();

        // 4. 启动一个流程实例
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(process.getId());

        // 5. 获取流程任务
        //final List<Task> list = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
        try {
            // 6. 将流程图保存到本地文件
            InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
            FileUtils.copyInputStreamToFile(processDiagram, new File("/deployments/" + process.getId() + ".png"));

            // 7. 保存BPMN.xml到本地文件
            InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), process.getId() + ".bpmn20.xml");
            List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deployment.getId());
            FileUtils.copyInputStreamToFile(processBpmn, new File("/deployments/" + process.getId() + ".bpmn20.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    //任务节点-组
    private UserTask createGroupTask(String id, String name, String candidateGroup) {
        List<String> candidateGroups = new ArrayList<String>();
        candidateGroups.add(candidateGroup);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateGroups(candidateGroups);
        return userTask;
    }

    //任务节点-用户
    private UserTask createUserTask(String id, String name, String candidateUsers,String assignee,MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics) throws Exception {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        if (!StrUtil.isEmpty(candidateUsers)){
            userTask.setCandidateUsers(Arrays.asList(candidateUsers.split(",")));
        }
        if (!StrUtil.isEmpty(assignee)) {
            userTask.setAssignee(assignee);
        }
        if (multiInstanceLoopCharacteristics != null) {
            if (!StrUtil.startWith(assignee,"${")||!StrUtil.endWith(assignee,"}")){
                throw new WrokFlowException("当有multiInstanceLoopCharacteristics节点时，userTask节点的assignee请输入正确的格式");
            }
            userTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);
        }
        return userTask;
    }

    /*连线*/
    private SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);
        if (StrUtil.isNotEmpty(conditionExpression)) {
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    //排他网关
    private ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }

    //并行网关
    private ParallelGateway createParallelGateway(String id, String name) {
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(id);
        gateway.setName(name);
        return gateway;
    }

    /**
     * <userTask id="usertask1" name="多实例任务" activiti:assignee="${assignee}">
     * <p>
     *       <multiInstanceLoopCharacteristics isSequential="false" activiti:collection="assigneeList" activiti:elementVariable="assignee">
     * <p>
     *         <completionCondition>${nrOfCompletedInstances/nrOfInstances >= 0.25}</completionCondition>
     * <p>
     *       </multiInstanceLoopCharacteristics>
     *
     * </userTask>
     * <p>
     * 参数说明：
     * <p>
     * activiti:assignee="${assignee}"
     * <p>
     * activiti:elementVariable="assignee" 多实例任务依赖上面的配置${assignee}
     * <p>
     * activiti:collection="assigneeList" 
     * <p>
     * 三个参数结合决定了，当前节点的处理人来自assigneeList集合，注意这里是集合信息而不是字符串，所以程序的运行时候变量的赋值，如下所示：
     * <p>
     * String[]v={"shareniu1","shareniu2","shareniu3","shareniu4"};
     * <p>
     * vars.put("assigneeList",  Arrays.asList(v));
     * <p>
     * String taskId="97515";
     * <p>
     * demo.getTaskService().complete(taskId,vars);
     *
     * @param sequential
     * @param loopCardinality
     * @param completionCondition
     * @return
     */
    private MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(boolean sequential, String loopCardinality,
                                                                                    String completionCondition) {
        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        multiInstanceLoopCharacteristics.setCollectionString(Contants.FLOWABLE_MULTIINSTANCELOOPCHARACTERISTICS_COLLECTION);
        //是否串行
        multiInstanceLoopCharacteristics.setSequential(sequential);
        multiInstanceLoopCharacteristics.setInputDataItem(Contants.FLOWABLE_MULTIINSTANCELOOPCHARACTERISTICS_COLLECTION);
        //nrOfInstances 实例总数
        //nrOfActiveInstances 当前还没有完成的实例
        //nrOfCompletedInstances 已经完成的实例个数
        //${nrOfCompletedInstances/nrOfInstances >= 0.25}
        //使用串行方式操作nrOfActiveInstances 变量始终是1，因为并行的时候才会去+1操作
        multiInstanceLoopCharacteristics.setCompletionCondition(completionCondition);
        multiInstanceLoopCharacteristics.setElementVariable(Contants.FLOWABLE_MULTIINSTANCELOOPCHARACTERISTICS_ELEMENTVARIABLE);
        return multiInstanceLoopCharacteristics;
    }

    //开始节点
    private StartEvent createStartEvent(List<FormProperty> formProperties,String formKey) {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        startEvent.setName("Start");
        startEvent.setInitiator(Contants.FLOWABLE_INITIATOR_PARAM_APPLICANT);
        if (formProperties !=null && formProperties.size() > 0){
            startEvent.setFormProperties(formProperties);
        }
        if (!StrUtil.isEmpty(formKey)) {
            startEvent.setFormKey(formKey);
        }
        return startEvent;
    }

    /*结束节点*/
    private EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        return endEvent;
    }
}
