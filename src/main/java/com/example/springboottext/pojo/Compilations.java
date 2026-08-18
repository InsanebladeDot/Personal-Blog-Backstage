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
 * 文章合集
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compilations implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String title;

    private String description;

    private LocalDateTime creatime;

    private LocalDateTime updatetime;

    private Boolean status;

    private String profilephoto;

}
