package com.example.springboottext.service;

import com.example.springboottext.pojo.Friendlink;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-02
 */
@Service
public interface IFriendlinkService  {

    public List<Friendlink> getList();

    public Friendlink getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Friendlink friendlink);

    public void updateByid(Friendlink friendlink);

    List<Friendlink> getPaginatedList(Integer index, Integer pages);
}
