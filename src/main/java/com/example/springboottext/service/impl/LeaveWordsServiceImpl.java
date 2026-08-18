package com.example.springboottext.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.springboottext.pojo.LeaveWords;
import com.example.springboottext.mapper.LeaveWordsMapper;
import com.example.springboottext.redis.RedisServe;
import com.example.springboottext.service.ILeaveWordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-19
 */
@Slf4j
@Service
public class LeaveWordsServiceImpl implements ILeaveWordsService{
     @Autowired
     private LeaveWordsMapper leaveWordsmapper;
     private static final TypeReference<List<Map<String, Object>>> TYPE_REFERENCE = new TypeReference<List<Map<String, Object>>>() {};
     @Override
     public List<Map<String, Object>>  getList() {
          String key = "leaveWords" + "List";
          // 尝试从 Redis 中获取数据
          String data = RedisServe.getValue(key);
          if (data == null || data.isEmpty()) {
               try {
                    // 如果 Redis 中没有数据，则从数据库获取
                    List<Map<String, Object>> list = leaveWordsmapper.getList();
                    // 将数据转换为 JSON 字符串并保存到 Redis 中
                    String json = JSON.toJSONString(list);
                    RedisServe.setValue(key, json,200);
                    return list;
               } catch (Exception e) {
                    log.error("从数据库获取数据时出错！", e);
                    return Collections.emptyList();
               }
          }
               try {
                    // 反序列化 JSON 数据
                    List<Map<String, Object>> list = JSON.parseObject(data, TYPE_REFERENCE);
                    log.info("从 Redis 获取的数据：{}", list);
                    return list;
               } catch (Exception e) {
                    log.error("反序列化数据时出错！", e);
                    return Collections.emptyList();
               }

     }

     @Override
     public LeaveWords getByid(Integer id) {
              return leaveWordsmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           leaveWordsmapper.deleteByid(id);
     }

     @Override
     public void insert(LeaveWords leaveWords) {
          leaveWordsmapper.insert(leaveWords);
     }

     @Override
     public void updateByid(LeaveWords leaveWords) {
         leaveWordsmapper.updateByid(leaveWords);
     }
}
