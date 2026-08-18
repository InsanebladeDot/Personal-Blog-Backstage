package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Friendlink;
import com.example.springboottext.mapper.FriendlinkMapper;
import com.example.springboottext.service.IFriendlinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-02
 */
@Service
public class FriendlinkServiceImpl implements IFriendlinkService{
     @Autowired
     private FriendlinkMapper friendlinkmapper;

     @Override
     public List<Friendlink> getList() {
          return friendlinkmapper.getList();
     }

     @Override
     public Friendlink getByid(Integer id) {
              return friendlinkmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           friendlinkmapper.deleteByid(id);
     }

     @Override
     public void insert(Friendlink friendlink) {
          friendlinkmapper.insert(friendlink);
     }

     @Override
     public void updateByid(Friendlink friendlink) {
         friendlinkmapper.updateByid(friendlink);
     }

     @Override
     public List<Friendlink> getPaginatedList(Integer index, Integer pages) {
          Integer offset = (index - 1) * pages;
          //pages 总共要多少页？
          //index 按钮提供的
          return friendlinkmapper.getPaginatedList(pages,offset);
     }
}
