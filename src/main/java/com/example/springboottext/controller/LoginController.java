package com.example.springboottext.controller;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.Result;
import com.example.springboottext.pojo.Users;
import com.example.springboottext.service.impl.UsersServiceImpl;
import com.example.springboottext.untill.JwtUtils;
import com.example.springboottext.untill.RSAKeyService;
import com.example.springboottext.untill.SHA256Util;
import io.jsonwebtoken.Claims;
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
    @Autowired
    private RSAKeyService rsaKeyService;

    /**
     * 下发 RSA 公钥,供前端加密密码(Base64 编码的 X.509 SPKI)
     */
    @GetMapping("/publicKey")
    public Result getPublicKey() {
        log.info("返回公钥---{}",rsaKeyService.getPublicKeyBase64());

        return Result.success(rsaKeyService.getPublicKeyBase64());
    }

    @PostMapping
    public Result LoginfindAll(@RequestBody Users users){
        log.info("查询总览----{}",users.toString());
        try {
            //RSA 解密:前端上传的 password 是公钥加密的密文,解密出明文后再走原有加盐 SHA-256 校验
            String plainPassword;
            try {
                plainPassword = rsaKeyService.decrypt(users.getPassword());
            } catch (Exception e) {
                log.warn("登录密码解密失败", e);
                throw new AccountNotFoundException("密码解密失败,请重新获取公钥后重试");
            }
            users.setPassword(plainPassword);

            Users user= loginController.Loginfind(users);
            if(user!=null){
                log.info("查询总览{}",user.toString());
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
        try {
            //RSA 解密出明文密码后入库(SHA256+盐)
            users.setPassword(rsaKeyService.decrypt(users.getPassword()));
        } catch (Exception e) {
            log.warn("注册密码解密失败", e);
            return Result.error("密码解密失败,请重新获取公钥后重试");
        }
        loginController.insert(users);
        return Result.success();
    }
    /**
     * 修改密码(需登录态 + 旧密码校验)
     * 请求头: token(登录后返回的 JWT)
     * 请求体: {"oldPassword":"旧密码","newPassword":"新密码"}
     */
    @PutMapping
    public Result update(@RequestBody Map<String, String> body, @RequestHeader("token") String token) {
        //1.登录态校验:解析 token 获取当前登录用户,防止修改他人密码
        Integer userId;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = Integer.valueOf(claims.get("id").toString());
        } catch (Exception e) {
            return Result.error("登录状态无效,请重新登录");
        }

        //RSA 解密:oldPassword / newPassword 均为前端公钥加密的密文
        String oldPassword;
        String newPassword;
        try {
            oldPassword = rsaKeyService.decrypt(body.get("oldPassword"));
            newPassword = rsaKeyService.decrypt(body.get("newPassword"));
        } catch (Exception e) {
            log.warn("修改密码密文解密失败", e);
            return Result.error("密码解密失败,请重新获取公钥后重试");
        }
        if (oldPassword == null || oldPassword.isEmpty()) {
            return Result.error("旧密码不能为空");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return Result.error("新密码不能为空");
        }

        //2.根据 token 中的用户 id 查询用户
        Users dbUser = loginController.getByid(userId);
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
        //3.校验旧密码
        if (dbUser.getPassword() == null || !SHA256Util.verifyPassword(oldPassword, dbUser.getPassword())) {
            return Result.error("旧密码错误");
        }
        //4.更新新密码(updateByid 内部会做 SHA256+盐 哈希入库)
        dbUser.setPassword(newPassword);
        loginController.updateByid(dbUser);
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
