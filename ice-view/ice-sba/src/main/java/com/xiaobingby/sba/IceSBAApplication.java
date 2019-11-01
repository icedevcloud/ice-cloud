package com.xiaobingby.sba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class IceSBAApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceSBAApplication.class, args);
    }

}
