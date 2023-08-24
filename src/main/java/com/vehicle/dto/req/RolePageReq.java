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
@ApiModel(value = "RolePageReq 对象", description = "RolePageReq 请求对象")
public class RolePageReq extends AbstractPaging {

    private static final long serialVersionUID = -4736258814210104081L;
    
    @ApiModelProperty("角色名称")
    private String roleName;
}
