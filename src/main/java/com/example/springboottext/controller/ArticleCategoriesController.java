package com.example.springboottext.controller;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IArticleCategoriesService;
import com.example.springboottext.pojo.ArticleCategories;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 文章分类表 前端控制器
    * </p>
*
* @author Baomidou
* @since 2024-07-07
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/articleCategories")
public class ArticleCategoriesController {
@Qualifier("articleCategoriesServiceImpl")
@Autowired
private IArticleCategoriesService articleCategoriesService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    try {
        List<ArticleCategories> articleCategories = articleCategoriesService.getList();
        return Result.success(articleCategories);
    } catch (Exception e) {
        throw new AccountNotFoundException("查询文章分类表时发生错误");
    }
}
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {
    try {
        ArticleCategories articleCategories = articleCategoriesService.getByid(id);
        return Result.success(articleCategories);
    }catch (Exception e) {
        e.printStackTrace();
        throw new AccountNotFoundException("查询文章分类表时发生错误");
    }
 }
 @GetMapping("/getMainClassification")
 public Result getMainClassification()  {
     try {
         List<ArticleCategories> list = articleCategoriesService.getMainClassification();
         return Result.success(list);
     }catch (Exception e) {
         throw new AccountNotFoundException("查询文章分类表时发生错误");
     }
 }
 //分页查询
 @GetMapping("/getPaginatedList/{index}/{pages}")
 public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {

    List<ArticleCategories> list = articleCategoriesService.getPaginatedList(index,pages);
    return Result.success(list);
}

 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));
    try {
        articleCategoriesService.deleteByid(id);
        return Result.success();
    }catch (Exception e){
        throw new AccountNotFoundException("删除文章分类时候发生错误");
    }
}
//通过主要分类的数据获取对应的值
@GetMapping("/getcategory_name/{name}")
public Result getcategory_name(@PathVariable  String name){
    try {
        List<ArticleCategories> list = articleCategoriesService.getcategory_name(name);
        return Result.success(list);
    }catch (Exception e){
        throw new AccountNotFoundException("查询文章分类时候发生错误");
    }
}
@PostMapping
public Result insert(@RequestBody ArticleCategories articleCategories) {
    log.info("插入{}",articleCategories.toString());
    try{
        articleCategoriesService.insert(articleCategories);
        return Result.success();
    }catch (Exception e){
        e.printStackTrace();
        throw new AccountNotFoundException("插入文章分类时发生失败");
    }
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody ArticleCategories articleCategories){
    log.info("请求更改 "+articleCategories);
    try{
        articleCategoriesService.updateByid(articleCategories);
        return Result.success();
    }catch (Exception e){
        throw new AccountNotFoundException("文章分类更新时发生错误");
    }
}
//批量更改
@PutMapping("/ListUpdate")
public Result updateListByid(@RequestBody List<ArticleCategories> articleCategories){
    log.info("请求更改 "+articleCategories);

    for (ArticleCategories articleCategory : articleCategories) {
        articleCategoriesService.updateByid(articleCategory);
    }
    return Result.success();
}

}

