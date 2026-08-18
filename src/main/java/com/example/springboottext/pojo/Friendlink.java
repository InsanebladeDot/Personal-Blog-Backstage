package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
/**
 * <p>
 * 友链
 * </p>
 *
 * @author jianglei
 * @since 2024-10-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendlink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String linkName;

    private String linkAdress;

    private String linkIntroduce;

    private String linkBackground;

    private String linkMail;

    private Integer userId;
}
