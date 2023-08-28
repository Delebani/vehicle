package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.dto.req.VehicleLogPageReq;
import com.vehicle.dto.vo.VehicleLogVo;
import com.vehicle.mapper.VehicleLogMapper;
import com.vehicle.po.VehicleLogPo;
import com.vehicle.transform.VehicleLogTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class VehicleLogService extends ServiceImpl<VehicleLogMapper, VehicleLogPo> {

    @Autowired
    private VehicleLogMapper vehicleLogMapper;


    public List<VehicleLogPo> findByVehicleId(Long vehicleId) {
        LambdaQueryWrapper<VehicleLogPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VehicleLogPo::getVehicleId, vehicleId);
        return super.list(queryWrapper);
    }

    public VehicleLogVo view(Long id) {
        VehicleLogPo po = super.getById(id);
        return VehicleLogTransform.INSTANCE.po2Vo(po);
    }

    public Page<VehicleLogVo> pages(VehicleLogPageReq req) {
        return null;
    }
}
