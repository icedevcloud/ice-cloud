package com.icedevcloud.upms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "ignore")
public class IgnorePropertiesConfig {

    private List<String> urls = new ArrayList<>();

}
