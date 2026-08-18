package com.example.springboottext.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.MessageBoard;
import com.example.springboottext.mapper.MessageBoardMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.IMessageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-20
 */
@Slf4j
@Service
public class MessageBoardServiceImpl implements IMessageBoardService{
     @Autowired
     private MessageBoardMapper messageBoardmapper;
     private String key = "MessageBoard";

     @Override
     public List<MessageBoard> getList() {
          return messageBoardmapper.getList();
     }

     @Override
     public MessageBoard getByid(Integer id) {
              return messageBoardmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           messageBoardmapper.deleteByid(id);
     }

     @Override
     public void insert(MessageBoard messageBoard) {
          messageBoardmapper.insert(messageBoard);
     }

     @Override
     public void updateByid(MessageBoard messageBoard) {
         messageBoardmapper.updateByid(messageBoard);
     }

     @Override
     public List<MessageBoard> getMessageBoard() {
         return messageBoardmapper.getMessageBoard();
     }

     @Override
     public List<MessageBoard> getSecondMessageBoard(MessageBoard messageBoard) {
          return messageBoardmapper.getSecondMessageBoard(messageBoard);
     }

     @Override
     public void updatemodifiedValueByid(MessageBoard messageBoard) {
          messageBoardmapper.updatemodifiedValueByid(messageBoard);
     }

     @Override
     public List<MessageBoard> getThirdComments(Integer id) {
          return  messageBoardmapper.getThirdComments(id);
     }

}
