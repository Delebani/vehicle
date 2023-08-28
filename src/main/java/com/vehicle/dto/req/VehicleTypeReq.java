package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/28 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "VehicleTypeReq 对象", description = "VehicleTypeReq 请求对象")
public class VehicleTypeReq implements Serializable {

    private static final long serialVersionUID = 8421646837918512588L;

    private Long id;

    @NotBlank(message = "请填写类型名称")
    private String typeName;
}
