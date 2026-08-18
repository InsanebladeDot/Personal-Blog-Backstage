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
 * 关注表
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follows implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关注者
     */
    private Integer followerId;

    /**
     * 被关注者
     */
    private Integer followingId;

    private LocalDateTime createdTime;

    private LocalDateTime modificationTime;
}
