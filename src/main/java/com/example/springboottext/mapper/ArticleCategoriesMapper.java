package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.ArticleCategories;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 * 文章分类表 Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-07
 */
public interface ArticleCategoriesMapper {
   @Select("Select * from article_categories")
   List<ArticleCategories> getList();

   @Select("Select * from article_categories where id = #{id}")
   ArticleCategories getByid(Integer id);

   //   获取主要分类
   @Select("SELECT MIN(id) AS id, main_classification FROM article_categories GROUP BY main_classification;")
   List<ArticleCategories> getMainClassification();

  //通过主要分类获取所有子类
   @Select("Select id, category_name from article_categories where main_classification=#{name}; ")
   List<ArticleCategories> getcategory_name(String name);

   @Delete("Delete from article_categories where id = #{id}")

   void deleteByid(Integer id);

   void insert(ArticleCategories articleCategories);

   void updateByid(ArticleCategories articleCategories);

    //分页查询
    @Select("SELECT * FROM article_categories  ORDER BY creatime DESC LIMIT #{pages}  OFFSET #{offset}")
    List<ArticleCategories> getPaginatedList(Integer pages, Integer offset);
}

