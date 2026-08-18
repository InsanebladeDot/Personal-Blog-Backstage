package com.example.springboottext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.example.springboottext.mapper") // 替换为你Mapper接口的实际路径
@EnableCaching  //启用缓存设置
public class SpringBootTextApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTextApplication.class, args);
    }

}
