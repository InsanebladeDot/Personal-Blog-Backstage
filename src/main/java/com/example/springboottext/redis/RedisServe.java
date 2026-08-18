package com.example.springboottext.redis;

import com.alibaba.fastjson.JSON;
import com.example.springboottext.mapper.ArticleCategoriesMapper;
import com.example.springboottext.mapper.ArticleMapper;
import com.example.springboottext.mapper.ArtistMapper;
import com.example.springboottext.pojo.Article;
import com.example.springboottext.service.impl.ArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Component
public class RedisServe {
    private static RedisTemplate<String, Object> redisTemplate;
    @Autowired
    public RedisServe(RedisTemplate<String, Object> redisTemplate) {
        RedisServe.redisTemplate = redisTemplate;
    }

    public static void setValue(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }
    public static void setValue(String key, String value,long time) {
        // 设置过期时间，单位为秒
        Duration duration = Duration.ofSeconds(time);
        redisTemplate.opsForValue().set(key, value,duration);
    }
    public static String getValue(String key) {
        return  (String) redisTemplate.opsForValue().get(key);
    }
    /**
     * 更新 Redis 中指定键的值。
     * 如果键不存在，则会创建新的键值对。
     *
     * @param key   要更新的键
     * @param value 新的值
     */
    public static void updateValue(String key, String value) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            // 如果键不存在，可以选择抛出异常或者直接插入新的键值对
            // 这里选择直接插入新的键值对
            insertValue(key, value);
        }
    }

    /**
     * 插入 Redis 中指定键的新值。
     * 如果键已存在，则不会覆盖原来的值。
     *
     * @param key   要插入的键
     * @param value 要插入的值
     */
    public static void insertValue(String key, String value) {
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            throw new IllegalStateException("Key already exists: " + key);
        }
    }
    //删除指定key
    public static void deleteKey(String key) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 从 Redis 列表中删除指定索引处的元素。
     *
     * @param key   列表的键
     * @param index 要删除元素的索引（从 0 开始）
     */
    public static void removeListIndex(String key, long index) {
        ListOperations<String, Object> listOps = redisTemplate.opsForList();

        // 获取列表长度
        long listLength = listOps.size(key);

        // 检查索引是否有效
        if (index >= 0 && index < listLength) {
            // 删除指定索引处的元素
            listOps.remove(key, 0, listOps.index(key, index));
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index + ", list length: " + listLength);
        }
    }

}