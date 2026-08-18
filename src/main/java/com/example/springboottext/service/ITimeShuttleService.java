package com.example.springboottext.service;

import com.example.springboottext.pojo.TimeShuttle;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-10
 */
@Service
public interface ITimeShuttleService  {

    public List<TimeShuttle> getList();

    public TimeShuttle getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(TimeShuttle timeShuttle);

    public void updateByid(TimeShuttle timeShuttle);
 }
