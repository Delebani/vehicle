package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lijianbing
 * @date 2023/8/1 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "VehicleLogPageReq 对象", description = "VehicleLogPageReq 请求对象")
public class VehicleLogPageReq extends AbstractPaging {

    private static final long serialVersionUID = -5760621996269112292L;

    @ApiModelProperty("车牌号")
    private String plateNo;

    @ApiModelProperty("车辆类型")
    private Long vehicleTypeId;

    @ApiModelProperty("审核状态")
    private Integer approveState;
}
