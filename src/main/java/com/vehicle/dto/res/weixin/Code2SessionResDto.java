package com.vehicle.dto.res.weixin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录凭证校验 返回结果
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Code2SessionResDto extends WxBaseResDto {
    private String openid;
    @JSONField(name = "session_key")
    private String sessionKey;
    private String unionid;
}
