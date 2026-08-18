package com.example.springboottext.service;

import com.example.springboottext.pojo.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-05
 */
@Service
public interface IUsersService  {

    public List<Users> getList();

    public Users getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Users users);

    public void updateByid(Users users);

    Users Loginfind(Users users) throws Exception;

    Users getusername(String name);

    Users getOpenTypeByid(Users user);

    List<Users> getPaginatedList(Integer index, Integer pages);

    Integer getAmount();
}
