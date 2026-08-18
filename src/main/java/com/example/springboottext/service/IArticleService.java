package com.example.springboottext.service;

import com.example.springboottext.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-08
 */
@Service
public interface IArticleService  {

    public List<Article> getList();

    public Article getByid(Integer id);

    public void deleteByid(Integer id);

    public Integer insert(Article article);

    public void updateByid(Article article);

    List<Article> getByidAll(Integer id);

    void updatemodifiedValueByid(Article article);

    List<Article> getIdentityByid(Integer id);

    void deleteUserByid(Integer id);

    List<Article> getPaginatedList(Integer index, Integer pages);

    Integer getAmount();

    List<Article> getAuthorArticlePaginatedList(Integer index, Integer pages, Integer id);

    Integer getAmountByid(Integer id);

    List<Article> getRandomArticle(Integer number);

    List<Article> getPaginatedClassIfiCationList(Integer index, Integer pages,Integer id);

    List<Article> getArticleByTitle(String title);
}
