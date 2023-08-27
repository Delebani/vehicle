package com.vehicle.dto.req;

/**
 * @author lijianbing
 * @date 2023/8/27 22:44
 */

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoleMenuReq 对象", description = "RoleMenuReq 请求对象")
public class RoleMenuReq implements Serializable {

    private static final long serialVersionUID = -243071926593632897L;

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不可为空")
    private Long id;

    @ApiModelProperty("菜单id集合")
    private List<Long> menuIdList = Lists.newArrayList();
}
