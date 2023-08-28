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
@ApiModel(value = "VehicleTypePageReq 对象", description = "VehicleTypePageReq 请求对象")
public class VehicleTypePageReq extends AbstractPaging {

    private static final long serialVersionUID = 8129759129013422570L;

    @ApiModelProperty("车辆类型")
    private String typeName;

}
