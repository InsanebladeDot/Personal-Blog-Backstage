package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @since 2024-07-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码:只允许从 JSON 接收(WRITE_ONLY),序列化输出与 toString 均不暴露,防止哈希泄露
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;

    private String username;

    private String phone;

    private String email;

    private String profilephoto;

    //携带第三方标识 openid 和登录type
    private Integer openId;

    private String loginType;

    private LocalDateTime creatime;

    private LocalDateTime updataTime;
}
