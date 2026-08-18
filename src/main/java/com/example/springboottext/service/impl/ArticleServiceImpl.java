package com.example.springboottext.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.springboottext.pojo.Article;
import com.example.springboottext.mapper.ArticleMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.IArticleService;
import com.example.springboottext.service.IFollowsService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-08
 */
@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService{
     @Autowired
     private ArticleMapper articlemapper;
     private String key = "Article";
    @Autowired
    private ArticleMapper articleMapper;

     @Override
     public List<Article> getList() {
     //尝试读取 redis 里面 是否含有 对应的list 数据 如果有则直接返回，如何没有 则去找 mapper返回的给他放入进去
        String data =   RedisServe.getValue(key+"List");
        if(data == null || data.equals("")){
             try {
                  List<Article> articles = articlemapper.getList();
                  String articleJson = JSON.toJSONString(articles);
                  RedisServe.setValue(key+"List", articleJson,200);
                  return articles;
             } catch (Exception e) {
                  e.printStackTrace();
                  log.error("插入出错了！!!!!!!");
                  return Collections.emptyList(); // 返回空集合qasaz
             }
        }
          //反序列化
          try {
               List<Article> articles = JSON.parseArray(data, Article.class);
               log.info("articless:{}",articles);

               return articles;
          }catch (Exception e){
               e.printStackTrace();
               log.error("转化的时候出错了！!!!!!!");
               return Collections.emptyList(); // 返回空集合qasaz
          }

     }

     @Override
     public Article getByid(Integer id) {
          return articlemapper.getByid(id);
//          String data =   RedisServe.getValue(key+id);
//          if(data == null || data.equals("")){
//               try{
//                    Article article = articlemapper.getByid(id);
//                    String articleJson = JSON.toJSONString(article);
//                    RedisServe.setValue(key+id, articleJson);
//                    return article;
//               }catch (Exception e){
//                    e.printStackTrace();
//                    log.error("插入出错了！!!!!!!");
//                    return null; // 返回空集合qasaz
//               }
//          }
//
//          try {
//               Article article = JSON.parseObject(data, Article.class);
//               return article ;
//          }catch (Exception e){
//               e.printStackTrace();
//               return null;
//          }
      }

     @Override
     public void deleteByid(Integer id) {
          RedisServe.deleteKey(key+id);
           articlemapper.deleteByid(id);
     }

     @Override
     public Integer insert(Article article) {
          //对文章执行插入操作 再对Redis 进行操作
          //再插入的时候对
          //这里出问题会导致整体出问题
//         String articleJson = JSON.toJSONString(article);
//         RedisServe.insertValue(key+"List", articleJson);

         articlemapper.insert(article);
         return article.getArticleId();
     }

     @Override
     public void updateByid(Article article) {
         String articleJson = JSON.toJSONString(article);
         RedisServe.updateValue(key+article.getArticleId(), articleJson);
         articlemapper.updateByid(article);
     }

     @Override
     public List<Article> getByidAll(Integer id) {
          return  articlemapper.getByidAll(id);
     }

     @Override
     public void updatemodifiedValueByid(Article article) {
          articlemapper.updatemodifiedValueByid(article);
     }

     @Override
     public List<Article> getIdentityByid(Integer id) {
          return articlemapper.getIdentityByid(id);
     }

     @Override
     public void deleteUserByid(Integer id) {
          articlemapper.deleteUserByid(id);
     }

     @Override
     public List<Article> getPaginatedList(Integer index, Integer pages) {
          Integer offset = (index - 1) * pages;
          //pages 总共要多少页？
          //index 按钮提供的
          String key = this.key + pages + index;
          String data =   RedisServe.getValue(key);
          if(data == null || data.equals("")){
               try {
                    List<Article> articles = articlemapper.getPaginatedList(pages,offset);
                    String articleJson = JSON.toJSONString(articles);
                    RedisServe.setValue(key, articleJson,200);
                    return articles;
               } catch (Exception e) {
                    e.printStackTrace();
                    log.error("插入出错了！!!!!!!");
                    return Collections.emptyList(); // 返回空集合qasaz
               }
          }
          //反序列化
          try {
               List<Article> articles = JSON.parseArray(data, Article.class);
               log.info("articless:{}",articles);

               return articles;
          }catch (Exception e){
               e.printStackTrace();
               return Collections.emptyList(); // 返回空集合qasaz
          }

     }

     @Override
     public Integer getAmount() {
          return articlemapper.getAmount();
     }

     @Override
     public List<Article> getAuthorArticlePaginatedList(Integer index, Integer pages, Integer id) {
          Integer offset = (index - 1) * pages;


          return articlemapper.getAuthorArticlePaginatedList(pages,offset,id);
     }

     @Override
     public Integer getAmountByid(Integer id) {
          return articleMapper.getAmountByid(id);
     }

    @Override
    public List<Article> getRandomArticle(Integer number) {
        return articleMapper.getRandomArticle(number);
    }

    @Override
    public List<Article> getPaginatedClassIfiCationList(Integer index, Integer pages,Integer id) {
        Integer offset = (index - 1) * pages;

        return articleMapper.getPaginatedClassIfiCationList(pages,offset,id);
    }

    @Override
    public List<Article> getArticleByTitle(String title) {
        return articleMapper.getArticleByTitle(title);
    }


}
