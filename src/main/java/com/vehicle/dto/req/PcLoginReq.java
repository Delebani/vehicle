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
@ApiModel(value = "PcLoginReq 对象", description = "PcLoginReq 请求对象")
public class PcLoginReq implements Serializable {

    private static final long serialVersionUID = -2417397929384201052L;

    @ApiModelProperty("手机号")
    @NotBlank(message = "请填写手机号码")
    private String mobile;

    @ApiModelProperty("密码")
    @NotBlank(message = "请填写密码")
    private String password;
}
