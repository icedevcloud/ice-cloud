package com.xiaobingby.flow.enums;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/10/18
 * Time: 16:28
 * Describe:
 */
public class Contants {

    /**
     * 因为是动态实例的节点配置，所有需要配置成${xxxxx} userTask  activiti:assignee
     */
    public final static String FLOWABLE_USERTASK_ASSIGNEE="${assignee}";
    /**
     * multiInstanceLoopCharacteristics节点 activiti:collection
    */
    public final static String FLOWABLE_MULTIINSTANCELOOPCHARACTERISTICS_COLLECTION="assigneeList";
    /**
     * multiInstanceLoopCharacteristics节点 activiti:elementVariable
     */
    public final static String  FLOWABLE_MULTIINSTANCELOOPCHARACTERISTICS_ELEMENTVARIABLE="assignee";
}
