package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/28 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ExpenseTypeReq 对象", description = "ExpenseTypeReq 请求对象")
public class ExpenseTypeReq implements Serializable {

    private static final long serialVersionUID = -5321173271862639415L;
    
    private Long id;

    @NotBlank(message = "请填写类型名称")
    private String typeName;
}
