package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.TimeShuttle;
import com.example.springboottext.mapper.TimeShuttleMapper;
import com.example.springboottext.service.ITimeShuttleService;
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
 * @since 2024-08-10
 */
@Service
public class TimeShuttleServiceImpl implements ITimeShuttleService{
     @Autowired
     private TimeShuttleMapper timeShuttlemapper;

     @Override
     public List<TimeShuttle> getList() {
          return timeShuttlemapper.getList();
     }

     @Override
     public TimeShuttle getByid(Integer id) {
              return timeShuttlemapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           timeShuttlemapper.deleteByid(id);
     }

     @Override
     public void insert(TimeShuttle timeShuttle) {
          timeShuttlemapper.insert(timeShuttle);
     }

     @Override
     public void updateByid(TimeShuttle timeShuttle) {
         timeShuttlemapper.updateByid(timeShuttle);
     }
}
