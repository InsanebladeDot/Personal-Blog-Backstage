package com.example.springboottext.mapper;

import com.example.springboottext.pojo.UserThirdAuth;

import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 第三方登陆表 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-09-26
 */
public interface UserThirdAuthMapper {
   @Select("Select * from user_third_auth")
   List<UserThirdAuth> getList();

   @Select("Select * from user_third_auth where id = #{id}")
   UserThirdAuth getByid(Integer id);

   @Delete("Delete from user_third_auth where id = #{id}")
   void deleteByid(Integer id);

   @Select("Select * from user_third_auth where openid = #{id}")
   UserThirdAuth getOpenid(Integer id);


   void insert(UserThirdAuth userThirdAuth);

   void updateByid(UserThirdAuth userThirdAuth);

}

