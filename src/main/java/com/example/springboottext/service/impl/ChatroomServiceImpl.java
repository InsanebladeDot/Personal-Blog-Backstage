package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Chatroom;
import com.example.springboottext.mapper.ChatroomMapper;
import com.example.springboottext.service.IChatroomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2025-01-26
 */
@Service
public class ChatroomServiceImpl implements IChatroomService{
     @Autowired
     private ChatroomMapper chatroommapper;

     @Override
     public List<Chatroom> getList() {
          return chatroommapper.getList();
     }

     @Override
     public Chatroom getByid(Integer id) {
          return chatroommapper.getByid(id);
      }
     @Override
     public Integer getAmount() {
         return chatroommapper.getAmount();
     }

     @Override
     public void deleteByid(Integer id) {
           chatroommapper.deleteByid(id);
     }

     @Override
     public void insert(Chatroom chatroom) {
          chatroommapper.insert(chatroom);
     }

     @Override
     public void updateByid(Chatroom chatroom) {
         chatroommapper.updateByid(chatroom);
     }

    @Override
    public List<Chatroom> getPaginatedList(Integer index, Integer pages) {
        Integer offset = (index - 1) * pages;
        //pages 总共要多少页？
        //index 按钮提供的
        return chatroommapper.getPaginatedList(pages,offset);
    }
}
