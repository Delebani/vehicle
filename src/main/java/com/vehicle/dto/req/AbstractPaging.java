package com.vehicle.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页
 */
@Data
public abstract class AbstractPaging implements Serializable {

    private static final long serialVersionUID = -7654473309656261066L;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    protected Long current = 1L;

    /**
     * 每页量
     */
    @ApiModelProperty(value = "每页量")
    protected Long pageSize = 10L;
}
