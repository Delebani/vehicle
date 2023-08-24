package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.vehicle.mapper.RoleMenuMapper;
import com.vehicle.po.RoleMenuPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenuPo> {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    public void delByRoleId(Long roleId) {
        super.remove(Wrappers.lambdaQuery(RoleMenuPo.class).eq(RoleMenuPo::getRoleId, roleId));
    }

    public void delByMenuId(Long menuId) {
        super.remove(Wrappers.lambdaQuery(RoleMenuPo.class).eq(RoleMenuPo::getMenuId, menuId));
    }

    public List<RoleMenuPo> findByRoleIdin(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<RoleMenuPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(RoleMenuPo::getRoleId, roleIdList);
        return super.list(queryWrapper);
    }
}
