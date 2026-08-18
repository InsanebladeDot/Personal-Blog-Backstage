package com.example.springboottext.service;

import com.example.springboottext.pojo.Publishmessages;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-01
 */
@Service
public interface IPublishmessagesService  {

    public List<Publishmessages> getList();

    public Publishmessages getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Publishmessages publishmessages);

    public void updateByid(Publishmessages publishmessages);
 }
