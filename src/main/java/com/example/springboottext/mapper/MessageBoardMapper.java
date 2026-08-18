package com.example.springboottext.mapper;

import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.MessageBoard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.*;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-20
 */
public interface MessageBoardMapper {
   @Select("Select * from message_board")
   List<MessageBoard> getList();
   @Select("Select count(*) from message_board")
   Integer getAmount();
   @Select("Select * from message_board where id = #{id}")
   MessageBoard getByid(Integer id);

   @Delete("Delete from message_board where id = #{id}")
   void deleteByid(Integer id);
   @Select("Select * from message_board where replied_user_id is null ")
   List<MessageBoard> getMessageBoard();
   //二级评论
   @Select("select * from message_board where  replied_user_id = #{repliedUserId} and reviewer_id = #{reviewerId}")
   List<MessageBoard> getSecondMessageBoard(MessageBoard messageBoard);

   void insert(MessageBoard messageBoard);

   void updateByid(MessageBoard messageBoard);

   //一级评论
   void updatemodifiedValueByid(MessageBoard messageBoard);

   @Select("Select * from message_board where reviewer_id = #{id}")
   List<MessageBoard> getThirdComments(Integer id);
}

