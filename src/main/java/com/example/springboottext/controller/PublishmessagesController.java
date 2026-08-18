package com.example.springboottext.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IPublishmessagesService;
import com.example.springboottext.pojo.Publishmessages;
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
* @since 2024-07-01
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/publishmessages")
  public class PublishmessagesController {
 @Qualifier("publishmessagesServiceImpl")
 @Autowired
 private IPublishmessagesService publishmessagesService;
// 增删改查
 //获取全部
 @GetMapping
 public Result getList() {
     List<Publishmessages> publishmessages = publishmessagesService.getList();

     return Result.success(publishmessages);
 }
//通过id 获取指定数据
 @Transactional
 @GetMapping("/{id}")
 public Result getByid(@PathVariable Integer id)  {

 Publishmessages publishmessages = publishmessagesService.getByid(id);

 return Result.success(publishmessages);
 }
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
     log.info("删除指定ID");
     log.info(String.valueOf(id));

     publishmessagesService.deleteByid(id);

     return Result.success();
 }

 @PostMapping
 public Result insert(@RequestBody Publishmessages publishmessages) {
     publishmessagesService.insert(publishmessages);
     return Result.success();
 }
//更新数据
 @PutMapping
 public Result updateByid(@RequestBody Publishmessages publishmessages){
    log.info("请求更改 "+publishmessages);
    publishmessagesService.updateByid(publishmessages);
     return Result.success();
   }
 }

