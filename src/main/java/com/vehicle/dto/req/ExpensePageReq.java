package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/1 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ExpenseTypePageReq 对象", description = "ExpenseTypePageReq 请求对象")
public class ExpensePageReq extends AbstractPaging {

    private static final long serialVersionUID = -2345871816825012782L;

    @ApiModelProperty("报销人")
    private String expenseUserName;

    @ApiModelProperty("车牌号")
    private String plateNo;

    @ApiModelProperty("费用类型")
    private Long expenseTypeId;

    @ApiModelProperty("审核状态")
    private Integer approveState;

    @ApiModelProperty("开始")
    private LocalDateTime start;

    @ApiModelProperty("结束")
    private LocalDateTime end;

}
