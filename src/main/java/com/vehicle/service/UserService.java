package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.constants.Constants;
import com.vehicle.base.constants.DutyStateEnum;
import com.vehicle.base.constants.UserStateEnum;
import com.vehicle.base.constants.UserTypeEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.UserEnableFreezeReq;
import com.vehicle.dto.req.UserPageReq;
import com.vehicle.dto.req.UserReq;
import com.vehicle.dto.req.UserRoleReq;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.mapper.UserMapper;
import com.vehicle.po.RoleMenuPo;
import com.vehicle.po.UserPo;
import com.vehicle.po.UserRolePo;
import com.vehicle.transform.UserTransform;
import com.vehicle.utils.PasswordUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserPo> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;


    public void saveOrUpdate(UserReq req) {
        UserPo userPo = UserTransform.INSTANCE.req2Po(req);
        // 头像
        if (StringUtils.isBlank(userPo.getHeadUrl())) {
            userPo.setHeadUrl(Constants.DEFAULT_HEAD);
        }
        // 密码
        if (StringUtils.isBlank(userPo.getPassword())) {
            userPo.setPassword(Constants.DEFAULT_PASSWORD);
        }
        UserPo mobileUserPo = getByMobile(req.getMobile());
        if (null == req.getId() && null != mobileUserPo) {
            throw BizException.error("该用户手机号已被使用，请确认");
        }
        if (null != req.getId() && null != mobileUserPo && req.getId() != mobileUserPo.getId()) {
            throw BizException.error("用户手机号已被使用，请确认");
        }

        super.saveOrUpdate(userPo);
    }

    public UserVo view(Long id) {
        UserPo po = super.getById(id);
        if (null == po) {
            throw BizException.error("用户不存在，请刷新重试");
        }
        return UserTransform.INSTANCE.po2Vo(po);
    }

    public Page<UserVo> page(UserPageReq req) {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getMobile()), UserPo::getMobile, req.getMobile());
        queryWrapper.like(StringUtils.isNotBlank(req.getName()), UserPo::getName, req.getName());
        queryWrapper.like(ObjectUtils.isNotNull(req.getType()), UserPo::getType, req.getType());
        Page<UserPo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);
        Page<UserVo> voPage = UserTransform.INSTANCE.poPage2VoPage(poPage);
        List<Long> userIdList = voPage.getRecords().stream().map(UserVo::getId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(userIdList)) {
            List<UserRolePo> userRolePoList = userRoleService.findByUserIdIn(userIdList);
            Map<Long, List<Long>> map = userRolePoList.stream().collect(Collectors.groupingBy(UserRolePo::getUserId, Collectors.mapping(UserRolePo::getRoleId, Collectors.toList())));
            voPage.getRecords().forEach(o -> {
                if (CollectionUtils.isNotEmpty(map.get(o.getId()))) {
                    o.setRoleIdList(map.get(o.getId()));
                }
            });
        }
        return voPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(Long id) {
        userRoleService.delByUserId(id);
        super.removeById(id);
    }

    public UserPo getByMobileAndPassword(String mobile, String password) {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery(UserPo.class).eq(UserPo::getMobile, mobile);
        UserPo po = super.getOne(queryWrapper);
        if (null == po) {
            throw BizException.error("该手机号尚未注册，请先注册");
        }
        if (!po.getPassword().equals(PasswordUtils.encryptPassword(password))) {
            throw BizException.error("密码填写错误，请重新输入");
        }
        return po;
    }

    public UserPo getByMobile(String mobile) {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery(UserPo.class).eq(UserPo::getMobile, mobile).last("limit 1");
        return super.getOne(queryWrapper);
    }

    public void updateToken(UserPo po) {
        LambdaUpdateWrapper<UserPo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserPo::getToken, po.getToken());
        updateWrapper.eq(UserPo::getId, po.getId());
        super.update(updateWrapper);
    }

    public List<MenuTreeVo> menuTree() {

        List<MenuTreeVo> menuVoList = Lists.newArrayList();
        CurrentUser currentUser = UserHolder.get();
        List<UserRolePo> userRolePoList = userRoleService.selectByUserId(currentUser.getId());
        if (CollectionUtils.isNotEmpty(userRolePoList)) {
            List<Long> roleIdList = userRolePoList.stream().map(UserRolePo::getRoleId).collect(Collectors.toList());
            List<RoleMenuPo> roleMenuPoList = roleMenuService.findByRoleIdin(roleIdList);
            if (CollectionUtils.isNotEmpty(roleMenuPoList)) {
                List<Long> menuIdList = roleMenuPoList.stream().map(RoleMenuPo::getMenuId).collect(Collectors.toList());
                menuVoList = menuService.findByIdIn(menuIdList);
            }
        }
        return menuVoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRole(UserRoleReq req) {
        Long userId = req.getId();
        userRoleService.delByUserId(userId);
        List<UserRolePo> savePoList = req.getRoleIdList().stream().map(o -> {
            UserRolePo userRolePo = new UserRolePo();
            userRolePo.setUserId(userId);
            userRolePo.setRoleId(o);
            return userRolePo;
        }).collect(Collectors.toList());
        userRoleService.saveBatch(savePoList);
    }

    public UserVo mine() {
        CurrentUser currentUser = UserHolder.get();
        UserPo po = super.getById(currentUser);
        return UserTransform.INSTANCE.po2Vo(po);
    }

    public UserVo saveMine(UserReq req) {
        UserPo po = UserTransform.INSTANCE.req2Po(req);
        super.updateById(po);
        return UserTransform.INSTANCE.po2Vo(po);
    }

    public void enableFreeze(UserEnableFreezeReq req) {
        if (CollectionUtils.isEmpty(req.getUserIdList()) || ObjectUtils.isNull(req.getState())) {
            return;
        }
        LambdaUpdateWrapper<UserPo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserPo::getState, req.getState());
        updateWrapper.in(UserPo::getId, req.getUserIdList());
        super.update(updateWrapper);
    }

    public List<UserPo> findByTypeAndStateAndDutyState(Integer type, Integer state, Integer dutyState) {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPo::getType, type);
        queryWrapper.eq(UserPo::getState, state);
        queryWrapper.eq(UserPo::getDutyState, dutyState);
        queryWrapper.orderByAsc(UserPo::getUserSort);
        return super.list(queryWrapper);
    }

    public void driverSort(Long id) {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPo::getType, UserTypeEnum.DRIVER.getCode());
        queryWrapper.eq(UserPo::getState, UserStateEnum.ENABLE.getCode());
        queryWrapper.eq(UserPo::getDutyState, DutyStateEnum.ON.getCode());
        queryWrapper.orderByDesc(UserPo::getUserSort);
        queryWrapper.last("limit 1");
        UserPo onDutyDriver = super.getOne(queryWrapper);
        Integer userSort = 0;
        if (null != onDutyDriver) {
            userSort = onDutyDriver.getUserSort() + 1;
        }

        LambdaUpdateWrapper<UserPo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserPo::getDutyState, DutyStateEnum.ON.getCode());
        updateWrapper.set(UserPo::getUserSort, userSort);
        updateWrapper.eq(UserPo::getId, id);
        super.update(updateWrapper);
    }

    public void departure(Long id) {
        LambdaUpdateWrapper<UserPo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserPo::getDutyState, DutyStateEnum.OUT.getCode());
        updateWrapper.eq(UserPo::getId, id);
        super.update(updateWrapper);
    }

    public void updateDutyStateById(Long id, Integer dutyState) {
        LambdaUpdateWrapper<UserPo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserPo::getDutyState, dutyState);
        updateWrapper.eq(UserPo::getId, id);
        super.update(updateWrapper);
    }

    public UserVo getByOpenId(String openId) {
        LambdaQueryWrapper<UserPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPo::getOpenId, openId);
        queryWrapper.last("limit 1");
        UserPo po = super.getOne(queryWrapper);
        if(null == po){
            throw BizException.error("用户不存在");
        }
        return UserTransform.INSTANCE.po2Vo(po);
    }
}
