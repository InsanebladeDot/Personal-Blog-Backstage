package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.ILeaveWordsService;
import com.example.springboottext.pojo.LeaveWords;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author Baomidou
* @since 2024-07-19
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/leaveWords")
public class LeaveWordsController {
@Qualifier("leaveWordsServiceImpl")
@Autowired
private ILeaveWordsService leaveWordsService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Map<String, Object>> leaveWords = leaveWordsService.getList();

    return Result.success(leaveWords);
    }
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    LeaveWords leaveWords = leaveWordsService.getByid(id);

    return Result.success(leaveWords);
 }
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    leaveWordsService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody LeaveWords leaveWords) {
    log.info("插入信息{}",leaveWords.toString());
    leaveWordsService.insert(leaveWords);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody LeaveWords leaveWords){
    log.info("请求更改 "+leaveWords);
    leaveWordsService.updateByid(leaveWords);
    return Result.success();
    }
}

