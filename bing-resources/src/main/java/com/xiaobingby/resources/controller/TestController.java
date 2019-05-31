package com.xiaobingby.resources.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@RefreshScope
public class TestController {

    @Value("${value}")
    private String value;

    @GetMapping("/value")
    public String value() {
        return value;
    }

    @GetMapping(value = "/hello")
    @SentinelResource("hello")
    public String hello() {
        return "Hello Sentinel";
    }

    @GetMapping(value = "/hello2")
    public String hello2() {
        return "Hello Sentinel";
    }

}
