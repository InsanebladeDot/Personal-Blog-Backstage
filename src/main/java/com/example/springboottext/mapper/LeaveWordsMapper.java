package com.example.springboottext.mapper;

import com.example.springboottext.pojo.LeaveWords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-19
 */
public interface LeaveWordsMapper {
   @Select("SELECT a.*,p.profilephoto ,b.artist_name FROM leave_words a LEFT JOIN users p ON a.user_id = p.id LEFT JOIN artist b ON p.id = b.user_id;")
   List<Map<String, Object>> getList();

   @Select("Select * from leave_words where id = #{id}")
   LeaveWords getByid(Integer id);

   @Delete("Delete from leave_words where id = #{id}")

   void deleteByid(Integer id);

   void insert(LeaveWords leaveWords);

   void updateByid(LeaveWords leaveWords);

 }

