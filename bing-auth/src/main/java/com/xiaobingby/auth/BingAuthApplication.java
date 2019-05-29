package com.xiaobingby.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BingAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingAuthApplication.class, args);
    }

}
