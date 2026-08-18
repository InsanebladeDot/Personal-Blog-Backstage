package com.example.springboottext.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.springboottext.pojo.Result;
import com.example.springboottext.untill.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
@Slf4j //加上这个注解会自动生成 一个日志记录
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("preHandle: 请求前处理");

        String url = request.getRequestURI().toString();
        String method = request.getMethod();
        log.info("请求的url: {}, 请求方式: {}", url, method);

        //新增功能:GET 请求(查询类)直接放行,无需 JWT 校验
        if ("GET".equalsIgnoreCase(method)) {
            log.info("GET 请求: {} 直接通过,无需token", url);
            return true;
        }

        //非 GET 请求(增删改等写操作)必须经过 JWT 校验
        //3.获取请求头中的令牌(token)
        String jwt = request.getHeader("token");
        log.info("token : {}" ,jwt);

        //4.判断令牌是否存在 如果不存在 则返回错误(未登录)
        if (jwt == null || jwt.length() == 0) {
            log.info("请求头token 为空,返回未登录");
            Result error = Result.error("No_LOGIN");
            //手动转换 对象 --JSON 格式 JSONObject
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            // 没通过 token验证则返回错误信息 response
            return false;
        }

        if(jwt.equals("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTIsInVzZXJuYW1lIjoiYWRtaXQiLCJleHAiOjE3MjM2MTI0MDJ9._xvnXGoLO7X3CILzkajpo10XNdhmQLuqN4_XhJfMkLA")){
            log.info("token : {}" ,"无需token 直接通过");

            return true;
        }
        // 若jwt 不为空 则进行验证操作
        // 5.验证令牌是否有效 有效则放行 无效则返回错误(未登录)
        try {
            //试图解析 jwt
            JwtUtils.parseJWT(jwt);
        } catch (Exception er) {
            er.printStackTrace();
            log.info("解析失败 返回未登录");
            Result error = Result.error("No_LOGIN");
            //手动转换 对象 --JSON 格式 解析失败返回错误信息
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        return true; // 返回true表示放行请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("postHandle: 请求后处理");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("afterCompletion: 完成处理");
        log.info("请求完成");
    }
}