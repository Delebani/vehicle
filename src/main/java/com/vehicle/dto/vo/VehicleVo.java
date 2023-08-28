package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/28 21:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "VehicleVo 对象", description = "VehicleVo 响应对象")
public class VehicleVo implements Serializable {

    private static final long serialVersionUID = -808558719333921710L;

    private Long id;

    private String plateNo;

    private Long vehicleTypeId;

    private String vehicleTypeName;

    private Integer state;

    private String stateName;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
