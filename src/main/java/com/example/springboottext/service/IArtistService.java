package com.example.springboottext.service;

import com.example.springboottext.pojo.Article;
import com.example.springboottext.pojo.Artist;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-06
 */
@Service
public interface IArtistService  {

    public List<Artist> getList();

    public Artist getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Artist artist);

    public void updateByid(Artist artist);

    Artist findByid(Integer id);

    void deleteUserByid(Integer id);

    Artist getuserByid(Integer id);

    List<Artist> getPaginatedList(Integer index, Integer pages);
}
