package com.example.springboottext;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CodeNew {


    private static final String DATABASE_URL = "jdbc:mysql://49.235.182.121:3306/personal_blog"; //服务端口 sql 地址
    private static final String DATABASE_USERNAME = "blog_admin"; //账户密码
    private static final String DATABASE_PASSWORD = "jl1314520";
    private static final String AUTHOR = "jianglei";//作者名

    public static void main(String[]args){
        // 使用 FastAutoGenerator 快速配置代码生成器
//        tables 你的表名
        List<String> tables = new ArrayList<>();

        tables.add("chatroom");


//url 是你的数据库 地址 后面 就是 账户密码
//        jdbc:mysql://129.28.26.171:3306 远程服务器
        //
        FastAutoGenerator.create(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)
                .globalConfig(builder -> builder
                        .author(AUTHOR)
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "\\src\\main\\java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.example.springboottext")
                        .entity("pojo")//实体类 也可以不动
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                )
                .strategyConfig(builder ->
                        builder.addInclude(tables) // 设置需要生成的表名
                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateConfig(new Consumer<TemplateConfig.Builder>() {
                    @Override
                    public void accept(TemplateConfig.Builder builder) {
                        // 实体类使用我们的自定义模板
                        builder.controller("templates/controller.java.ftl");
                        builder.entity("templates/entity.java.ftl");
                        builder.mapper("templates/mapper.java.ftl");
                        builder.xml("templates/mapper.xml.ftl");
                        builder.service("templates/service.java.ftl");
                        builder.serviceImpl("templates/serviceImpl.java.ftl");

                    }
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
