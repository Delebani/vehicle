package com.vehicle.base.cas;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = -6811355235037006250L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户类型")
    private Integer type;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "微信id")
    private String wechatId;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用户角色集合")
    private List<Integer> roleIdList = Lists.newArrayList();
}
