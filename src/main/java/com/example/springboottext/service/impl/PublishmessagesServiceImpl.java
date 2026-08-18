package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Publishmessages;
import com.example.springboottext.mapper.PublishmessagesMapper;
import com.example.springboottext.service.IPublishmessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-01
 */
@Service
public class PublishmessagesServiceImpl implements IPublishmessagesService{
     @Autowired
     private PublishmessagesMapper publishmessagesmapper;

     @Override
     public List<Publishmessages> getList() {
          return publishmessagesmapper.getList();
     }

     @Override
     public Publishmessages getByid(Integer id) {
              return publishmessagesmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           publishmessagesmapper.deleteByid(id);
     }

     @Override
     public void insert(Publishmessages publishmessages) {
          publishmessagesmapper.insert(publishmessages);
     }

     @Override
     public void updateByid(Publishmessages publishmessages) {
         publishmessagesmapper.updateByid(publishmessages);
     }
}
