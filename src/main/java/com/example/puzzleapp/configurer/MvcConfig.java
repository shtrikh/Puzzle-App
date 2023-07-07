package com.example.puzzleapp.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploaded-images-shuffle/**", "/uploaded-images/**")
                .addResourceLocations("file:uploaded-images-shuffle/", "file:uploaded-images/");
    }
}

