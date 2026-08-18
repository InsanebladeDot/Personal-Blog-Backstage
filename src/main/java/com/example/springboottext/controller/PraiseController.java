package com.example.springboottext.controller;

import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IPraiseService;
import com.example.springboottext.pojo.Praise;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 点赞表 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-10-03
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/praise")
public class PraiseController {
@Qualifier("praiseServiceImpl")
@Autowired
private IPraiseService praiseService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Praise> praise = praiseService.getList();

    return Result.success(praise);
}
@GetMapping("/userid/{id}")
public Result getUserList(@PathVariable Integer id) {
   List<Praise> praise = praiseService.getUserList(id);

   return Result.success(praise);
}
//通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Praise praise = praiseService.getByid(id);

    return Result.success(praise);
 }
//获取指定的 点赞数据
@PostMapping("/getPraise")
public Result getPraise(@RequestBody Praise praise){
    log.info("传递对象信息-点赞or 点踩！！{}", praise.toString());
    Praise praiseitem = praiseService.getPraise(praise);

    return Result.success(praiseitem);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    praiseService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Praise praise) {
    log.info("插入消息", praise.toString());
    praiseService.insert(praise);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Praise praise){
    log.info("请求更改 "+praise);
    praiseService.updateByid(praise);
    return Result.success();
    }
}

