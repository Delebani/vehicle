package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.RoleMenuReq;
import com.vehicle.dto.req.RolePageReq;
import com.vehicle.dto.req.RoleReq;
import com.vehicle.dto.vo.RoleVo;
import com.vehicle.mapper.RoleMapper;
import com.vehicle.po.MenuPo;
import com.vehicle.po.RoleMenuPo;
import com.vehicle.po.RolePo;
import com.vehicle.transform.RoleTransform;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePo> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    @Lazy
    private UserRoleService userRoleService;

    public void saveOrUpdate(RoleReq req) {
        super.saveOrUpdate(RoleTransform.INSTANCE.req2Po(req));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delById(Long id) {
        // 1. 删除用户角色关联关系
        userRoleService.delByRoleId(id);
        // 2. 删除角色菜单关联关系
        roleMenuService.delByRoleId(id);

        // 3. 删除角色
        super.removeById(id);
    }

    public RoleVo view(Long id) {
        RolePo po = super.getById(id);
        if (null == po) {
            throw BizException.error("角色不存在，请刷新页面重试");
        }
        return RoleTransform.INSTANCE.po2Vo(po);
    }

    public Page<RoleVo> page(RolePageReq req) {
        LambdaQueryWrapper<RolePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getRoleName()), RolePo::getRoleName, req.getRoleName());
        Page<RolePo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);
        Page<RoleVo> voPage = RoleTransform.INSTANCE.poPage2VoPage(poPage);
        List<RoleVo> list = voPage.getRecords();
        // 角色菜单集合
        Set<Long> roleIdSet = list.stream().map(RoleVo::getId).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(roleIdSet)){
            return voPage;
        }
        List<RoleMenuPo> roleMenuList = roleMenuService.findByRoleIdin(roleIdSet);

        // 去掉父级菜单id
        List<MenuPo> menuList = menuService.listByIds(roleMenuList.stream().map(RoleMenuPo::getMenuId).collect(Collectors.toList()));
        List<Long> menuIdList = menuList.stream().filter(o -> {
            return o.getParentId() != 0;
        }).map(MenuPo::getId).collect(Collectors.toList());

        Map<Long, List<Long>> map = roleMenuList.stream().filter(o -> {
            return menuIdList.contains(o.getMenuId());
        }).collect(Collectors.groupingBy(RoleMenuPo::getRoleId, Collectors.mapping(RoleMenuPo::getMenuId, Collectors.toList())));
        list.forEach(o -> {
            if (CollectionUtils.isNotEmpty(map.get(o.getId()))) {
                o.setMenuIdList(map.get(o.getId()));
            }
        });
        return voPage;
    }

    public List<RoleVo> findAll() {
        List<RolePo> poList = super.list();
        return RoleTransform.INSTANCE.poList2VoList(poList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMenu(RoleMenuReq req) {
        Long roleId = req.getId();
        roleMenuService.delByRoleId(req.getId());
        List<RoleMenuPo> saveList = req.getMenuIdList().stream().map(menuId -> {
            RoleMenuPo po = new RoleMenuPo();
            po.setRoleId(roleId);
            po.setMenuId(menuId);
            return po;
        }).collect(Collectors.toList());
        roleMenuService.saveBatch(saveList);
    }
}
