package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.*;
/**
 * <p>
 * 
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "artist_id", type = IdType.AUTO)
    private Integer artistId;

    private String artistName;

    private Integer userId;

    private String aphorism;

    private Integer identity;

    private Integer level;

    private LocalDateTime creatime;

    private LocalDateTime modificationTime;


}
