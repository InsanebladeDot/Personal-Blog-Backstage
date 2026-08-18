package com.example.springboottext.service;

import com.example.springboottext.pojo.Historybrowsing;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-01
 */
@Service
public interface IHistorybrowsingService  {

    public List<Historybrowsing> getList();

    public Historybrowsing getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Historybrowsing historybrowsing);

    public void updateByid(Historybrowsing historybrowsing);
 }
