package com.vehicle.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.VehicleTypeMapper;
import com.vehicle.po.VehicleTypePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class VehicleTypeService extends ServiceImpl<VehicleTypeMapper, VehicleTypePo> {

    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;
}
