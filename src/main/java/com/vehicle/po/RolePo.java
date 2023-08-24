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
@TableName("role")
@Data
public class RolePo implements Serializable {

    private static final long serialVersionUID = 4085499043073378609L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String roleName;


    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
