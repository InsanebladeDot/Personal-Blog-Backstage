package com.example.springboottext.service.impl;

import com.example.springboottext.pojo.Comments;
import com.example.springboottext.mapper.CommentsMapper;
import com.example.springboottext.service.ICommentsService;
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
 * @since 2024-06-30
 */
@Service
public class CommentsServiceImpl implements ICommentsService{
     @Autowired
     private CommentsMapper commentsmapper;

     @Override
     public List<Comments> getList() {
          return commentsmapper.getList();
     }

     @Override
     public Comments getByid(Integer id) {
              return commentsmapper.getByid(id);
      }

     @Override
     public void deleteByid(Integer id) {
           commentsmapper.deleteByid(id);
     }

     @Override
     public void insert(Comments comments) {
          commentsmapper.insert(comments);
     }

     @Override
     public void updateByid(Comments comments) {
          commentsmapper.updateByid(comments);
     }



     @Override
     public List<Comments> getComments(Integer id) {
          return commentsmapper.getComments(id);
     }

     @Override
     public List<Comments> getSecondComments(Comments comments) {
          // 如果 特定的操作下
          if(comments.getReviewerId() == null && comments.getArticleId() == null){
               return commentsmapper.getSecondByUserid(comments);
          }
          return commentsmapper.getSecondComments(comments);
     }

     @Override
     public List<Comments> getUserComments(Integer id) {
          return commentsmapper.getUserComments(id);
     }

     @Override
     public Comments getByUserComment(Comments comments) {
          return commentsmapper.getByUserComment(comments);
     }

     @Override
     public void updatemodifiedValueByid(Comments comments) {
          commentsmapper.updatemodifiedValueByid(comments);
     }

     @Override
     public List<Comments> getArticleComments(Integer id) {
          return commentsmapper.getArticleComments(id);
     }


     @Override
     public List<Comments> getPaginatedList(Integer index, Integer pages){
          Integer offset = (index - 1) * pages;
          //pages 总共要多少页？
          //index 按钮提供的
          return commentsmapper.getPaginatedList(pages,offset);
     }

     @Override
     public List<Comments> getFristPaginatedList(Integer id,Integer index, Integer pages) {
          Integer offset = (index - 1) * pages;

          return commentsmapper.getFristPaginatedList(id,pages,offset);
     }

     @Override
     public Integer getAmount() {
          return commentsmapper.getAmount();
     }

     @Override
     public List<Comments> getThirdComments(Integer id) {
          return commentsmapper.getThirdComments(id);
     }
}
