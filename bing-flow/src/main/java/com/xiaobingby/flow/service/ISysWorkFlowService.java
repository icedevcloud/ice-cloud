package com.xiaobingby.flow.service;

import com.xiaobingby.flow.dto.SysWorkFlow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.flow.dto.SysWorkflowStep;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiex
 * @since 2019-10-06
 */
public interface ISysWorkFlowService extends IService<SysWorkFlow> {
    /**
     * 动态流程
     * @param sysWorkFlow
     */
    public void dynamicFlow(SysWorkFlow sysWorkFlow, List<SysWorkflowStep> sysWorkflowSteps) throws Exception;

}
