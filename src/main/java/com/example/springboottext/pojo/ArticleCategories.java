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
 * 文章分类表
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article_categories")
public class ArticleCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 主要分类
     */
    private String mainClassification;

    private String topic;

    private LocalDateTime creatime;

    private LocalDateTime updataTime;
}
