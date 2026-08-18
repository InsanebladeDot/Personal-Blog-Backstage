package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Praise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 点赞表 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-10-03
 */
public interface PraiseMapper {
   @Select("Select * from praise")
   List<Praise> getList();

   @Select("Select * from praise where id = #{id}")
   Praise getByid(Integer id);

   @Select("Select * from praise where  user_id = #{id}")
   List<Praise> getUserList(Integer id);

   //获取指定评论消息自己是否点赞 or 点踩
   @Select("Select * from praise where  replied_user_id = #{repliedUserId} and reviewer_id = #{reviewerId} and article_id = #{articleId} and user_id = #{userId} and type=#{type}")
   Praise getPraise(Praise praise);

   @Delete("Delete from praise where id = #{id}")
   void deleteByid(Integer id);

   void insert(Praise praise);

   void updateByid(Praise praise);

}

