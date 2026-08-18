package com.example.springboottext.controller;

import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IChatroomService;
import com.example.springboottext.pojo.Chatroom;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author jianglei
* @since 2025-01-26
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/chatroom")
public class ChatroomController {
@Qualifier("chatroomServiceImpl")
@Autowired
private IChatroomService chatroomService;
// 增删改查
//获取全部
@GetMapping
public Result getList() {
    List<Chatroom> chatroom = chatroomService.getList();

    return Result.success(chatroom);
}
@GetMapping("/amount")
public Result getAmount() {
    //总共有多少条数据
    Integer number = chatroomService.getAmount();

    return Result.success(number);
}
//通过id 获取指定数据
@Transactional
@GetMapping("/{id}")
public Result getByid(@PathVariable Integer id)  {

    Chatroom chatroom = chatroomService.getByid(id);

    return Result.success(chatroom);
}
//分页查询
@GetMapping("/getPaginatedList/{index}/{pages}")
public Result getPaginatedList(@PathVariable Integer index , @PathVariable Integer pages) {
    //Index 代表着：你点击 1-n 的按钮传递的值 表示1页 还是n页
    //pages 代表着：一页你要多少个内容？
    List<Chatroom> list = chatroomService.getPaginatedList(index,pages);

    return Result.success(list);
}

 @DeleteMapping("/{id}")
 public Result deleteByid(@PathVariable Integer id){
    log.info("删除指定ID");
    log.info(String.valueOf(id));

    chatroomService.deleteByid(id);

    return Result.success();
    }

@PostMapping
public Result insert(@RequestBody Chatroom chatroom) {

    log.info("发送的聊天室数据{}",chatroom.toString());

    chatroomService.insert(chatroom);
    return Result.success();
    }
    //更新数据
@PutMapping
public Result updateByid(@RequestBody Chatroom chatroom){
    log.info("请求更改 "+chatroom);
    chatroomService.updateByid(chatroom);
    return Result.success();
    }
}

