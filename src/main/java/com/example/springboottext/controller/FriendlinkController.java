package com.example.springboottext.controller;

import com.example.springboottext.pojo.Artist;
import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IFriendlinkService;
import com.example.springboottext.pojo.Friendlink;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    * 友链 前端控制器
    * </p>
*
* @author jianglei
* @since 2024-10-02
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/friendlink")
public class FriendlinkController {
@Qualifier("friendlinkServiceImpl")
@Autowired
private IFriendlinkService friendlinkService;
    // 增删改查
    //获取全部
@GetMapping
public Result getList() {
    List<Friendlink> friendlink = friendlinkService.getList();

    return Result.success(friendlink);
    }
    //通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Friendlink friendlink = friendlinkService.getByid(id);

    return Result.success(friendlink);
 }
 //分页查询
 @GetMapping("/getPaginatedList/{index}/{pages}")
 public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
    List<Friendlink> list = friendlinkService.getPaginatedList(index,pages);

    return Result.success(list);
}
 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    friendlinkService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Friendlink friendlink) {
    friendlinkService.insert(friendlink);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Friendlink friendlink){
    log.info("请求更改 "+friendlink);
    friendlinkService.updateByid(friendlink);
    return Result.success();
    }
}

