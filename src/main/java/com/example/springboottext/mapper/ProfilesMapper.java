package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Profiles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-23
 */
public interface ProfilesMapper {
   @Select("Select * from profiles")
   List<Profiles> getList();

   @Select("Select * from profiles where profile_id = #{id}")
   Profiles getByid(Integer id);

   @Delete("Delete from profiles where profile_id = #{id}")
   void deleteByid(Integer id);

   void insert(Profiles profiles);

   void updateByid(Profiles profiles);

   @Delete("Delete from profiles where user_id = #{id}")
   void deleteUserByid(Integer id);
}

