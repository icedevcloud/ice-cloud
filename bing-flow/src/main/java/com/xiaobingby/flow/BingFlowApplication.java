package com.xiaobingby.flow;

import org.flowable.ui.modeler.conf.ApplicationConfiguration;
import org.flowable.ui.modeler.servlet.AppDispatcherServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/20
 * Time: 17:54
 * Describe:
 * assignee:受理人  执行任务的用户
 * 1、当流程模型 xml 中指定了受理人时，Task 会直接填入该用户；
 * 2、当没有指定或仅仅指定了候选人或候选组的时候，该字段为空。
 * 当该字段为空时，可以使用签收功能指定受理人，即从候选人或候选组中选择用户签收该任务。
 * owner 委托人 受理人把当前需要执行的任务委托给其他人，这个时候，受理人就变成了委托人
 *
 */
@Import({
        ApplicationConfiguration.class,
        AppDispatcherServletConfiguration.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class BingFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(BingFlowApplication.class, args);
    }

}
