package com.xiaobingby.flow.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/10/6
 * Time: 12:50
 * Describe:
 */
@Data
public class SysWorkflowStep implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *主键
     */
    private Long id;
    /**
     * 该步骤审核的角色
     */
    private String roleNo;
    /**
     * 该不步骤审核的用户   roleNo与userNo与SysWorkFlow的type有关
     */
    private String userNo;
    /**
     * type==1  会签
     * type==2  普通流转
     * 默认为2 普通流转
     */
    private Integer examineType;
    /**
     * 步骤审核类型
     * 1 按照用户审核；
     * 2 按照角色审核
     */
    private Integer stepExamineType;
    /**
     * 下面三个属性，当examineType=1 表明该节点 属于会签时，才有值
     */
    private String loopCardinality;

    private String completionCondition;

    private Boolean sequential;
}
