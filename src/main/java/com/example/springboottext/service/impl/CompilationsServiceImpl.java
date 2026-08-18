package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Compilations;
import com.example.springboottext.mapper.CompilationsMapper;
import com.example.springboottext.service.ICompilationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * <p>
 * 文章合集 服务实现类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
@Service
public class CompilationsServiceImpl implements ICompilationsService{
     @Autowired
     private CompilationsMapper compilationsmapper;

     @Override
     public List<Compilations> getList() {
          return compilationsmapper.getList();
     }

     @Override
     public Compilations getByid(Integer id) {
         return compilationsmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           compilationsmapper.deleteByid(id);
     }

     @Override
     public void insert(Compilations compilations) {
          compilationsmapper.insert(compilations);
     }

     @Override
     public void updateByid(Compilations compilations) {
         compilationsmapper.updateByid(compilations);
     }

    @Override
    public List<Compilations> getUserByid(Integer id) {
        return compilationsmapper.getUserByid(id);
    }

    @Override
    public List<Compilations> getPaginatedList(Integer index, Integer pages) {
        Integer offset = (index - 1) * pages;
        //pages 总共要多少页？
        //index 按钮提供的
        return compilationsmapper.getPaginatedList(pages,offset);
    }
}
