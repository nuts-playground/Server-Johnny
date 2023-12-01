package com.backend.johnny.config;

import com.backend.johnny.intercepter.ApiIntercepter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WepConfig implements WebMvcConfigurer {

    private final ApiIntercepter apiIntercepter;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiIntercepter)
                .addPathPatterns("/**");
    }
}
