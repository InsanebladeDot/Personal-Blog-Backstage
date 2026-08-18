package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Historybrowsing;
import com.example.springboottext.mapper.HistorybrowsingMapper;
import com.example.springboottext.service.IHistorybrowsingService;
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
public class HistorybrowsingServiceImpl implements IHistorybrowsingService{
     @Autowired
     private HistorybrowsingMapper historybrowsingmapper;

     @Override
     public List<Historybrowsing> getList() {
          return historybrowsingmapper.getList();
     }

     @Override
     public Historybrowsing getByid(Integer id) {
              return historybrowsingmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           historybrowsingmapper.deleteByid(id);
     }

     @Override
     public void insert(Historybrowsing historybrowsing) {
          historybrowsingmapper.insert(historybrowsing);
     }

     @Override
     public void updateByid(Historybrowsing historybrowsing) {
         historybrowsingmapper.updateByid(historybrowsing);
     }
}
