package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
/**
 * <p>
 * 文章表
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 总点赞数
     */
    private Integer totalLikes;

    /**
     * 总收藏数
     */
    private Integer totalFavorites;
    /**
     * 总浏览量
     */
    private Integer pageView;
    /**
     * 文章内容
     */
    private String content;

    private LocalDateTime creativeTime;

    private LocalDateTime updataTime;

    private String title;

    private Integer comments;

    /**
     * 文章分类-具体前往文章分类表(article_categories)看
     */
    private Integer articleBlockId;

    /**
     *
     *  video_adreess 视频地址
     */
    private String videoAdress;
    /**
     *
     *  category 类别  1：帖子 2 ： 图片   3： 视频
     */
    private  Integer category;

    private Boolean isTop;

    private String articleImg;
}
