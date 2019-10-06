package com.xiaobingby.flow.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     */
    private Integer type;
}
