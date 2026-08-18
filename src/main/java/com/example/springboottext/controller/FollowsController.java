package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IFollowsService;
import com.example.springboottext.pojo.Follows;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 关注表 前端控制器
    * </p>
*
* @author Baomidou
* @since 2024-07-17
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/follows")
public class FollowsController {
@Qualifier("followsServiceImpl")
@Autowired
private IFollowsService followsService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Follows> follows = followsService.getList();

    return Result.success(follows);
    }
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Follows follows = followsService.getByid(id);

    return Result.success(follows);
 }
 @GetMapping("/getIsconcerned")
 public Result getIsconcerned(Integer followerId,Integer followingId) {
    log.info("数据总览{}, {}", followerId, followingId);
    Follows follows = followsService.getIsconcerned(followingId,followerId);
        // 调用服务层获取数据
     return Result.success(follows);
 }
 @GetMapping("/getfunList/{id}")
 public Result getfunList(@PathVariable Integer id) {
    log.info("数据总览{}", id);
    List<Follows> list = followsService.getfunList(id);
        // 调用服务层获取数据
     return Result.success(list);
}
 @GetMapping("/getMyconcernedList/{id}")
 public Result getMyconcernedList(@PathVariable Integer id) {
    log.info("数据总览{}, {}", id);
    List<Follows> list = followsService.getMyconcernedList(id);
        // 调用服务层获取数据
     return Result.success(list);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    followsService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Follows follows) {
    followsService.insert(follows);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Follows follows){
    log.info("请求更改 "+follows);
    followsService.updateByid(follows);
    return Result.success();
    }
}

