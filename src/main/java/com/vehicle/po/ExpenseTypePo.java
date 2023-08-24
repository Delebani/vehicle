package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 报销类型表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("expense_type")
@Data
public class ExpenseTypePo implements Serializable {


    private static final long serialVersionUID = 2894771158791907799L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String typeName;

}
