package com.xiaobingby.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/20
 * Time: 17:54
 * Describe:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BingFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(BingFlowApplication.class, args);
    }
}
