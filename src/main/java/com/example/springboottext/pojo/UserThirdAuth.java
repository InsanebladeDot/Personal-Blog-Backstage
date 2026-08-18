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
 * 第三方登陆表
 * </p>
 *
 * @author jianglei
 * @since 2024-09-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_third_auth")
public class UserThirdAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String token;

    /**
     * 用户名
     */
    private String username;

    private String avatarUrl;

    /**
     * 第三方登录的类别名字
     */
    private String loginType;

    /**
     * 第三方登录的唯一标识
     */
    private Integer openid;

    private LocalDateTime createdAt;

    private LocalDateTime updataAt;
}
