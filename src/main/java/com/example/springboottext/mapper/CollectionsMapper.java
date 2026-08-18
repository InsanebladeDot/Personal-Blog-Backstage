package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Collections;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 归档 Mapper 接口
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
public interface CollectionsMapper {
   @Select("Select * from collections")
   List<Collections> getList();

   @Select("WITH RankedCollections AS (SELECT *, ROW_NUMBER() OVER (PARTITION BY compilationid ORDER BY id) AS rn FROM collections) SELECT * FROM RankedCollections WHERE rn = 1;")
   List<Collections> getDistinctList();

   @Select("Select * from collections where id = #{id}")
   Collections getByid(Integer id);
   //通过文章id获取所处归档
   @Select("Select * from collections where article_id = #{id}")
   Collections getArticleByid(Integer id);
   //分页查询 如果 creatime 不对应表中的 创建时间则自己去修改即可
   @Select("SELECT * FROM  collections  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Collections> getPaginatedList(Integer pages,Integer offset);
   //查询对应的 文章合集 的所有文章
   @Select("SELECT * FROM article WHERE article_id IN (Select article_id from collections where compilationid = #{id})")
   List<Article> getCompilationArticleList(Integer id);
   @Delete("Delete from collections where id = #{id}")
   void deleteByid(Integer id);

   void insert(Collections collections);

   void updateByid(Collections collections);

    void insertList(List<Collections> list);

}

