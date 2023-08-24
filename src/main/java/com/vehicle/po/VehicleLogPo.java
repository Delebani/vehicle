package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车辆使用记录表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("vehicle_log")
@Data
public class VehicleLogPo implements Serializable {


    private static final long serialVersionUID = -5657346222499315341L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long applyId;

    private Long vehicleTypeId;

    private Long vehicleId;

    private LocalDateTime applyTime;

    private String dest;

    private Integer applyReason;

    private String remark;

    private Long approveId;

    private Integer approveState;

    private LocalDateTime approveTime;

    private Long driverUserId;

    private LocalDateTime returnTime;

    private BigDecimal mileage;


    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
