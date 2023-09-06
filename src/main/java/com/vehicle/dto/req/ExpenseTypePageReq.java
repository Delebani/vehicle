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
@ApiModel(value = "ExpenseTypePageReq 对象", description = "ExpenseTypePageReq 请求对象")
public class ExpenseTypePageReq extends AbstractPaging {

    private static final long serialVersionUID = -2345871816825012782L;

    @ApiModelProperty("费用类型")
    private String typeName;

}
