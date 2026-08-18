package com.example.springboottext.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSubmit implements Serializable {
    private Integer userId;
    private Integer article_categories_id;
    private String content;
    private String title;
    private Integer article_block_id;
}
