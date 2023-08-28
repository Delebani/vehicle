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
@ApiModel(value = "VehiclePageReq 对象", description = "VehiclePageReq 请求对象")
public class VehiclePageReq extends AbstractPaging {

    private static final long serialVersionUID = 7866798566489352834L;

    @ApiModelProperty("车牌号")
    private String plateNo;

    @ApiModelProperty("车辆类型")
    private Long vehicleTypeId;

    @ApiModelProperty("车辆状态：0-未出车：1-已出车")
    private Integer state;
}
