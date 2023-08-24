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
@ApiModel(value = "UserPageReq 对象", description = "UserPageReq 请求对象")
public class UserPageReq extends AbstractPaging {

    private static final long serialVersionUID = 1278608468858801101L;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("人员类型：1-管理员；2-用车人员；3-司机；4-普通用户；10-其他")
    private Integer type;
}
