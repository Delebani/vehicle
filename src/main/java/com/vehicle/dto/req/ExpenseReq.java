package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/28 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ExpenseReq 对象", description = "ExpenseReq 请求对象")
public class ExpenseReq implements Serializable {


    private static final long serialVersionUID = 1359543605214358655L;

    @NotNull(message = "请填写报销车辆")
    private Long vehicleId;

    private Long applyUserId;

    private LocalDateTime expenseTime;

    @NotNull(message = "请填写费用类型")
    private Long expenseTypeId;

    private String invoiceUrl;

    @NotNull(message = "请填写费用金额")
    private BigDecimal amount;

    private String remark;
}
