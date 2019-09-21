package com.xiaobingby.flow.taskHandler;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/21
 * Time: 13:06
 * Describe:
 */
public class BossTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("老板");
    }
}
