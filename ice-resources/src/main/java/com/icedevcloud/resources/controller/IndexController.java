package com.icedevcloud.resources.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cache")
    public String cache() {
        return cacheData();
    }

    @Cacheable("cache")
    public String cacheData() {
        return "hello cache";
    }

}
