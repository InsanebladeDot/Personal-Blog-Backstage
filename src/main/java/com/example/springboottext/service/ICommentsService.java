package com.example.springboottext.service;

import com.example.springboottext.pojo.Comments;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-06-30
 */
@Service
public interface ICommentsService  {

    public List<Comments> getList();

    public Comments getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(Comments comments);

    public void updateByid(Comments comments);

    List<Comments> getComments(Integer id);

    List<Comments> getSecondComments(Comments comments);

    List<Comments> getUserComments(Integer id);

    Comments getByUserComment(Comments comments);

    public void updatemodifiedValueByid(Comments comments);

    List<Comments> getArticleComments(Integer id);

    List<Comments> getPaginatedList(Integer index, Integer pages);

    List<Comments> getFristPaginatedList(Integer id,Integer index,Integer pages);

    Integer getAmount();

    List<Comments> getThirdComments(Integer id);
}
