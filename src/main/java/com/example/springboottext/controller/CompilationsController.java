package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.ICompilationsService;
import com.example.springboottext.pojo.Compilations;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 文章合集 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-10-09
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/compilations")
public class CompilationsController {
@Qualifier("compilationsServiceImpl")
@Autowired
private ICompilationsService compilationsService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Compilations> compilations = compilationsService.getList();

    return Result.success(compilations);
    }
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Compilations compilations = compilationsService.getByid(id);

    return Result.success(compilations);
 }
 //通过userid获取
 @GetMapping("/user/{id}")
 public Result getUserByid(@PathVariable Integer id)  {

     List<Compilations> list = compilationsService.getUserByid(id);

     return Result.success(list);
 }
//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {

    List<Compilations> list = compilationsService.getPaginatedList(index,pages);

    return Result.success(list);
}

 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    compilationsService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Compilations compilations) {
    compilationsService.insert(compilations);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Compilations compilations){
    log.info("请求更改 "+compilations);
    compilationsService.updateByid(compilations);
    return Result.success();
    }
}

