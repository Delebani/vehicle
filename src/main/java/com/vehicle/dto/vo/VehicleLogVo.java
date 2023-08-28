package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/28 21:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "VehicleLogVo 对象", description = "VehicleLogVo 响应对象")
public class VehicleLogVo implements Serializable {

    private static final long serialVersionUID = -3840123522079264254L;

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


    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
