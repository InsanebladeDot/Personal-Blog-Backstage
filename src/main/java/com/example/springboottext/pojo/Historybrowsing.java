package com.example.springboottext.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Baomidou
 * @since 2024-06-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Historybrowsing implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "history_id", type = IdType.AUTO)
    private Integer historyId;

    private Integer userId;

    private Integer publishmessageId;
}
