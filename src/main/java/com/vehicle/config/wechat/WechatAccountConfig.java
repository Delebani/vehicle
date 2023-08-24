package com.vehicle.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weixin")
public class WechatAccountConfig {

    //公众号appid
    private String appid;

    //公众号appSecret
    private String secret;
}
