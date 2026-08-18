package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Publishmessages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-01
 */
public interface PublishmessagesMapper {
   @Select("Select * from publishmessages")
   List<Publishmessages> getList();

   @Select("Select * from publishmessages where id = #{id}")
   Publishmessages getByid(Integer id);

   @Delete("Delete from publishmessages where id = #{id}")
   void deleteByid(Integer id);
   @Select("Select * from publishmessages")
   void insert(Publishmessages publishmessages);
   @Select("Select * from publishmessages")
   void updateByid(Publishmessages publishmessages);
}

