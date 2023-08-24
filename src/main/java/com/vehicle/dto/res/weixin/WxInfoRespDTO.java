package com.vehicle.dto.res.weixin;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class WxInfoRespDTO extends WxBaseResDto {

    @ApiModelProperty(value = "会话标识", required = true)
    private String openId;

    @ApiModelProperty(value = "昵称", required = true)
    private String nickName;

    @ApiModelProperty(value = "性别", required = true)
    private Integer sex;

    @ApiModelProperty(value = "省份", required = true)
    private String province;

    @ApiModelProperty(value = "市", required = true)
    private String city;

    @ApiModelProperty(value = "国家", required = true)
    private String country;

    @ApiModelProperty(value = "头像地址", required = true)
    private String headimgurl;

    @ApiModelProperty(value = "头像地址", required = true)
    private String unionid;

    @ApiModelProperty(value = "头像地址", required = true)
    private List<String> privilege;
}