package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.dto.req.UserAddressPageReq;
import com.vehicle.dto.req.UserAddressReq;
import com.vehicle.dto.vo.UserAddressVo;
import com.vehicle.mapper.UserAddressMapper;
import com.vehicle.po.UserAddressPo;
import com.vehicle.transform.UserAddressTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/9/2 2:07
 */
@Slf4j
@Service
public class UserAddressService extends ServiceImpl<UserAddressMapper, UserAddressPo> {
    public void saveOrUpdate(UserAddressReq req) {
        UserAddressPo po = UserAddressTransform.INSTANCE.req2Po(req);
        po.setUserId(UserHolder.get().getId());
        super.saveOrUpdate(po);
    }

    public void delById(Long id) {
        super.removeById(id);
    }

    public UserAddressVo view(Long id) {
        UserAddressPo po = super.getById(id);
        return UserAddressTransform.INSTANCE.po2Vo(po);
    }

    public Page<UserAddressVo> pages(UserAddressPageReq req) {
        LambdaQueryWrapper<UserAddressPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserAddressPo::getUserId, UserHolder.get().getId());
        queryWrapper.like(StringUtils.isNotBlank(req.getAddress()), UserAddressPo::getAddress, req.getAddress());
        Page<UserAddressPo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);
        return UserAddressTransform.INSTANCE.poPage2VoPage(poPage);
    }

    public List<UserAddressVo> all() {
        return UserAddressTransform.INSTANCE.poList2VoList(super.list());
    }
}
