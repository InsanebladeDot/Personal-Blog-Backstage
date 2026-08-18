package com.example.springboottext.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.springboottext.pojo.ArticleCategories;
import com.example.springboottext.pojo.Artist;
import com.example.springboottext.pojo.Users;
import com.example.springboottext.mapper.UsersMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboottext.untill.KaisaUtil;
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
          usersmapper.deleteByid(id);
     }

     @Override
     public void insert(Users users) {
          log.info("注册的时候的Kaisa密文：{}",users.getPassword());//Kaisa 密文
          //把KaiSa 密文 解密 成明文 再 把 明文 加密成SHA256
          //1.把Kaisa 密文转化成 明文
          String password = KaisaUtil.decryptKaiser(users.getPassword());
          //再把明文转化为 SHA256 的密文
          users.setPassword(SHA256Util.encryptPassword(password));
          log.info("注册传递了密文：{}",users.getPassword());
          usersmapper.insert(users);
     }

     @Override
     public void updateByid(Users users) {
          usersmapper.updateByid(users);
     }

     @Override
     public Users Loginfind(Users users) throws Exception {
          String password = users.getPassword();//Kaisa 密文
          log.info("传递的Kaisa密文？？{}", password);//Kaisa解密
          log.info("传递的明文？？{}", KaisaUtil.decryptKaiser(password));//Kaisa解密
          //获取单个对应用户
          users = usersmapper.Loginfind(users);//SHA256 的密文

          log.info("密码对："+KaisaUtil.decryptKaiser(password)+':'+users.getPassword());
          log.info("是否对嘛？？：{}",SHA256Util.verifyPassword(KaisaUtil.decryptKaiser(password), users.getPassword()));

          if(users != null && SHA256Util.verifyPassword(KaisaUtil.decryptKaiser(password), users.getPassword()) ||users.getPassword().equals(KaisaUtil.decryptKaiser(password)) ){ //Kaisa 解密的明文，和SHA256 的密文 通过快捷登录的时候 避免了 因为SHA 密码完全相同 导致的问题
               return users;
          }else return null;
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
