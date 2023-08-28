package com.vehicle.dto.req;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
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
@ApiModel(value = "UserEnableFreezeReq 对象", description = "UserEnableFreezeReq 请求对象")
public class UserEnableFreezeReq implements Serializable {

    private static final long serialVersionUID = -9032711153989413084L;

    @ApiModelProperty("用户id集合")
    @NotEmpty(message = "用户集合不可为空")
    private List<Long> userIdList = Lists.newArrayList();

    @ApiModelProperty("账号状态")
    @NotNull(message = "账号状态不可为空")
    private Integer state;
}
