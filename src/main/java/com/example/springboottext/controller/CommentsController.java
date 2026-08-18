package com.example.springboottext.controller;

import com.example.springboottext.pojo.Artist;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.ICommentsService;
import com.example.springboottext.pojo.Comments;
import org.springframework.web.bind.annotation.*;
import com.example.springboottext.pojo.Result;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
 *  前端控制器
 * </p>
*
* @author Baomidou
* @since 2024-06-30
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/comments")
  public class CommentsController {
    @Qualifier("commentsServiceImpl")
    @Autowired
 private ICommentsService commentsService;
// 增删改查
 //获取全部
 @GetMapping
 public Result getList() {
     List<Comments> comments = commentsService.getList();

     return Result.success(comments);
 }
@GetMapping("/amount")
public Result getAmount() {
        //总共有多少条数据
     Integer number = commentsService.getAmount();
   return Result.success(number);
}
//通过id 获取指定数据
 @Transactional
 @GetMapping("/{id}")
 public Result getByid(@PathVariable Integer id) {
     log.info("获取指定 id: {}", id); // 使用占位符{}来避免日志拼接问题，提升性能
     Comments comments = commentsService.getByid(id);

     return Result.success(comments);
 }
 //分页查询
 @GetMapping("/getPaginatedList/{index}/{pages}")
 public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
     List<Comments> list = commentsService.getPaginatedList(index,pages);
     return Result.success(list);
 }
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
     log.info("删除指定ID");
     log.info(String.valueOf(id));

     commentsService.deleteByid(id);

     return Result.success();
 }
//Get comments 获取一级评论也就是 被评论者为空的
 @GetMapping("/getComments/{id}")
 public Result getComments(@PathVariable Integer id){

     List<Comments> comments = commentsService.getComments(id);

     return Result.success(comments);
 }

//分页查询 ----- 一级评论评论分页
@GetMapping("/getComments/getPaginatedList/{id}/{index}/{pages}")
public Result getFristPaginatedList(@PathVariable Integer id,@PathVariable Integer index , @PathVariable Integer pages){

    List<Comments> comments = commentsService.getFristPaginatedList(id,index,pages);

    return Result.success(comments);
}

//Get comments 获取二级评论
 @PostMapping("/getSecondComments")
 public Result getSecondComments(@RequestBody Comments comments){
     log.info("传递对象信息-文章评论！！{}",comments.toString());
     List<Comments> commentslist = commentsService.getSecondComments(comments);

     return Result.success(commentslist);
 }
 //获取用户自己的所有评论
 @GetMapping("/getUserComments/{id}")
 public  Result getUserComments(@PathVariable Integer id){
     List<Comments> list = commentsService.getUserComments(id);
     return Result.success(list);
 }
 @GetMapping("/getThirdComments/{id}")
 public Result getThirdComments(@PathVariable Integer id){
     List<Comments> list = commentsService.getThirdComments(id);

     return Result.success(list);
 }

    //获取指定文章的所有评论
 @GetMapping("/getArticleComments/{id}")
 public  Result getArticleComments(@PathVariable Integer id){
     List<Comments> list = commentsService.getArticleComments(id);
     return Result.success(list);
 }
 @PostMapping("/getByUserComment")
 public  Result getByUserComment(@RequestBody Comments comments){
     log.info("数据总览{}",comments.toString());
     Comments comment = commentsService.getByUserComment(comments);
     return Result.success(comment);
 }
 @PostMapping
 public Result insert(@RequestBody Comments comments) {
     log.info("请求插入{} ",comments.toString());
     commentsService.insert(comments);
     return Result.success();
 }
//更新数据
 @PutMapping
 public Result updateByid(@RequestBody Comments comments){
     log.info("请求更新数据" + comments);
    commentsService.updateByid(comments);
     return Result.success();
   }
    @PutMapping("/updatemodifiedValueByid")
public Result updatemodifiedValueByid(@RequestBody Comments comments){
    log.info("请求更新数据" + comments);
    commentsService.updatemodifiedValueByid(comments);
    return Result.success();
}
}
