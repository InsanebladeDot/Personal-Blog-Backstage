package com.example.springboottext.service;

import com.example.springboottext.pojo.Compilations;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 文章合集 服务类
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
@Service
public interface ICompilationsService  {

    public List<Compilations> getList();

    public Compilations getByid(Integer id);

    public List<Compilations> getPaginatedList(Integer index,Integer pages);

    public void deleteByid(Integer id);

    public void insert(Compilations compilations);

    public void updateByid(Compilations compilations);

    List<Compilations> getUserByid(Integer id);
}
