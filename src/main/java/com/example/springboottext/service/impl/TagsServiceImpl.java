package com.example.springboottext.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.springboottext.pojo.Tags;
import com.example.springboottext.mapper.TagsMapper;
import com.example.springboottext.pojo.Users;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.ITagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-05
 */
@Service
public class TagsServiceImpl implements ITagsService{
     @Autowired
     private TagsMapper tagsmapper;
     private String key = "Tags";

     @Override
     public List<Tags> getList() {
          return tagsmapper.getList();
     }

     @Override
     public Tags getByid(Integer id) {
              return tagsmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           tagsmapper.deleteByid(id);
     }

     @Override
     public void insert(Tags tags) {
          tagsmapper.insert(tags);
     }

     @Override
     public void updateByid(Tags tags) {
         tagsmapper.updateByid(tags);
     }

    @Override
    public void insertList(Integer id, List<String> list) {
        tagsmapper.insertList(id,list);
    }

    @Override
    public List<Tags> getArticleByid(Integer id) {
        String key = this.key + id;
        String data = RedisServe.getValue(key);
        if(data == null || data.equals("")) {
            try {
                List<Tags> list = tagsmapper.getArticleByid(id);
                String Json = JSON.toJSONString(list);
                RedisServe.setValue(key, Json);
                return list;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return JSON.parseArray(data, Tags.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getHotTag() {
        String key = this.key + ":hot";
        String data = RedisServe.getValue(key);
        if(data == null || data.equals("")) {
            try {
                List<String> list = tagsmapper.getHotTag();
                String Json = JSON.toJSONString(list);
                RedisServe.setValue(key, Json);
                return list;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return JSON.parseArray(data, String.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tags> getDistinctList() {
        return tagsmapper.getDistinctList();
    }

    @Override
    public List<Tags> getPaginatedList(Integer index, Integer pages) {
        Integer offset = (index - 1) * pages;
        //pages 总共要多少页？
        //index 按钮提供的
        return tagsmapper.getPaginatedList(pages,offset);
    }
}
