package com.example.springboottext.service;

import com.example.springboottext.pojo.Follows;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 关注表 服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-17
 */
@Service
public interface IFollowsService  {

    public List<Follows> getList();

    public Follows getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Follows follows);

    public void updateByid(Follows follows);

    public Follows getIsconcerned(Integer followingId,Integer followerId);

    List<Follows> getMyconcernedList(Integer id);

    List<Follows> getfunList(Integer id);
}
