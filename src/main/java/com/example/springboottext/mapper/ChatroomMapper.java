package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Chatroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2025-01-26
 */
public interface ChatroomMapper {
   @Select("Select * from chatroom")
   List<Chatroom> getList();

   @Select("SELECT COUNT(*) FROM chatroom")
   Integer getAmount();

   @Select("Select * from chatroom where id = #{id}")
   Chatroom getByid(Integer id);
    //分页查询 如果 creatime 不对应表中的 创建时间则自己去修改即可
   @Select("SELECT * FROM  chatroom  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Chatroom> getPaginatedList(Integer pages,Integer offset);

   @Delete("Delete from chatroom where id = #{id}")
   void deleteByid(Integer id);

   void insert(Chatroom chatroom);

   void updateByid(Chatroom chatroom);

 }

