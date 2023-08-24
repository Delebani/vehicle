package com.vehicle.dto.res.weixin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * 小程序全局唯一后台接口调用凭据 返回结果
 */
@Data
@ToString(callSuper = true)
public class AccessTokenResDto extends WxBaseResDto {

    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "expires_in")
    private Long expiresIn;
}
