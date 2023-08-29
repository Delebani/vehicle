package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lijianbing
 * @date 2023/8/29 23:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplyReasonPageReq 对象", description = "ApplyReasonPageReq 响应对象")
public class ApplyReasonPageReq extends AbstractPaging {

    private static final long serialVersionUID = -8580699229242514149L;

    private String reason;
}
