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
 * @date 2023/8/1 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserAddressReq 对象", description = "UserAddressReq 请求对象")
public class UserAddressReq implements Serializable {


    private static final long serialVersionUID = -6161438701393991031L;
    private Long id;

    private Long userId;

    @NotBlank(message = "请填写地址")
    private String address;
}
