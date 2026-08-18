package com.example.springboottext.service;

import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.Praise;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 点赞表 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-03
 */
@Service
public interface IPraiseService  {

    public List<Praise> getList();

    public Praise getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Praise praise);

    public void updateByid(Praise praise);

    Praise getPraise(Praise praise);

    List<Praise> getUserList(Integer id);
}
