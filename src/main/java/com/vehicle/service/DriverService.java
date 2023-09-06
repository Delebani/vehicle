package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.vehicle.base.constants.DutyStateEnum;
import com.vehicle.base.constants.UserStateEnum;
import com.vehicle.base.constants.UserTypeEnum;
import com.vehicle.dto.req.DriverSortReq;
import com.vehicle.dto.vo.DriverVo;
import com.vehicle.po.UserPo;
import com.vehicle.transform.UserTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/9/5 23:23
 */
@Service
@Slf4j
public class DriverService {

    @Autowired
    private UserService userService;

    public List<DriverVo> all() {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPo::getType, UserTypeEnum.DRIVER.getCode());
        queryWrapper.eq(UserPo::getState, UserStateEnum.ENABLE.getCode());
        queryWrapper.eq(UserPo::getDutyState, DutyStateEnum.ON.getCode());
        queryWrapper.orderByAsc(UserPo::getUserSort);
        List<UserPo> userPoList = userService.list(queryWrapper);
        return UserTransform.INSTANCE.poList2DriverList(userPoList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<DriverSortReq> reqList) {
        if (CollectionUtils.isEmpty(reqList)) {
            return;
        }
        reqList.forEach(o -> {
            updateSort(o);
        });
    }

    private void updateSort(DriverSortReq req) {
        LambdaUpdateWrapper<UserPo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserPo::getUserSort, req.getUserSort());
        updateWrapper.eq(UserPo::getId, req.getId());
        userService.update(updateWrapper);
    }
}
