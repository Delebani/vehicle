package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/28 21:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ExpenseVo 对象", description = "ExpenseVo 响应对象")
public class ExpenseVo implements Serializable {

    private static final long serialVersionUID = 5040476284451968023L;

    private Long id;

    private String expenseNo;

    private Long vehicleId;

    private String plateNo;

    private Long expenseUserId;

    private String expenseUserName;

    private String expenseUserMobile;

    private LocalDateTime expenseTime;

    private Long expenseTypeId;

    private String expenseTypeName;

    private String invoiceUrl;

    private BigDecimal amount;

    private String remark;

    private Long approveId;

    private Long approveUserId;

    private String approveUserName;

    private Integer approveState;

    private String approveStateName;

    private String approveNotes;

    private LocalDateTime approveTime;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
