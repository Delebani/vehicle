package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.constants.*;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.ApplyLogPageReq;
import com.vehicle.dto.req.ApplyLogReq;
import com.vehicle.dto.vo.ApplyLogVo;
import com.vehicle.dto.vo.VehicleVo;
import com.vehicle.mapper.ApplyLogMapper;
import com.vehicle.po.ApplyLogPo;
import com.vehicle.po.ApprovePo;
import com.vehicle.po.UserPo;
import com.vehicle.po.VehiclePo;
import com.vehicle.transform.ApplyLogTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class ApplyLogService extends ServiceImpl<ApplyLogMapper, ApplyLogPo> {

    @Autowired
    private ApplyLogMapper applyLogMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ApproveService approveService;


    public List<ApplyLogPo> findByVehicleId(Long vehicleId) {
        LambdaQueryWrapper<ApplyLogPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ApplyLogPo::getVehicleId, vehicleId);
        return super.list(queryWrapper);
    }

    public ApplyLogVo view(Long id) {
        ApplyLogPo po = super.getById(id);
        ApplyLogVo vo = ApplyLogTransform.INSTANCE.po2Vo(po);
        // 申请人，出车司机
        List<UserPo> userPoList = userService.listByIds(Lists.newArrayList(vo.getApplyUserId(), vo.getDriverUserId()));
        Map<Long, String> userMap = userPoList.stream().collect(Collectors.toMap(UserPo::getId, UserPo::getName));
        vo.setApplyUserName(userMap.get(vo.getApplyUserId()));
        vo.setDriverUserName(userMap.get(vo.getDriverUserId()));

        // 出车车辆
        VehicleVo vehicleVo = vehicleService.view(vo.getVehicleId());
        if (null != vehicleVo) {
            vo.setVehicleTypeId(vehicleVo.getVehicleTypeId());
            vo.setVehicleTypeName(vehicleVo.getVehicleTypeName());
        }

        // 审核信息
        if (null != vo.getApproveId()) {
            ApprovePo approvePo = approveService.getById(vo.getApproveId());
            if (null != approvePo) {
                vo.setApproveState(approvePo.getApproveState());
                vo.setApproveStateName(ApproveStateEnum.getByCode(approvePo.getApproveState()).getDesc());
                vo.setApproveTime(approvePo.getApproveTime());
                vo.setApproveNotes(approvePo.getApproveNotes());
            }
        }
        return vo;
    }

    public Page<ApplyLogVo> pages(ApplyLogPageReq req) {
        Page<ApplyLogVo> voPage = new Page<>(req.getCurrent(), req.getPageSize());
        List<ApplyLogVo> voList = applyLogMapper.listByReq(req);
        voList.forEach(o -> {
            o.setApproveStateName(ApproveStateEnum.getByCode(o.getApproveState()).getDesc());
            o.setStateName(ApplyVehcileStateEnum.getByCode(o.getState()).getDesc());
        });
        voPage.setRecords(voList);
        Integer total = applyLogMapper.countByReq(req);
        voPage.setTotal(total);
        return voPage;
    }

    public Integer findByApplyReasonId(Long applyReasonId) {
        LambdaQueryWrapper<ApplyLogPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ApplyLogPo::getApplyReasonId, applyReasonId);
        return super.count(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void submit(ApplyLogReq req) {
        CurrentUser currentUser = UserHolder.get();
        ApplyLogPo po = ApplyLogTransform.INSTANCE.req2po(req);

        po.setApplyUserId(currentUser.getId());
        po.setApplyTime(LocalDateTime.now());

        // 提交审批
        ApprovePo approvePo = new ApprovePo();
        approvePo.setApplyUserId(currentUser.getId());
        approvePo.setApproveType(ApproveTypeEnum.VEHICLE.getCode());
        approvePo.setApproveState(ApproveStateEnum.APPROVING.getCode());
        approveService.save(approvePo);
        po.setApproveId(approvePo.getId());
        super.saveOrUpdate(po);
    }

    public void approvePass(Long approveId) {
        ApplyLogPo po = findByApproveId(approveId);
        if (null == po) {
            throw BizException.error("申请用车审核信息未找到");
        }
        // 分配车辆
        List<VehiclePo> vehiclePoList = vehicleService.findByVehicleTypeIdAndState(po.getVehicleTypeId(), VehicleStateEnum.NO.getCode());
        if (CollectionUtils.isEmpty(vehiclePoList)) {
            throw BizException.error("审批失败，无可用车辆");
        }
        Long vehicleId = vehiclePoList.get(0).getId();
        po.setVehicleId(vehicleId);
        vehicleService.departure(vehicleId);

        // 分配司机
        List<UserPo> userPoList = userService.findByTypeAndStateAndDutyState(UserTypeEnum.DRIVER.getCode(), UserStateEnum.ENABLE.getCode(), DutyStateEnum.ON.getCode());
        if (CollectionUtils.isEmpty(userPoList)) {
            throw BizException.error("审批失败，无可用司机");
        }
        Long driverUserId = userPoList.get(0).getId();
        po.setDriverUserId(driverUserId);
        userService.departure(driverUserId);

        // 给申请用户发送通知 todo

        // 给司机用户发送通知 todo

        po.setState(ApplyVehcileStateEnum.NO.getCode());
        super.updateById(po);
    }

    public ApplyLogPo findByApproveId(Long approveId) {
        LambdaQueryWrapper<ApplyLogPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ApplyLogPo::getApproveId, approveId);
        queryWrapper.last("limit 1");
        return super.getOne(queryWrapper);
    }

    public void departure(Long id) {
        ApplyLogPo po = super.getById(id);
        po.setState(ApplyVehcileStateEnum.ING.getCode());
        super.updateById(po);
    }

    @Transactional(rollbackFor = Exception.class)
    public void returnVehicle(Long id) {
        // 判断位置 TODO
        ApplyLogPo po = super.getById(id);
        po.setMileage(new BigDecimal(0));// 行驶里程
        po.setReturnTime(LocalDateTime.now());
        po.setState(ApplyVehcileStateEnum.COMPLETED.getCode());
        super.updateById(po);
        // 车辆状态
        vehicleService.returnVehicle(po.getVehicleId());

        // 司机排序
        userService.driverSort(po.getDriverUserId());
    }

}
