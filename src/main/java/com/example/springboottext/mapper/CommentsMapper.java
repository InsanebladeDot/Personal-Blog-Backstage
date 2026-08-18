package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Comments;

import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-06-30
 */
@Mapper
public interface CommentsMapper {
   @Select("Select * from comments")
   List<Comments> getList();
   @Select("Select COUNT(*) from comments")
   Integer getAmount();
   @Select("Select * from comments where comment_id = #{id}")
   Comments getByid(Integer id);
    //   获取一级评论 也即是数据中没有被评论 and 该文章id
   @Select("Select * from comments where article_id = #{id} and replied_user_id is null ")
   List<Comments> getComments(Integer id);
   // 获取所有我被评论的
    // 二级评论1. 被评论者 是谁？replied_user_id = ？ 评论在那个文章上？article_id = ？ 在这个文章的第几行 ？ reviewer_id = ？
   //2. 被评论者 User_id = UserId && 那个文章？article_id = articleId && 那一段？reviewer_id = comment_id
   @Select("select * from comments where  replied_user_id = #{repliedUserId} and reviewer_id = #{reviewerId} and article_id = #{articleId}")
   List<Comments> getSecondComments(Comments comments);
   //3 级评论 这个commentid 相当于锁死在一个文章了 所以直接搜reviewerId = commentid 即可
   @Select("select * from comments where reviewer_id = #{id}")
   List<Comments> getThirdComments(Integer id);

   //获取指定用户的二级评论
   @Select("select * from comments where replied_user_id = #{repliedUserId}")
   List<Comments> getSecondByUserid(Comments comments);

   @Select("Select * from comments where user_id=#{id}")
   List<Comments> getUserComments(Integer id);
   // 获取指定一级评论    那个文章? article_id = articleId  这个文章的哪一行？ reviewer_id = comment_id  自己评论的一级评论者？user_id = replied_user_id
   @Select("Select * from comments where article_id = #{articleId} and comment_id = #{reviewerId} and user_id = #{repliedUserId}")
   Comments getByUserComment(Comments comments);
   //获取指定文章的所有
   @Select("Select * from comments where  article_id = #{id} ")
   List<Comments> getArticleComments(Integer id);
   //分页查询
   @Select("SELECT * FROM artist  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Comments> getPaginatedList(Integer pages,Integer offset);

   @Delete("Delete from comments where comment_id = #{id}")
   void deleteByid(Integer id);

   void insert(Comments comments);

   void updateByid(Comments comments);

   void updatemodifiedValueByid(Comments comments);

   //获取指定文章 的所有评论 并且分离使用
   @Select("Select * from comments where article_id = #{id} and replied_user_id is null  ORDER BY creatime DESC LIMIT #{pages} OFFSET #{offset}")
   List<Comments> getFristPaginatedList(Integer id,Integer pages, Integer offset);


}
