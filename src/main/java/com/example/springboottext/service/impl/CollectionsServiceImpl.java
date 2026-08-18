package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Collections;
import com.example.springboottext.mapper.CollectionsMapper;
import com.example.springboottext.service.ICollectionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 归档 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
@Service
public class CollectionsServiceImpl implements ICollectionsService{
     @Autowired
     private CollectionsMapper collectionsmapper;

     @Override
     public List<Collections> getList() {
          return collectionsmapper.getList();
     }

     @Override
     public Collections getByid(Integer id) {
              return collectionsmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           collectionsmapper.deleteByid(id);
     }

     @Override
     public void insert(Collections collections) {
          collectionsmapper.insert(collections);
     }

     @Override
     public void updateByid(Collections collections) {
         collectionsmapper.updateByid(collections);
     }

    @Override
    public void insertList( List<Collections> list) {
        collectionsmapper.insertList(list);
    }

    @Override
    public List<Article> getCompilationArticleList(Integer id) {
        return collectionsmapper.getCompilationArticleList(id);
    }

    @Override
    public Collections getArticleByid(Integer id) {
        return collectionsmapper.getArticleByid(id);
    }

    @Override
    public List<Collections> getDistinctList() {
        return collectionsmapper.getDistinctList();
    }

    @Override
    public List<Collections> getPaginatedList(Integer index, Integer pages) {
        Integer offset = (index - 1) * pages;
        //pages 总共要多少页？
        //index 按钮提供的
        return collectionsmapper.getPaginatedList(pages,offset);
    }
}
