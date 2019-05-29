package com.xiaobingby.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BingResourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingResourcesApplication.class, args);
    }

}
