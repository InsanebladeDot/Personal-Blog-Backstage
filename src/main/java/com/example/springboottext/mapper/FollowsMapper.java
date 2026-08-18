package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Follows;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 关注表 Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-17
 */
public interface FollowsMapper {
   @Select("Select * from follows")
   List<Follows> getList();

   @Select("Select * from follows where id = #{id}")
   Follows getByid(Integer id);
   @Select("Select * from follows where follower_id = #{id}")
   List<Follows> getMyconcernedList(Integer id);

   @Select("Select * from follows where follower_id = #{followerId} and following_id = #{followingId}")
   Follows getIsconcerned(Integer followingId,Integer followerId);
   @Delete("Delete from follows where id = #{id}")
   void deleteByid(Integer id);

   void insert(Follows follows);

   void updateByid(Follows follows);

   @Select("Select * from follows where following_id=#{id}")
   List<Follows> getfunList(Integer id);
}

