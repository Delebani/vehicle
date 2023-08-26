package com.vehicle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 将/upload/**映射到file:D://upload
         */
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + uploadPath);
    }

}