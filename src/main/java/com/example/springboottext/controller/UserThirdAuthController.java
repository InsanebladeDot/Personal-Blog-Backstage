package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IUserThirdAuthService;
import com.example.springboottext.pojo.UserThirdAuth;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 第三方登陆表 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-09-26
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/userThirdAuth")
public class UserThirdAuthController {
@Qualifier("userThirdAuthServiceImpl")
@Autowired
private IUserThirdAuthService userThirdAuthService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<UserThirdAuth> userThirdAuth = userThirdAuthService.getList();

    return Result.success(userThirdAuth);
    }
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    UserThirdAuth userThirdAuth = userThirdAuthService.getByid(id);

    return Result.success(userThirdAuth);
 }
@GetMapping("/openid/{id}")
public Result getOpenid(@PathVariable Integer id)  {
    log.info("id:{}",id);

    UserThirdAuth userThirdAuth = userThirdAuthService.getOpenid(id);
   return Result.success(userThirdAuth);
 }

 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    userThirdAuthService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody UserThirdAuth userThirdAuth) {
    userThirdAuthService.insert(userThirdAuth);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody UserThirdAuth userThirdAuth){
    log.info("请求更改 "+userThirdAuth);
    userThirdAuthService.updateByid(userThirdAuth);
    return Result.success();
    }
}

