package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Friendlink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 友链 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-10-02
 */
public interface FriendlinkMapper {
   @Select("Select * from friendlink")
   List<Friendlink> getList();

   @Select("Select * from friendlink where id = #{id}")
   Friendlink getByid(Integer id);

   @Select("SELECT * FROM friendlink  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Friendlink> getPaginatedList(Integer pages, Integer offset);

   @Delete("Delete from friendlink where id = #{id}")
   void deleteByid(Integer id);

   void insert(Friendlink friendlink);

   void updateByid(Friendlink friendlink);

}

