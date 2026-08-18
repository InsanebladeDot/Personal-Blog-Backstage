package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IProfilesService;
import com.example.springboottext.pojo.Profiles;
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
* @since 2024-08-23
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/profiles")
public class ProfilesController {
@Qualifier("profilesServiceImpl")
@Autowired
private IProfilesService profilesService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Profiles> profiles = profilesService.getList();

    return Result.success(profiles);
    }
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Profiles profiles = profilesService.getByid(id);

    return Result.success(profiles);
 }
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    profilesService.deleteByid(id);

    return Result.success();
}
//通过user_id 删除指定数据
@DeleteMapping("/user/{id}")
public Result deleteUserByid(@PathVariable Integer id){
    log.info("删除指定User_ID");
    log.info(String.valueOf(id));
    profilesService.deleteUserByid(id);
    return Result.success();
}

@PostMapping
public Result insert(@RequestBody Profiles profiles) {
    profilesService.insert(profiles);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Profiles profiles){
    log.info("请求更改 "+profiles);
    profilesService.updateByid(profiles);
    return Result.success();
    }
}

