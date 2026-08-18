package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Article;

import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-08
 */
public interface ArticleMapper {
   @Select("Select * from article ORDER BY istop DESC, creative_time DESC;")
   List<Article> getList();
   @Select("Select * from article where author_id = #{id}  ORDER BY istop DESC, creative_time DESC;")
   List<Article> getByidAll(Integer id);
   @Select("Select * from article where article_id = #{id}")
   Article getByid(Integer id);
   @Select("SELECT article.* FROM artist,article WHERE artist.user_id = article.author_id and artist.identity = #{id} ORDER BY istop DESC, creative_time DESC;; ")
   List<Article> getIdentityByid(Integer id);
   //分页查询
   @Select("SELECT * FROM article  ORDER BY creative_time DESC LIMIT #{pages}  OFFSET #{offset}")
   List<Article> getPaginatedList(Integer pages,Integer offset);

   @Select("SELECT DISTINCT * FROM (SELECT * FROM article ORDER BY RAND() LIMIT #{number}) AS subquery;")
   List<Article> getRandomArticle(Integer number);

   @Delete("Delete from article where article_id = #{id}")
   void deleteByid(Integer id);

   //删除对应用户的所有文章
   @Delete("Delete from article where author_id = #{id}")
   void deleteUserByid(Integer id);

   Integer insert(Article article);

   void updateByid(Article article);

   void updatemodifiedValueByid(Article article);
   @Select("SELECT COUNT(*) FROM article")
   Integer getAmount();

   @Select("SELECT COUNT(*) FROM article where author_id = #{id} ")
   Integer getAmountByid(Integer id);

   @Select("SELECT * FROM article  WHERE author_id = #{id} ORDER BY istop DESC, creative_time DESC LIMIT #{pages}  OFFSET #{offset}")
   List<Article> getAuthorArticlePaginatedList(Integer pages,Integer offset, Integer id);

   @Select("SELECT * FROM article  WHERE article_block_id = #{id} ORDER BY istop DESC, creative_time DESC LIMIT #{pages}  OFFSET #{offset}")
   List<Article> getPaginatedClassIfiCationList(Integer pages, Integer offset, Integer id);

   //模糊查询
   @Select("SELECT * FROM article WHERE title LIKE CONCAT('%', LOWER(#{title}), '%')")
   List<Article> getArticleByTitle(String title);
}

