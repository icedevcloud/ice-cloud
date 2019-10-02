package com.xiaobingby.flow.utils;

import com.xiaobingby.flow.dto.Task;

import java.util.List;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/27
 * Time: 0:23
 * Describe:
 */
public class TaskToBeanUtils {

    public static Task[] ListTaskTranste(org.flowable.task.api.Task... tasks){
        Task[] tasks1=new Task[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            Task task=new Task();
            task.setTaskId(tasks[i].getId());
            task.setTaskName(tasks[i].getName());
            task.setOwner(tasks[i].getOwner());
            task.setAssignee(tasks[i].getAssignee());
            task.setDelegationState(tasks[i].getDelegationState());
            task.setParentTaskId(tasks[i].getParentTaskId());
            task.setLocalizedName(tasks[i].getName());
            task.setDescription(tasks[i].getDescription());
            task.setLocalizedDescription(tasks[i].getDescription());
            task.setPriority(tasks[i].getPriority());
            task.setCreateTime(tasks[i].getCreateTime());
            task.setDueDate(tasks[i].getDueDate());
            task.setCategory(tasks[i].getCategory());
            task.setExecutionId(tasks[i].getExecutionId());
            task.setProcessInstanceId(tasks[i].getProcessInstanceId());
            task.setProcessDefinitionId(tasks[i].getProcessDefinitionId());
            task.setTaskDefinitionId(tasks[i].getTaskDefinitionId());
            task.setScopeId(tasks[i].getScopeId());
            task.setSubScopeId(tasks[i].getSubScopeId());
            task.setScopeType(tasks[i].getScopeType());
            task.setScopeDefinitionId(tasks[i].getScopeDefinitionId());
            task.setTaskDefinitionKey(tasks[i].getTaskDefinitionKey());
            task.setFormKey(tasks[i].getFormKey());
            task.setClaimTime(tasks[i].getClaimTime());
            task.setTenantId(tasks[i].getTenantId());
            tasks1[i]=task;
        }
        return tasks1;

    }

}
