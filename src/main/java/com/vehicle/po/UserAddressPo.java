package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("user_address")
@Data
public class UserAddressPo implements Serializable {

    private static final long serialVersionUID = -1419626903767535824L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String address;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
