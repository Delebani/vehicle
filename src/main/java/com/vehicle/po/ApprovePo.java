package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 车辆使用记录表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("approve")
@Data
public class ApprovePo implements Serializable {

    private static final long serialVersionUID = 338595900540304273L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long applyUserId;

    private Integer approveType;

    private Long approveUserId;

    private String approveUserName;

    private Integer approveState;

    private LocalDateTime approveTime;

    private String approveNotes;


    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
