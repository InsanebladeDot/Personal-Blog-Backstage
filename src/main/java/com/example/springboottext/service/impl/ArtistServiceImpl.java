package com.example.springboottext.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Artist;
import com.example.springboottext.mapper.ArtistMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.IArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-06
 */
@Slf4j
@Service
public class ArtistServiceImpl implements IArtistService{
     @Autowired
     private ArtistMapper artistmapper;

     @Override
     public List<Artist> getList() {
          //尝试读取 redis 里面 是否含有 对应的list 数据 如果有则直接返回，如何没有 则去找 mapper返回的给他放入进去
          String data =   RedisServe.getValue("ArtistList");
          if(data == null || data.equals("")){
               try {
                    List<Artist> artist = artistmapper.getList();
                    String articleJson = JSON.toJSONString(artist);
                    RedisServe.setValue("ArtistList", articleJson,200);
                    return artist;
               } catch (Exception e) {
                    e.printStackTrace();
                    log.error("插入出错了！!!!!!!");
                    return Collections.emptyList(); // 返回空集合qasaz
               }
          }
          //反序列化
          try {
               List<Artist> artist = JSON.parseArray(data, Artist.class);

               return artist;
          }catch (Exception e){
               e.printStackTrace();
               log.error("转化的时候出错了！!!!!!!");
               return Collections.emptyList(); // 返回空集合qasaz
          }
     }

     @Override
     public Artist getByid(Integer id) {
              return artistmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           artistmapper.deleteByid(id);
     }

     @Override
     public void insert(Artist artist) {
          artistmapper.insert(artist);
     }

     @Override
     public void updateByid(Artist artist) {
         artistmapper.updateByid(artist);
     }

     @Override
     public Artist findByid(Integer id) {
          return artistmapper.FindByid(id);
     }

     @Override
     public void deleteUserByid(Integer id) {
          artistmapper.deleteUserByid(id);
     }

     @Override
     public Artist getuserByid(Integer id) {
          return artistmapper.getuserByid(id);
     }

     @Override
     public List<Artist> getPaginatedList(Integer index, Integer pages) {
          Integer offset = (index - 1) * pages;
          //pages 总共要多少页？
          //index 按钮提供的
          return artistmapper.getPaginatedList(pages,offset);
     }
}
