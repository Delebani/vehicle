package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "UserAddressPageReq 对象", description = "UserAddressPageReq 请求对象")
public class UserAddressPageReq extends AbstractPaging {

    private String address;
}
