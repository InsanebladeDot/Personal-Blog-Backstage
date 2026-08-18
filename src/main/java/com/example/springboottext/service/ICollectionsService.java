package com.example.springboottext.service;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Collections;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 归档 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
@Service
public interface ICollectionsService  {

    public List<Collections> getList();

    public Collections getByid(Integer id);

    public List<Collections> getPaginatedList(Integer index,Integer pages);

    public void deleteByid(Integer id);

    public void insert(Collections collections);

    public void updateByid(Collections collections);

    void insertList(List<Collections> list);

    List<Article> getCompilationArticleList(Integer id);

    Collections getArticleByid(Integer id);

    List<Collections> getDistinctList();
}
