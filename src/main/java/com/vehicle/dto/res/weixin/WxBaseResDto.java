package com.vehicle.dto.res.weixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信接口基本格式
 */
@Getter
@Setter
@ToString
public class WxBaseResDto {
    private Integer errcode;
    private String errmsg;

    public boolean isOk() {
        return errcode == null || errcode == 0;
    }
}
