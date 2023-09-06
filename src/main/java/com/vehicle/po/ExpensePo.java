package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 报销单
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("expense")
@Data
public class ExpensePo implements Serializable {


    private static final long serialVersionUID = -5062633591065501573L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String expenseNo;

    private Long vehicleId;

    private Long expenseUserId;

    private LocalDateTime expenseTime;

    private Long expenseTypeId;

    private String invoiceUrl;

    private BigDecimal amount;

    private String remark;

    private Long approveId;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
