package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-10-05
 */
public interface TagsMapper {
   @Select("Select * from tags")
   List<Tags> getList();
   @Select("SELECT article_id, tag_name, creatime, updatatime FROM tags WHERE (tag_name, creatime) IN (SELECT tag_name, MIN(creatime) FROM tags GROUP BY tag_name);")
   List<Tags> getDistinctList();

   @Select("Select * from tags where id = #{id}")
   Tags getByid(Integer id);
    //分页查询 如果 creatime 不对应表中的 创建时间则自己去修改即可
   @Select("SELECT * FROM  tags  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Tags> getPaginatedList(Integer pages,Integer offset);

   @Select("SELECT * FROM  tags  where article_id = #{id}")
   List<Tags> getArticleByid(Integer id);
   @Select("SELECT tag_name FROM (SELECT tag_name, COUNT(*) AS tag_count FROM tags GROUP BY tag_name ORDER BY tag_count DESC LIMIT 50) AS subquery;")
   List<String> getHotTag();
   @Delete("Delete from tags where id = #{id}")
   void deleteByid(Integer id);

   void insert(Tags tags);

   void updateByid(Tags tags);

   void insertList(Integer id, List<String> list);

}

