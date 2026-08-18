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
 * 
 * </p>
 *
 * @author jianglei
 * @since 2025-01-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chatroom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应的用户id
     */
    private Integer userid;

    /**
     * 用户名字
     */
    private String username;

    /**
     * 用户头像
     */
    private String userImg;

    /**
     * 聊天信息
     */
    private String chatText;

    /**
     * 创建时间
     */
    private LocalDateTime creativeTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
