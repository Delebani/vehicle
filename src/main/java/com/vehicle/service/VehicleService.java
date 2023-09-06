package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.constants.VehicleStateEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.VehiclePageReq;
import com.vehicle.dto.req.VehicleReq;
import com.vehicle.dto.vo.VehicleVo;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.po.ApplyLogPo;
import com.vehicle.po.VehiclePo;
import com.vehicle.po.VehicleTypePo;
import com.vehicle.transform.VehicleTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
@Slf4j
public class VehicleService extends ServiceImpl<VehicleMapper, VehiclePo> {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @Autowired
    @Lazy
    private ApplyLogService applyLogService;

    public void saveOrUpdate(VehicleReq req) {
        VehiclePo po = VehicleTransform.INSTANCE.req2Po(req);
        LambdaQueryWrapper<VehiclePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VehiclePo::getPlateNo, req.getPlateNo());
        queryWrapper.last("limit 1");
        VehiclePo plateNoPo = super.getOne(queryWrapper);
        if (null == req.getId() && null != plateNoPo) {
            throw BizException.error("该车牌号已存在，请确认");
        }
        if (null != req.getId() && null != plateNoPo && req.getId() != plateNoPo.getId()) {
            throw BizException.error("该车牌号已存在，请确认");
        }

        super.saveOrUpdate(po);
    }

    public void del(Long id) {

        List<ApplyLogPo> logPoList = applyLogService.findByVehicleId(id);
        if (CollectionUtils.isNotEmpty(logPoList)) {
            throw BizException.error("车辆存在使用记录，不可删除");
        }
        super.removeById(id);
    }

    public VehicleVo view(Long id) {
        VehiclePo po = super.getById(id);
        VehicleVo vo = VehicleTransform.INSTANCE.po2Vo(po);
        VehicleTypePo typePo = vehicleTypeService.getById(vo.getVehicleTypeId());
        if (null != typePo) {
            vo.setVehicleTypeName(typePo.getTypeName());
        }
        return vo;
    }

    public Page<VehicleVo> page(VehiclePageReq req) {
        LambdaQueryWrapper<VehiclePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getPlateNo()), VehiclePo::getPlateNo, req.getPlateNo());
        queryWrapper.eq(Objects.nonNull(req.getVehicleTypeId()), VehiclePo::getVehicleTypeId, req.getVehicleTypeId());
        queryWrapper.eq(Objects.nonNull(req.getState()), VehiclePo::getState, req.getState());
        Page<VehiclePo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);
        Page<VehicleVo> voPage = VehicleTransform.INSTANCE.poPage2VoPage(poPage);
        if (CollectionUtils.isEmpty(voPage.getRecords())) {
            return voPage;
        }
        Set<Long> typeIdSet = voPage.getRecords().stream().map(VehicleVo::getVehicleTypeId).collect(Collectors.toSet());
        List<VehicleTypePo> typePoList = vehicleTypeService.listByIds(typeIdSet);
        Map<Long, String> typeMap = typePoList.stream().collect(Collectors.toMap(VehicleTypePo::getId, VehicleTypePo::getTypeName));
        voPage.getRecords().forEach(o -> {
            o.setVehicleTypeName(typeMap.get(o.getVehicleTypeId()));
        });
        return voPage;
    }

    public List<VehiclePo> findByVehicleTypeId(Long vehicleTypeId) {
        LambdaQueryWrapper<VehiclePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VehiclePo::getVehicleTypeId, vehicleTypeId);
        return super.list(queryWrapper);
    }

    public List<VehiclePo> findByVehicleTypeIdAndState(Long vehicleTypeId, Integer state) {
        LambdaQueryWrapper<VehiclePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VehiclePo::getVehicleTypeId, vehicleTypeId);
        queryWrapper.eq(VehiclePo::getState, state);
        return super.list(queryWrapper);
    }

    public void departure(Long vehicleId) {
        LambdaUpdateWrapper<VehiclePo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(VehiclePo::getState, VehicleStateEnum.YES.getCode());
        updateWrapper.eq(VehiclePo::getId, vehicleId);
        super.update(updateWrapper);
    }

    public void returnVehicle(Long vehicleId) {
        LambdaUpdateWrapper<VehiclePo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(VehiclePo::getState, VehicleStateEnum.NO.getCode());
        updateWrapper.eq(VehiclePo::getId, vehicleId);
        super.update(updateWrapper);
    }

    public void updateStateById(Long vehicleId, Integer state) {
        LambdaUpdateWrapper<VehiclePo> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(VehiclePo::getState, state);
        updateWrapper.eq(VehiclePo::getId, vehicleId);
        super.update(updateWrapper);
    }

    public List<VehicleVo> all() {
        List<VehiclePo> poList = super.list();
        return VehicleTransform.INSTANCE.poList2VoList(poList);
    }
}
