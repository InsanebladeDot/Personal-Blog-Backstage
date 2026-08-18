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
 * 标签表
 * </p>
 *
 * @author jianglei
 * @since 2024-10-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String tagName;

    /**
     * 对应文章id
     */
    private Integer articleId;

    private LocalDateTime creatime;

    private LocalDateTime updatatime;
}
