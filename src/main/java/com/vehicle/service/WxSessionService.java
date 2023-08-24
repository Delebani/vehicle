package com.vehicle.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.WxSessionMapper;
import com.vehicle.po.WxSessionPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class WxSessionService extends ServiceImpl<WxSessionMapper, WxSessionPo> {

    @Autowired
    private WxSessionMapper wxSessionMapper;

    public WxSessionPo getByOpenid(String openid) {
        return getOne(Wrappers.lambdaQuery(WxSessionPo.class).eq(WxSessionPo::getOpenid, openid));
    }
}
