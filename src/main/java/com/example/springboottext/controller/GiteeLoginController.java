package com.example.springboottext.controller;
import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.Result;
import com.example.springboottext.pojo.UserThirdAuth;
import com.example.springboottext.pojo.Users;
import com.example.springboottext.service.IUsersService;
import com.example.springboottext.untill.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.cos.utils.UrlEncoderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/giteeLogin")
public class GiteeLoginController {
    @Value("${gitee.client_id}")
    private String clientId;

    @Value("${gitee.client_secret}")
    private String clientSecret;

    @Value("${gitee.redirectURI}")
    private String redirectURI;

    @Value("${gitee.authorizeURL}")
    private String authorizeURL;

    @Value("${gitee.accessToken}")
    private String accessToken;

    @Value("${gitee.userInfo}")
    private String userInfo;
    @Value("${gitee.homeURI}")
    private String homeURI;

    @Autowired
    private UserThirdAuthController userThirdAuthController;
    @Autowired
    private IUsersService usersService;

    @GetMapping("/callback")
    public RedirectView handleCallback(HttpServletRequest request) {
        String code = request.getParameter("code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", redirectURI);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responses = restTemplate.exchange(
                "https://gitee.com/oauth/token",
                HttpMethod.POST,
                entity,
                Map.class
        );

        String accessToken = (String) responses.getBody().get("access_token");
        log.info("打印信息{}",  responses.getBody());

        // 获取用户信息
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                "https://gitee.com/api/v5/user?access_token=" + accessToken,
                HttpMethod.GET,
                null,
                Map.class
        );
        log.info("打印信息{}", userInfoResponse.getBody());

        // 解构用户信息
        Map<String, Object> userInfo = userInfoResponse.getBody();

        // 序列化为JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        UserThirdAuth userThirdAuth  = SetUserThirdAuth(accessToken, userInfo);

        Result result = userThirdAuthController.getOpenid((Integer) userInfo.get("id"));

        // 如果第三方表中存在 则 不插入
        if(result.getData() != null){
            // 更新数据
            UserThirdAuth userThirdAuth1 = (UserThirdAuth) result.getData();
            userThirdAuth.setId(userThirdAuth1.getId());
            updata(userThirdAuth);
        }else {
            insert(userThirdAuth);
        }


        try {
            String userInfoJson = mapper.writeValueAsString(userThirdAuth);
            return new RedirectView(homeURI + "?userInfo=" + URLEncoder.encode(userInfoJson, StandardCharsets.UTF_8.toString()));
        } catch (Exception e) {
            log.error("Error serializing user info to JSON", e);
            return new RedirectView(homeURI);
        }
    }




    private void insert(UserThirdAuth userThirdAuth) {
        //插入第三方表
        try{
            userThirdAuthController.insert(userThirdAuth);
            Users users = new Users();
            //给第三方用户注册一个带有唯一性 的用户名 和 默认密码
            users.setUsername(userThirdAuth.getUsername() + userThirdAuth.getLoginType() + userThirdAuth.getOpenid());
            users.setProfilephoto(userThirdAuth.getAvatarUrl());
            users.setOpenId(userThirdAuth.getOpenid());
            users.setLoginType(userThirdAuth.getLoginType());
            //密码默认是123456
            users.setPassword("123456");
            // 把与之对应的 插入到用户表中
            //注意:这里是后端内部调用,密码为明文,直接走 Service 入库(SHA256+盐),
            //不能走 UsersController.insert(它会按 RSA 密文解密,明文会解密失败)
            usersService.insert(users);

        }catch (Exception e){
            throw new AccountNotFoundException("在第三方登录插入用户时发生错误");
        }
    }
    private void updata(UserThirdAuth userThirdAuth) {
        //更新第三方表
        try{
            userThirdAuthController.updateByid(userThirdAuth);

        }catch (Exception e){
            throw new AccountNotFoundException("在第三方登录插入用户时发生错误");
        }
    }

    private UserThirdAuth SetUserThirdAuth(String accessToken, Map<String, Object> userInfo){
        UserThirdAuth userThirdAuth = new UserThirdAuth();
        userThirdAuth.setAvatarUrl(userInfo.get("avatar_url").toString());
        userThirdAuth.setUsername(userInfo.get("name").toString());
        userThirdAuth.setToken(accessToken);
        //根据 具体来源方面插入 对应logintype来源
        userThirdAuth.setLoginType("Gitee");
        //插入 openid 唯一标识
        userThirdAuth.setOpenid(Integer.valueOf(userInfo.get("id").toString()));

        return userThirdAuth;
    }
}
