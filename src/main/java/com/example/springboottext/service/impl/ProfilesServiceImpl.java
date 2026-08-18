package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Profiles;
import com.example.springboottext.mapper.ProfilesMapper;
import com.example.springboottext.service.IProfilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-23
 */
@Service
public class ProfilesServiceImpl implements IProfilesService{
     @Autowired
     private ProfilesMapper profilesmapper;

     @Override
     public List<Profiles> getList() {
          return profilesmapper.getList();
     }

     @Override
     public Profiles getByid(Integer id) {
              return profilesmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           profilesmapper.deleteByid(id);
     }

     @Override
     public void insert(Profiles profiles) {
          profilesmapper.insert(profiles);
     }

     @Override
     public void updateByid(Profiles profiles) {
         profilesmapper.updateByid(profiles);
     }

     @Override
     public void deleteUserByid(Integer id) {
          profilesmapper.deleteUserByid(id);
     }
}
