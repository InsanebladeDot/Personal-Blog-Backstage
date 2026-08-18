package com.example.springboottext.service;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.ArticleCategories;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 文章分类表 服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-07
 */
@Service
public interface IArticleCategoriesService  {

    public List<ArticleCategories> getList();

    public ArticleCategories getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(ArticleCategories articleCategories);

    public void updateByid(ArticleCategories articleCategories);

    List<ArticleCategories> getMainClassification();

    List<ArticleCategories> getcategory_name(String name);

    List<ArticleCategories> getPaginatedList(Integer index, Integer pages);
}
