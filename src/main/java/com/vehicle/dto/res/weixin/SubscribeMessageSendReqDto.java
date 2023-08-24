package com.vehicle.dto.res.weixin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * 小程序发送订阅消息请求对象
 *
 * @author lfwer
 * @date 2021-04-16
 */
@Data
@ToString(callSuper = true)
public class SubscribeMessageSendReqDto {

    @JSONField(name = "touser")
    private String toUser;

    @JSONField(name = "template_id")
    private String templateId;

    @JSONField(name = "page")
    private String page;

    @JSONField(name = "data")
    private Object data;

    @JSONField(name = "miniprogram_state")
    private String miniProgramState;

    @JSONField(name = "lang")
    private String lang;
}
