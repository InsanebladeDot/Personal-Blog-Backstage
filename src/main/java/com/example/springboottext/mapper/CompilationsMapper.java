package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Compilations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 文章合集 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
public interface CompilationsMapper {
   @Select("Select * from compilations")
   List<Compilations> getList();

   @Select("Select * from compilations where id = #{id}")
   Compilations getByid(Integer id);
   @Select("Select * from compilations where user_id = #{id}")
   List<Compilations> getUserByid(Integer id);
   //分页查询 如果 creatime 不对应表中的 创建时间则自己去修改即可
   @Select("SELECT * FROM  compilations  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Compilations> getPaginatedList(Integer pages,Integer offset);

   @Delete("Delete from compilations where id = #{id}")
   void deleteByid(Integer id);

   void insert(Compilations compilations);

   void updateByid(Compilations compilations);

}

