package com.example.springboottext.service;

import com.example.springboottext.pojo.Comments;
import com.example.springboottext.pojo.MessageBoard;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-20
 */
@Service
public interface IMessageBoardService  {

    public List<MessageBoard> getList();

    public MessageBoard getByid(Integer id);

    public void deleteByid(Integer id);

    public void insert(MessageBoard messageBoard);

    public void updateByid(MessageBoard messageBoard);

    List<MessageBoard> getMessageBoard();

    List<MessageBoard> getSecondMessageBoard(MessageBoard messageBoard);

    void updatemodifiedValueByid(MessageBoard messageBoard);

    List<MessageBoard> getThirdComments(Integer id);
}
