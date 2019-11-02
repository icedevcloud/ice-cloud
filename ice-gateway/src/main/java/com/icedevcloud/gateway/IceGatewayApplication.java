package com.icedevcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceGatewayApplication.class, args);
    }

}
