package com.example.springboottext.service;

import com.example.springboottext.pojo.Chatroom;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianglei
 * @since 2025-01-26
 */
@Service
public interface IChatroomService  {

    public List<Chatroom> getList();

    public Chatroom getByid(Integer id);

    public Integer getAmount();
    public List<Chatroom> getPaginatedList(Integer index,Integer pages);

    public void deleteByid(Integer id);

    public void insert(Chatroom chatroom);

    public void updateByid(Chatroom chatroom);
 }
