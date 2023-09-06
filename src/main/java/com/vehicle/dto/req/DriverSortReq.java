package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lijianbing
 * @date 2023/9/5 23:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DriverSortReq 对象", description = "DriverSortReq 响应对象")
public class DriverSortReq implements Serializable {

    private static final long serialVersionUID = -3983761977417439676L;

    @NotNull(message = "用户id不可为空")
    private Long id;

    @NotNull(message = "序号不可为空")
    private Integer userSort;
}