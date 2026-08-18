package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.apache.commons.logging.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.ITagsService;
import com.example.springboottext.pojo.Tags;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 标签表 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-10-05
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/tags")
public class TagsController {
@Qualifier("tagsServiceImpl")
@Autowired
private ITagsService tagsService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Tags> tags = tagsService.getList();

    return Result.success(tags);
    }
@GetMapping("/getDistinctList")
public Result getDistinctList() {
   List<Tags> tags = tagsService.getDistinctList();
   return Result.success(tags);
}
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Tags tags = tagsService.getByid(id);

    return Result.success(tags);
 }

 @GetMapping("/article/{id}")
 public Result getArticleByid(@PathVariable Integer id)  {

    List<Tags> list = tagsService.getArticleByid(id);

    return Result.success(list);
}

//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {

    List<Tags> list = tagsService.getPaginatedList(index,pages);

    return Result.success(list);
}
//获取前50的热门tag
@GetMapping("/getHotTag")
public Result getHotTag() {

    List<String> list = tagsService.getHotTag();

    return Result.success(list);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    tagsService.deleteByid(id);

    return Result.success();
}

@PostMapping
public Result insert(@RequestBody Tags tags) {
    tagsService.insert(tags);
    return Result.success();
}
@PostMapping("/list/{id}")
public Result insertList(@RequestBody List<String> list,@PathVariable Integer id) {
    log.info("id:{},list:{}", id, list.toString());
    tagsService.insertList(id,list);
    return Result.success();
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Tags tags){
    log.info("请求更改 "+tags);
    tagsService.updateByid(tags);
    return Result.success();
}
}

