package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/2 14:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PcResetReq 对象", description = "PcResetReq 请求对象")
public class PcResetReq implements Serializable {

    private static final long serialVersionUID = 1457321407466993100L;

    @ApiModelProperty("手机号")
    @NotBlank(message = "请填写手机号码")
    private String mobile;

    @ApiModelProperty("验证码")
    @NotBlank(message = "请填写验证码")
    private String code;

    @ApiModelProperty("密码")
    @NotBlank(message = "请填写密码")
    private String password;
}
