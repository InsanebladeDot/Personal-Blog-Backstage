package com.example.springboottext.service;

import com.example.springboottext.pojo.Profiles;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-23
 */
@Service
public interface IProfilesService  {

    public List<Profiles> getList();

    public Profiles getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Profiles profiles);

    public void updateByid(Profiles profiles);

    void deleteUserByid(Integer id);
}
