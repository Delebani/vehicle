package com.vehicle.config.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "white")
@Component
public class WhiteConfig {
    private List<String> whiteList;
}