package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Artist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-06
 */
public interface ArtistMapper {
   @Select("Select * from artist")
   List<Artist> getList();
   @Select("Select * from artist where artist_id = #{id}")
   Artist getByid(Integer id);
   @Select("Select * from artist where user_id = #{id}")
   Artist FindByid(Integer id);
   @Select("Select * from artist where user_id = #{id}")
   Artist getuserByid(Integer id);
   @Select("SELECT * FROM artist  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Artist> getPaginatedList(Integer pages,Integer offset);

   @Delete("Delete from artist where user_id = #{id}")
   void deleteUserByid(Integer id);

   @Delete("Delete from artist where artist_id = #{id}")
   void deleteByid(Integer id);

   void insert(Artist artist);

   void updateByid(Artist artist);


}

