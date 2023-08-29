package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/29 23:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplyReasonReq 对象", description = "ApplyReasonReq 响应对象")
public class ApplyReasonReq implements Serializable {

    private static final long serialVersionUID = 3227643308819438764L;

    private Long id;

    @NotBlank(message = "请填写申请原因")
    private String reason;
}
