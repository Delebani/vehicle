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
@ApiModel(value = "MenuPageReq 对象", description = "MenuPageReq 请求对象")
public class MenuPageReq extends AbstractPaging {

    private static final long serialVersionUID = 30486105757754346L;

    @ApiModelProperty("菜单名称")
    private String menuName;
}
