package com.vehicle.dto.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserVo 对象", description = "UserVo 响应对象")
public class UserVo implements Serializable {

    private static final long serialVersionUID = -633662079996532438L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("用户类型：1-管理员；2-用车人员；3-司机；4-普通用户；10-其他")
    private String type;

    @ApiModelProperty("姓别")
    private String sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("头像")
    private String headUrl;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("职务")
    private String duty;

    @ApiModelProperty("岗位")
    private String post;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("微信id")
    private String wechatId;

    private String token;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;

    private List<Long> roleIdList = Lists.newArrayList();;
}
