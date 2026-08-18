package com.example.springboottext.service;

import com.example.springboottext.pojo.Tags;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-05
 */
@Service
public interface ITagsService  {

    public List<Tags> getList();

    public Tags getByid(Integer id);

    public List<Tags> getPaginatedList(Integer index,Integer pages);

    public void deleteByid(Integer id);

    public void insert(Tags tags);

    public void updateByid(Tags tags);

    void insertList(Integer id, List<String> list);

    List<Tags> getArticleByid(Integer id);

    List<String> getHotTag();

    List<Tags> getDistinctList();
}
