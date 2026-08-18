package com.example.springboottext.mapper;

import com.example.springboottext.pojo.TimeShuttle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-10
 */
public interface TimeShuttleMapper {
   @Select("Select * from time_shuttle")
   List<TimeShuttle> getList();

   @Select("Select * from time_shuttle where id = #{id}")
   TimeShuttle getByid(Integer id);

   @Delete("Delete from time_shuttle where id = #{id}")

   void deleteByid(Integer id);

   void insert(TimeShuttle timeShuttle);

   void updateByid(TimeShuttle timeShuttle);

 }

