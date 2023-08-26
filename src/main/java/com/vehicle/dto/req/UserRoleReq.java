package com.vehicle.dto.req;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/26 23:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserRoleReq 对象", description = "UserRoleReq 请求对象")
public class UserRoleReq implements Serializable {

    private static final long serialVersionUID = -7171281806038091102L;

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不可为空")
    private Long id;

    @ApiModelProperty("角色id集合")
    private List<Long> roleIdList = Lists.newArrayList();
}
