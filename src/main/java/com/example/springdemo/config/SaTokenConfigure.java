package com.example.springdemo.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 李鑫豪
 * @created: 2021/05/27 19:51
 * @description: 登陆验证配置
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册sa-token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}