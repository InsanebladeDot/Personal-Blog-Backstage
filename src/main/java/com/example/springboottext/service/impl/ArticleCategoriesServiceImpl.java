package com.example.springboottext.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.ArticleCategories;
import com.example.springboottext.mapper.ArticleCategoriesMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.IArticleCategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 文章分类表 服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-07
 */
@Slf4j
@Service
public class ArticleCategoriesServiceImpl implements IArticleCategoriesService{
     @Autowired
     private ArticleCategoriesMapper articleCategoriesmapper;
     private String key = "ArticleCategories";
     @Override
     public List<ArticleCategories> getList() {
          //尝试读取 redis 里面 是否含有 对应的list 数据 如果有则直接返回，如何没有 则去找 mapper返回的给他放入进去
          String data =   RedisServe.getValue(key + "List");
          if(data == null || data.equals("")){
               try {
                    List<ArticleCategories> articleCategories = articleCategoriesmapper.getList();
                    String articleJson = JSON.toJSONString(articleCategories);
                    RedisServe.setValue(key + "List", articleJson,200);
                    return articleCategories;
               } catch (Exception e) {
                    e.printStackTrace();
                    log.error("插入出错了！!!!!!!");
                    return Collections.emptyList(); // 返回空集合qasaz
               }
          }
          //反序列化
          try {
               List<ArticleCategories> articles = JSON.parseArray(data, ArticleCategories.class);
               log.info("articless:-------->",articles.toString());

               return articles;
          }catch (Exception e){
               e.printStackTrace();
               log.error("转化的时候出错了！!!!!!!");
               return Collections.emptyList(); // 返回空集合qasaz
          }
     }

     @Override
     public ArticleCategories getByid(Integer id) {
          return articleCategoriesmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           articleCategoriesmapper.deleteByid(id);
     }

     @Override
     public void insert(ArticleCategories articleCategories) {
          articleCategoriesmapper.insert(articleCategories);
     }

     @Override
     public void updateByid(ArticleCategories articleCategories) {
         articleCategoriesmapper.updateByid(articleCategories);
     }

     @Override
     public List<ArticleCategories> getMainClassification() {
          return  articleCategoriesmapper.getMainClassification();
     }

     @Override
     public List<ArticleCategories> getcategory_name(String name) {
          return articleCategoriesmapper.getcategory_name(name);
     }

     @Override
     public List<ArticleCategories> getPaginatedList(Integer index, Integer pages) {
          Integer offset = (index - 1) * pages;
          //pages 总共要多少页？
          //index 按钮提供的
          return articleCategoriesmapper.getPaginatedList(pages,offset);
     }
}
