package com.example.springboottext.service;

import com.example.springboottext.pojo.LeaveWords;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-19
 */
@Service
public interface ILeaveWordsService  {

    public  List<Map<String, Object>> getList();

    public LeaveWords getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(LeaveWords leaveWords);

    public void updateByid(LeaveWords leaveWords);
 }
