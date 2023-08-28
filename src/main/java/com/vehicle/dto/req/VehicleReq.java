package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/28 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "VehicleReq 对象", description = "VehicleReq 请求对象")
public class VehicleReq implements Serializable {

    private static final long serialVersionUID = 8868703589812315114L;

    private Long id;

    @NotBlank(message = "请填写车牌号")
    private String plateNo;

    @NotNull(message = "请填写车辆类型")
    private Long vehicleTypeId;

    @NotNull(message = "请填写车辆状态")
    private Integer state;
}
