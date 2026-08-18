package com.example.springboottext.controller;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.ICollectionsService;
import com.example.springboottext.pojo.Collections;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 归档 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-10-09
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/collections")
public class CollectionsController {
@Qualifier("collectionsServiceImpl")
@Autowired
private ICollectionsService collectionsService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Collections> collections = collectionsService.getList();

    return Result.success(collections);
    }
@GetMapping("/getDistinctList")
public Result getDistinctList() {
    List<Collections> collections = collectionsService.getDistinctList();
    return Result.success(collections);
}
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Collections collections = collectionsService.getByid(id);

    return Result.success(collections);
}
@Transactional
@GetMapping("/article/{id}")
public Result getArticleByid(@PathVariable Integer id)  {

    Collections collections = collectionsService.getArticleByid(id);

    return Result.success(collections);
}
//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {

    List<Collections> list = collectionsService.getPaginatedList(index,pages);

    return Result.success(list);
}
//根据对应的归档集合获取指定数据集合-> 文章的
@GetMapping("/getCompilationArticleList/{id}")
public Result getCompilationArticleList(@PathVariable Integer id) {

    List<Article> list = collectionsService.getCompilationArticleList(id);

    return Result.success(list);
}


 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    collectionsService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Collections collections) {
    collectionsService.insert(collections);
    return Result.success();
}
//批量插入
@PostMapping("/list")
public Result insertList(@RequestBody List<Collections> list) {
    log.info("list:{}", list.toString());
    collectionsService.insertList(list);
    return Result.success();
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Collections collections){
    log.info("请求更改 "+collections);
    collectionsService.updateByid(collections);
    return Result.success();
    }
}

