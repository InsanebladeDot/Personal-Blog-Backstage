package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
/**
 * <p>
 * 
 * </p>
 *
 * @author Baomidou
 * @since 2024-08-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "profile_id", type = IdType.AUTO)
    private Integer profileId;

    private Integer userId;

    private String name;

    private Integer age;

    private String address;

    private String idCard;

    private String school;

    private String lifeExperience;

    /**
     * 1:男
2:女
3:保密

     */
    private Integer sex;
}
