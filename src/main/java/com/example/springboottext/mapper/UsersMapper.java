package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-05
 */
public interface UsersMapper {
   @Select("Select * from users")
   List<Users> getList();
   @Select("Select COUNT(*) from users")
   Integer getAmount();
   @Select("Select * from users where id = #{id}")
   Users getByid(Integer id);

   @Select("Select * from users where ( username =#{username} ) or( email=#{email} )")
   Users Loginfind(Users users);

   @Select("Select * from users where username = #{name}")
   Users getusername(String name);

   @Select("Select * from users where login_type = #{loginType} and openid = #{openId}")
   Users getOpenTypeByid(Users user);


   @Delete("Delete from users where id = #{id}")
   void deleteByid(Integer id);

   void insert(Users users);

   @Update({
           "<script>",
           "UPDATE users",
           "SET",
           "<trim prefixOverrides=','>",
           "<if test='user.username != null'>, username = #{user.username}</if>",
           "<if test='user.password != null'>, `password` = #{user.password}</if>",
           "<if test='user.phone != null'>, phone = #{user.phone}</if>",
           "<if test='user.email != null'>, email = #{user.email}</if>",
           "<if test='user.profilephoto != null'>, profilephoto = #{user.profilephoto}</if>",
           "<if test='user.openid != null'>, openid = #{user.openid}</if>",
           "<if test='user.loginType != null'>, login_type = #{user.loginType}</if>",
           "<if test='user.creatime != null'>, creatime = #{user.creatime}</if>",
           "<if test='user.updatatime != null'>, updatatime = #{user.updatatime}</if>",
           "</trim>",
           "WHERE id = #{user.id}",
           "</script>"
   })
   void updateByid(@Param("user") Users user);

   @Select("SELECT * FROM users  ORDER BY creatime DESC LIMIT #{pages}  OFFSET #{offset}")
   List<Users> getPaginatedList(Integer pages, Integer offset);

}

