package com.xiaobingby.flow.controller;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/10/2
 * Time: 18:16
 * Describe:
 */
@Slf4j
public class BaseController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     * 查询正在执行的任务
     * @param taskId
     * @return
     */
    Task getTaskFromRequest(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        return task;
    }

    /**
     * 查询his表中的任务
     * @param taskId
     * @return
     */
    HistoricTaskInstance getHistoricTaskFromRequest(String taskId) {
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        return task;
    }
}
