package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Praise;
import com.example.springboottext.mapper.PraiseMapper;
import com.example.springboottext.service.IPraiseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-03
 */
@Service
public class PraiseServiceImpl implements IPraiseService{
     @Autowired
     private PraiseMapper praisemapper;

     @Override
     public List<Praise> getList() {
          return praisemapper.getList();
     }

     @Override
     public Praise getByid(Integer id) {
              return praisemapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           praisemapper.deleteByid(id);
     }

     @Override
     public void insert(Praise praise) {
          praisemapper.insert(praise);
     }

     @Override
     public void updateByid(Praise praise) {
         praisemapper.updateByid(praise);
     }

     @Override
     public Praise getPraise(Praise praise) {

          return praisemapper.getPraise(praise);
     }

     @Override
     public List<Praise> getUserList(Integer id) {
          return praisemapper.getUserList(id);
     }
}
