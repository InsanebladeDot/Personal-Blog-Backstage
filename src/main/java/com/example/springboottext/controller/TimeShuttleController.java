package com.example.springboottext.controller;

import com.example.springboottext.exception.model.AccountNotFoundException;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.ITimeShuttleService;
import com.example.springboottext.pojo.TimeShuttle;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author Baomidou
* @since 2024-08-10
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/timeShuttle")
public class TimeShuttleController {
@Qualifier("timeShuttleServiceImpl")
@Autowired
private ITimeShuttleService timeShuttleService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {

    List<TimeShuttle> timeShuttle ;
    try {
        timeShuttle = timeShuttleService.getList();
        return Result.success(timeShuttle);
    } catch (Exception e) {
        e.printStackTrace();
        throw new AccountNotFoundException("处理用户时光梭获取时，发生错误");
    }

}
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {
    try {
        TimeShuttle timeShuttle = timeShuttleService.getByid(id);
        return Result.success(timeShuttle);
    }catch (Exception e) {
        e.printStackTrace();
        throw new AccountNotFoundException("处理查询特定时光梭时，发生错误");
    }

 }
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));
    try {
        timeShuttleService.deleteByid(id);
        return Result.success();
    }catch (Exception e) {
        throw new AccountNotFoundException("处理删除时光梭时，发生错误");
    }

 }

@PostMapping
public Result insert(@RequestBody TimeShuttle timeShuttle) {
    try{
        timeShuttleService.insert(timeShuttle);
        return Result.success();
    }catch (Exception e) {
        e.printStackTrace();
        throw new AccountNotFoundException("插入时光梭时，发生错误");
    }
}
    //更新数据
@PutMapping
public Result updateByid(@RequestBody TimeShuttle timeShuttle){
    log.info("请求更改 "+timeShuttle);
    try {
        timeShuttleService.updateByid(timeShuttle);
        return Result.success();
    }catch (Exception e) {
        throw new AccountNotFoundException("更新时光梭时，发生错误");
    }
    }
}

