package com.example.springboottext.service;

import com.example.springboottext.pojo.UserThirdAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 第三方登陆表 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-09-26
 */
@Service
public interface IUserThirdAuthService  {

    public List<UserThirdAuth> getList();

    public UserThirdAuth getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(UserThirdAuth userThirdAuth);

    public void updateByid(UserThirdAuth userThirdAuth);

    UserThirdAuth getOpenid(Integer id);
}
