package com.example.springboottext.controller;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.Result;
import com.example.springboottext.pojo.Users;
import com.example.springboottext.service.impl.UsersServiceImpl;
import com.example.springboottext.untill.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    private UsersServiceImpl loginController;
    @PostMapping
    public Result LoginfindAll(@RequestBody Users users){
        log.info("查询总览----{}",users.toString());
        try {
            Users user= loginController.Loginfind(users);
            log.info("查询总览{}",user.toString());
            if(user!=null){
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",user.getId());
                claims.put("username",user.getUsername());
                String jwt = JwtUtils.generateJwt(claims);
                // 使用 Map 直接封装 user 和 jwt
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("user", user);
                resultMap.put("token", jwt);
                return Result.success(resultMap);
            }else {
                //处理报错信息 user or password or emit?
                throw new AccountNotFoundException("验证失败，账户(邮箱)或密码错误");
            }
        }catch (Exception e){
            //处理报错信息 user or password or emit?
            e.printStackTrace();
            throw new AccountNotFoundException("验证失败，账户(邮箱)或密码错误");
        }
        //登录错误
    }
    // 请求申请账号
    @PostMapping("/registeredAccount")
    public Result insert(@RequestBody Users users) {
        loginController.insert(users);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody Users users) {
        //获取对应账户id 对应属性必须唯一
        Users users1 = loginController.getusername(users.getUsername());
        if(users1 == null)return Result.error("找不到账户！！！！");
        log.info("获取对应账户信息：{}",users1.toString());
        users.setId(users1.getId());

        loginController.updateByid(users);
        return Result.success();
    }
    @PostMapping("/open")
    public Result getOpenTypeByid(@RequestBody Users user){
        log.info("-------------------过来了吗？----------------------------");
        log.info("userAuth{}",user.toString());

        try {
            Users users = loginController.getOpenTypeByid(user);
            return Result.success(users);
        }catch (AccountNotFoundException e){
            throw new AccountNotFoundException("在通过第三方标识 和登陆方查找用户时发生错误");
        }
    }
}
