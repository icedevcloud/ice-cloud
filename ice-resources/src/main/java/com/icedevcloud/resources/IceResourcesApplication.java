package com.icedevcloud.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IceResourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceResourcesApplication.class, args);
    }

}
