package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("apply_reason")
@Data
public class ApplyReasonPo implements Serializable {

    private static final long serialVersionUID = 1814926624796332974L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String reason;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
