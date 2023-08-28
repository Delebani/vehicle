package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 车辆类型表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("vehicle_type")
@Data
public class VehicleTypePo implements Serializable {


    private static final long serialVersionUID = 2756004677571286759L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String typeName;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
