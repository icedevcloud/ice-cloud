package com.xiaobingby.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients({"com.xiaobingby.upms.api.feign"})
@EnableDiscoveryClient
@SpringBootApplication
public class IceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceAuthApplication.class, args);
    }

}
