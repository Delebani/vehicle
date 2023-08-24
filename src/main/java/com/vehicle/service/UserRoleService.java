package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.UserRoleMapper;
import com.vehicle.po.UserRolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRolePo> {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public void delByUserId(Long userId) {
        super.remove(Wrappers.lambdaQuery(UserRolePo.class).eq(UserRolePo::getUserId, userId));
    }

    public void delByRoleId(Long roleId) {
        super.remove(Wrappers.lambdaQuery(UserRolePo.class).eq(UserRolePo::getRoleId, roleId));
    }

    public List<UserRolePo> selectByUserId(Long userId) {
        LambdaQueryWrapper<UserRolePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserRolePo::getUserId, userId);
        return super.list(queryWrapper);
    }
}
