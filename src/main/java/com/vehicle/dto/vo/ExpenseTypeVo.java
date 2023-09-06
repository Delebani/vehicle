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
@ApiModel(value = "VehicleTypeVo 对象", description = "VehicleTypeVo 响应对象")
public class ExpenseTypeVo implements Serializable {

    private static final long serialVersionUID = 849377807431498746L;

    private Long id;

    private String typeName;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
