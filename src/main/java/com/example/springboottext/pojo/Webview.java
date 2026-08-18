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
 * 网站浏览量
 * </p>
 *
 * @author jianglei
 * @since 2024-11-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Webview implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String ip;

    private LocalDateTime time;

    private String address;
}
