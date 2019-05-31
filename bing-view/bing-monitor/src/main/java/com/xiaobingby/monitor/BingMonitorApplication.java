package com.xiaobingby.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class BingMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingMonitorApplication.class, args);
    }

}
