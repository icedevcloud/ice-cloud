package com.icedevcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IceUpmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceUpmsServiceApplication.class, args);
    }

}
