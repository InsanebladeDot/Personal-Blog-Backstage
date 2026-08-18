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
 * 归档
 * </p>
 *
 * @author jianglei
 * @since 2024-10-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collections implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer articleId;

    private Integer compilationid;

    private Integer userId;

    private LocalDateTime creatime;

    private LocalDateTime updatedtime;

    private String status;
}
