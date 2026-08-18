package com.example.springboottext.exception;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice // 定义一个 异常处理器
@Slf4j //加上这个注解会自动生成 一个日志记录
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) // 捕获所有的异常
    public Result ex(Exception ex){
        ex.printStackTrace();
        log.info("错误{}",ex.getMessage());
        return Result.error("对不起,操作失败,请联系管理员");
    }

    @ExceptionHandler(ArithmeticException.class) // 捕获 算术异常
    public Result ArithmeticException(ArithmeticException e) {
        log.error(e.getMessage(), e);
        return Result.error("计算时发成错误,算术异常");
    }

    @ExceptionHandler(IOException.class)
    public Result IOException(IOException e) {
        log.error(e.getMessage(), e);
        return Result.error("读写文件时发生错误,IO异常");
    }
    //处理账户类错误
    @ExceptionHandler(AccountNotFoundException.class)
    public Result handleAccountNotFoundException(AccountNotFoundException ex) {
        log.error("账号未找到", ex);
        return Result.error(ex.getMessage());
    }
}
