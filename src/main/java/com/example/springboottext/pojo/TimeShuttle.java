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
 * 
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("time_shuttle")
public class TimeShuttle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题名

     */
    private String title;

    /**
     * 内容

     */
    private String content;

    private LocalDateTime creatime;

    /**
     * 图片链接
     */
    private String picture;
}
