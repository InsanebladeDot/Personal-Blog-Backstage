package com.example.springboottext.untill;

import com.example.springboottext.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 配置类示例
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                // 添加拦截规则，可以指定拦截哪些路径或排除哪些路径
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/Login/**")
                .excludePathPatterns("/giteeLogin/**")
        ; // 排除某些路径

    }
}