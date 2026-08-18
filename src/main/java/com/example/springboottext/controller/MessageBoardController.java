package com.example.springboottext.controller;

import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboottext.service.IMessageBoardService;
import com.example.springboottext.pojo.MessageBoard;
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
* @since 2024-07-20
*/
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/messageBoard")
public class MessageBoardController {
    @Qualifier("messageBoardServiceImpl")
    @Autowired
    private IMessageBoardService messageBoardService;

    // 增删改查
    //获取全部
    @GetMapping
    public Result getList() {
        List<MessageBoard> messageBoard = messageBoardService.getList();

        return Result.success(messageBoard);
    }

    //通过id 获取指定数据
    @Transactional
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {

        MessageBoard messageBoard = messageBoardService.getByid(id);

        return Result.success(messageBoard);
    }

    //Get comments 获取一级评论也就是 被评论者为空的
    @GetMapping("/getMessageBoard")
    public Result getMessageBoard() {

        List<MessageBoard> comments = messageBoardService.getMessageBoard();

        return Result.success(comments);
    }
    @GetMapping("/getThirdComments/{id}")
    public Result getThirdComments(@PathVariable Integer id){
        List<MessageBoard> list = messageBoardService.getThirdComments(id);

        return Result.success(list);
    }
    //Get comments 获取二级评论
    @PostMapping("/getSecondMessageBoard")
    public Result getSecondMessageBoard(@RequestBody MessageBoard messageBoard) {
        log.info("传递对象信息" + messageBoard.toString());
        List<MessageBoard> messageBoardList = messageBoardService.getSecondMessageBoard(messageBoard);

        return Result.success(messageBoardList);
    }

    @DeleteMapping("/{id}")
    public Result deleteByid(@PathVariable Integer id) {
        log.info("删除指定ID");
        log.info(String.valueOf(id));

        messageBoardService.deleteByid(id);

        return Result.success();
    }

    @PostMapping
    public Result insert(@RequestBody MessageBoard messageBoard) {
        log.info("数据总览{}", messageBoard.toString());
        messageBoardService.insert(messageBoard);
        return Result.success();
    }

    //更新数据
    @PutMapping
    public Result updateByid(@RequestBody MessageBoard messageBoard) {
        log.info("请求更改 " + messageBoard);
        messageBoardService.updateByid(messageBoard);
        return Result.success();
    }
    @PutMapping("/updatemodifiedValueByid")
    public Result updatemodifiedValueByid(@RequestBody MessageBoard messageBoard) {
        log.info("请求更改 " + messageBoard);
        //仅仅修改增加减少的 点赞 点菜数量
        messageBoardService.updatemodifiedValueByid(messageBoard);
        return Result.success();
    }
}
