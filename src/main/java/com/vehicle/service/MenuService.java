package com.vehicle.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.MenuReq;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.mapper.MenuMapper;
import com.vehicle.po.MenuPo;
import com.vehicle.transform.MenuTransform;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuPo> {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    public void saveOrUpdate(MenuReq req) {
        MenuPo po = MenuTransform.INSTANCE.req2Po(req);
        super.saveOrUpdate(po);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delById(Long id) {
        int count = super.count(Wrappers.lambdaQuery(MenuPo.class).eq(MenuPo::getParentId, id));
        if (count > 0) {
            throw BizException.error("该菜单仍有子菜单存在，请先删除子菜单");
        }
        roleMenuService.delByMenuId(id);
        super.removeById(id);
    }

    public List<MenuTreeVo> tree() {
        List<MenuPo> poList = super.list();
        List<MenuTreeVo> voList = MenuTransform.INSTANCE.poList2TreeVoList(poList);
        List<MenuTreeVo> rootList = voList.stream().filter(e -> 0 == e.getParentId()).sorted(Comparator.comparing(MenuTreeVo::getMenuSort)).collect(Collectors.toList());
        rootList = completeChildrenMenu(rootList, voList);
        return rootList;
    }

    /**
     * 补全子级菜单
     *
     * @param parentList
     * @param menuList
     * @return
     */
    private List<MenuTreeVo> completeChildrenMenu(List<MenuTreeVo> parentList, List<MenuTreeVo> menuList) {
        for (MenuTreeVo menuVo : parentList) {
            Long menuId = menuVo.getId();
            // 2.1 找出当前所有子类集合
            List<MenuTreeVo> childrenList = menuList.stream().filter(e -> e.getParentId().equals(menuId)).sorted(Comparator.comparing(MenuTreeVo::getMenuSort)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(childrenList)) {
                completeChildrenMenu(childrenList, menuList);
            }
            menuVo.setChildren(childrenList);
        }
        return parentList;
    }

    public List<MenuTreeVo> findByIdIn(List<Long> menuIdList) {
        List<MenuPo> poList = super.listByIds(menuIdList);
        List<MenuTreeVo> voList = MenuTransform.INSTANCE.poList2TreeVoList(poList);
        List<MenuTreeVo> rootList = voList.stream().filter(e -> 0 == e.getParentId()).sorted(Comparator.comparing(MenuTreeVo::getMenuSort)).collect(Collectors.toList());
        rootList = completeChildrenMenu(rootList, voList);
        return rootList;
    }
}
