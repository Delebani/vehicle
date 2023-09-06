package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "v 对象", description = "DriverVo 响应对象")
public class DriverVo implements Serializable {

    private static final long serialVersionUID = 1839779720255368072L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("职务")
    private String duty;

    @ApiModelProperty("岗位")
    private String post;

    @ApiModelProperty("排序")
    private Integer userSort;
}
