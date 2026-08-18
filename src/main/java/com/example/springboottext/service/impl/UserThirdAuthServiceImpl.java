package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.UserThirdAuth;
import com.example.springboottext.mapper.UserThirdAuthMapper;
import com.example.springboottext.service.IUserThirdAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 第三方登陆表 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-09-26
 */
@Service
public class UserThirdAuthServiceImpl implements IUserThirdAuthService{
     @Autowired
     private UserThirdAuthMapper userThirdAuthmapper;

     @Override
     public List<UserThirdAuth> getList() {
          return userThirdAuthmapper.getList();
     }

     @Override
     public UserThirdAuth getByid(Integer id) {
              return userThirdAuthmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           userThirdAuthmapper.deleteByid(id);
     }

     @Override
     public void insert(UserThirdAuth userThirdAuth) {
          userThirdAuthmapper.insert(userThirdAuth);
     }

     @Override
     public void updateByid(UserThirdAuth userThirdAuth) {
         userThirdAuthmapper.updateByid(userThirdAuth);
     }

     @Override
     public UserThirdAuth getOpenid(Integer id) {
          return userThirdAuthmapper.getOpenid(id);
     }
}
