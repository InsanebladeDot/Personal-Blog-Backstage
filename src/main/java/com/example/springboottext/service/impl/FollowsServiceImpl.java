package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Follows;
import com.example.springboottext.mapper.FollowsMapper;
import com.example.springboottext.service.IFollowsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 关注表 服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-17
 */
@Service
public class FollowsServiceImpl implements IFollowsService{
     @Autowired
     private FollowsMapper followsmapper;

     @Override
     public List<Follows> getList() {
          return followsmapper.getList();
     }

     @Override
     public Follows getByid(Integer id) {
              return followsmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           followsmapper.deleteByid(id);
     }

     @Override
     public void insert(Follows follows) {
          followsmapper.insert(follows);
     }

     @Override
     public void updateByid(Follows follows) {
         followsmapper.updateByid(follows);
     }

     @Override
     public Follows getIsconcerned(Integer followingId,Integer followerId) {
          return followsmapper.getIsconcerned(followingId,followerId);
     }

     @Override
     public List<Follows> getMyconcernedList(Integer id) {
          return followsmapper.getMyconcernedList(id);
     }

     @Override
     public List<Follows> getfunList(Integer id) {
          return followsmapper.getfunList(id);
     }
}
