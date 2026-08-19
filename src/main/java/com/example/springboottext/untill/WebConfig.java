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
                // 仅放行登录/注册/公钥/第三方登录相关接口;其余 /Login/** (如 PUT /Login 修改密码) 必须携带 JWT
                .excludePathPatterns("/Login")                   // POST 登录
                .excludePathPatterns("/Login/publicKey")         // GET 下发 RSA 公钥
                .excludePathPatterns("/Login/registeredAccount") // POST 注册
                .excludePathPatterns("/Login/open")              // POST 第三方登录查询
                .excludePathPatterns("/giteeLogin/**")
        ; // 排除某些路径

    }
}