package com.example.springboottext.controller;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.mapper.CommentsMapper;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IArticleService;
import com.example.springboottext.pojo.Article;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 文章表 前端控制器
    * </p>
*
* @author Baomidou
* @since 2024-07-08
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/article")
public class ArticleController {
@Qualifier("articleServiceImpl")
@Autowired
private IArticleService articleService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Article> article = articleService.getList();

    return Result.success(article);
    }
@GetMapping("/amount")
public Result getAmount() {
    //总共有多少条数据
 Integer number = articleService.getAmount();
 return Result.success(number);
}
//根据文章分类获取对应数据-进行文章按页分类
@GetMapping("/getPaginatedList/classification/{index}/{pages}/{id}")
public Result getPaginatedClassIfiCationList(@PathVariable Integer index , @PathVariable Integer pages,@PathVariable Integer id) {
    //Index 代表着：你点击 1-n 的按钮传递的值 表示1页 还是n页
    //pages 代表着：一页你要多少个内容？
    log.info("分页{}{}",index,pages);

    List<Article> list = articleService.getPaginatedClassIfiCationList(index,pages,id);

    return Result.success(list);
}
@GetMapping("/amount/{id}")
public Result getAmountByid(@PathVariable Integer id) {
    //总共有多少条数据
    Integer number = articleService.getAmountByid(id);
    return Result.success(number);
}
@GetMapping("/title/{title}")
public Result getArticleByTitle(@PathVariable String title) {
    List<Article> list = articleService.getArticleByTitle(title);
    return Result.success(list);
}
//随机获取n个文章 如果文章不够 则返回所有的文章
@GetMapping("/RandomArticle/{number}")
public Result getRandomArticle(@PathVariable Integer number) {
    List<Article> list = articleService.getRandomArticle(number);

    return Result.success(list);
}
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Article article = articleService.getByid(id);

    return Result.success(article);
}
 @GetMapping("/getUserArtcle/{id}")
 public Result getByidAll(@PathVariable Integer id){
    List<Article> list = articleService.getByidAll(id);

    return Result.success(list);
 }
 @GetMapping("/getPaginatedList/{index}/{pages}")
 public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
    //Index 代表着：你点击 1-n 的按钮传递的值 表示1页 还是n页
    //pages 代表着：一页你要多少个内容？
     log.info("分页{}{}",index,pages);

     List<Article> list = articleService.getPaginatedList(index,pages);

    return Result.success(list);
}
@GetMapping("/getAuthorArticlePaginatedList/{index}/{pages}/{id}")
public Result getAuthorArticlePaginatedList(@PathVariable Integer index , @PathVariable Integer pages , @PathVariable Integer id) {

    log.info("分页{}{}，作者{}",index,pages,id);

    List<Article> list = articleService.getAuthorArticlePaginatedList(index,pages,id);

    return Result.success(list);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));
    try {
        articleService.deleteByid(id);
        //删除对应文章时把文章中对应图片也一并删除

        return Result.success();
    }catch (Exception e) {
        e.printStackTrace();
        throw new AccountNotFoundException("删除对应文章时发生错误");
    }

 }
 @DeleteMapping("/userDelete/{id}")
 public Result deleteUserByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    articleService.deleteUserByid(id);

    return Result.success();
}
    //查询 指定用户身份的文章信息
 @GetMapping("/getIdentityByid/{id}")
 public Result getIdentityByid(@PathVariable Integer id){
     log.info(" 查看 :{} "+id);
     List<Article> list = articleService.getIdentityByid(id);
     return Result.success(list);
 }
@PostMapping
public Result insert(@RequestBody Article article) {
    log.info("插入数据集合{}",article.toString());
    try {
        Integer id =  articleService.insert(article);
        log.info("id:{}",id);
        return Result.success(id);
    }catch (Exception e){
        throw new AccountNotFoundException("文章插入失败！");
    }
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Article article){
    log.info("请求更改 "+article);
    articleService.updateByid(article);
    return Result.success();
}

@PutMapping("/updatemodifiedValueByid")
public Result updatemodifiedValueByid(@RequestBody Article article){
    log.info("请求更改 "+article);
    articleService.updatemodifiedValueByid(article);
    return Result.success();

    }
}

