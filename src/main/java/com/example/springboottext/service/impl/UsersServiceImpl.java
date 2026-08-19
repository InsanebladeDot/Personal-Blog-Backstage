package com.example.springboottext.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.springboottext.pojo.ArticleCategories;
import com.example.springboottext.pojo.Artist;
import com.example.springboottext.pojo.Users;
import com.example.springboottext.mapper.UsersMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboottext.untill.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-05
 */
@Slf4j
@Service
public class UsersServiceImpl implements IUsersService {
     @Autowired
     private UsersMapper usersmapper;
     private String key = "Users";
     @Override
     public List<Users> getList() {
          String data =   RedisServe.getValue(key + "List");
          if(data == null || data.equals("")){
               try {
                    List<Users> users = usersmapper.getList();
                    String articleJson = JSON.toJSONString(users);
                    RedisServe.setValue(key + "List", articleJson);
                    return users;
               } catch (Exception e) {
                    e.printStackTrace();
                    log.error("插入出错了！!!!!!!");
                    return Collections.emptyList(); // 返回空集合qasaz
               }
          }
          //反序列化
          try {

              return JSON.parseArray(data, Users.class);
          }catch (RuntimeException e) {
               throw new RuntimeException(e);
          }
     }

     @Override
     public Users getByid(Integer id) {
          String data =   RedisServe.getValue(key + id);
          if(data == null || data.equals("")) {
               try{
                    Users users = usersmapper.getByid(id);
                    String articleJson = JSON.toJSONString(users);
                    RedisServe.setValue(key + id, articleJson);
                    return users;
               }catch (RuntimeException e) {
                    throw new RuntimeException(e);
               }
          }

          try {
              return JSON.parseObject(data, Users.class);
          }catch (RuntimeException e) {
               throw new RuntimeException(e);
          }
     }

     @Override
     public void deleteByid(Integer id) {
          RedisServe.deleteKey(key + id);
          RedisServe.deleteKey(key + "List");
          usersmapper.deleteByid(id);
     }

     @Override
     public void insert(Users users) {
          //只使用一种加密:对前端提交的明文密码直接做 SHA256+盐 哈希后入库(不再使用凯撒加密)
          String plainPassword = users.getPassword();
          if (plainPassword == null || plainPassword.isEmpty()) {
               throw new IllegalArgumentException("密码不能为空");
          }
          users.setPassword(SHA256Util.encryptPassword(plainPassword));
          usersmapper.insert(users);
          //清除用户列表缓存,保证新用户立即可见
          RedisServe.deleteKey(key + "List");
     }

     @Override
     public void updateByid(Users users) {
          //密码更新同样走 SHA256+盐 哈希,防止明文入库
          if (users.getPassword() != null && !users.getPassword().isEmpty()) {
               users.setPassword(SHA256Util.encryptPassword(users.getPassword()));
          }
          usersmapper.updateByid(users);
          //更新后清除缓存,避免后续读取到旧数据(如旧密码哈希)
          if (users.getId() != null) {
               RedisServe.deleteKey(key + users.getId());
          }
          RedisServe.deleteKey(key + "List");
     }

     @Override
     public Users Loginfind(Users users) throws Exception {
          String plainPassword = users.getPassword();
          //按用户名或邮箱查询用户(密码不参与 SQL 查询)
          Users dbUser = usersmapper.Loginfind(users);

          //只使用一种加密:SHA256+盐 校验
          //注意:必须 dbUser != null 之后再访问 dbUser.getPassword(),否则空指针;
          //且不可出现 (A && B) || C 这种优先级陷阱
          if (dbUser != null
                  && dbUser.getPassword() != null
                  && plainPassword != null
                  && SHA256Util.verifyPassword(plainPassword, dbUser.getPassword())) {
               return dbUser;
          }
          return null;
     }

     @Override
     public Users getusername(String name) {
          return usersmapper.getusername(name);
     }

     @Override
     public Users getOpenTypeByid(Users user) {
          return usersmapper.getOpenTypeByid(user);
     }

     @Override
     public List<Users> getPaginatedList(Integer index, Integer pages) {
          Integer offset = (index - 1) * pages;
          //pages 总共要多少页？
          //index 按钮提供的
          return usersmapper.getPaginatedList(pages, offset);
     }

     @Override
     public Integer getAmount() {
          return usersmapper.getAmount();
     }
}
