package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Baomidou
 * @since 2024-06-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    private Integer articleId;

    private Integer userId;

    private Integer repliedUserId;

    private String commentText;

    private LocalDateTime commentTime;

    private Integer upvotes;

    private Integer downvotes;

    private Long reviewerId;

}
