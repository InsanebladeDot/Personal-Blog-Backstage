package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author jianglei
 * @since 2024-10-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Praise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 点赞的是哪一个文章？
     */
    private Integer articleId;

    /**
     * 点赞人 是谁？
     */
    private Integer userId;

    /**
     * 被点赞人是谁？
     */
    private Integer repliedUserId;

    /**
     * 这个的作用是：用于标记被评论者的文章 id 是那一个
     */
    private Integer reviewerId;

    /**
     * 创建时间
     */
    private LocalDateTime creatime;

    /**
     * 更新时间
     */
    private LocalDateTime updatatime;

    /**
     * true : 是点赞
false: 是点踩
     */
    private Boolean  type;
}
