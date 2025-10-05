package com.qianlou.app.config;


import com.qianlou.app.interceptor.AuthenticationInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${app.auth.path-patterns.include}")
    private String[] includePathPatterns;

    @Value("${app.auth.path-patterns.exclude}")
    private String[] excludePathPatterns;

    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authenticationInterceptor).addPathPatterns(includePathPatterns).excludePathPatterns(excludePathPatterns);

    }
}