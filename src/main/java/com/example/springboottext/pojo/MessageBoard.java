package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
/**
 * <p>
 * 
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("message_board")
public class MessageBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 一级评论者
     */
    private Integer userId;

    /**
     * 被评论者 为null 表示是一级评论 
     */
    private Integer repliedUserId;

    /**
     * 回复文本
     */
    private String commentText;

    private LocalDateTime commentTime;

    private Integer upvotes;

    private Integer downvotes;

    /**
     * 如果是二级评论 则用来表示是那一段
     */
    private Integer reviewerId;
}
