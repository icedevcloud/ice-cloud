package com.xiaobingby.flow.dto;

import lombok.Data;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.flowable.task.api.DelegationState;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/27
 * Time: 0:26
 * Describe:
 */
@Data
public class Task implements Serializable {
    /**
     * 流程编码
     */
    private String taskId;
    /**
     * 流程名称
     */
    private String taskName;
    /**
     * 所属人
     */
    private String owner;

    private int assigneeUpdatedCount;
    /**
     * 原任务人
     */
    private String originalAssignee;
    /**
     * 先任务人
     */
    private String assignee;
    /**
     * 委派状态
     */
    private DelegationState delegationState;
    /**
     * 父单
     */
    private String parentTaskId;


    private String localizedName;
    /**
     * 描述
     */
    private String description;
    /**
     * 本地化描述
     */
    private String localizedDescription;
    private int priority = 50;
    private Date createTime;
    private Date dueDate;
    private int suspensionState;
    private String category;
    private boolean isIdentityLinksInitialized;
    private List<IdentityLinkEntity> taskIdentityLinkEntities;
    private String executionId;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionId;
    private String scopeId;
    private String subScopeId;
    private String scopeType;
    private String scopeDefinitionId;
    private String taskDefinitionKey;
    private String formKey;
    private boolean isCanceled;
    private boolean isCountEnabled;
    private int variableCount;
    private int identityLinkCount;
    private int subTaskCount;
    private Date claimTime;
    private String tenantId;
    private String eventName;
    private String eventHandlerId;
    private List<VariableInstanceEntity> queryVariables;
    private List<IdentityLinkEntity> queryIdentityLinks;
    private boolean forcedUpdate;
}
