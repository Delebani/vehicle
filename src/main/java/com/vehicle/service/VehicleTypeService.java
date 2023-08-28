package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.VehicleTypePageReq;
import com.vehicle.dto.req.VehicleTypeReq;
import com.vehicle.dto.vo.VehicleTypeVo;
import com.vehicle.mapper.VehicleTypeMapper;
import com.vehicle.po.VehiclePo;
import com.vehicle.po.VehicleTypePo;
import com.vehicle.transform.VehicleTypeTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class VehicleTypeService extends ServiceImpl<VehicleTypeMapper, VehicleTypePo> {

    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;

    @Autowired
    @Lazy
    private VehicleService vehicleService;

    public void saveOrUpdate(VehicleTypeReq req) {
        VehicleTypePo po = VehicleTypeTransform.INSTANCE.req2Po(req);
        LambdaQueryWrapper<VehicleTypePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VehicleTypePo::getTypeName, req.getTypeName());
        queryWrapper.last("limit 1");
        VehicleTypePo namePo = super.getOne(queryWrapper);
        if (null == req.getId() && null != namePo) {
            throw BizException.error("该车辆类型已存在");
        }
        if (null != req.getId() && null != namePo && req.getId() != namePo.getId()) {
            throw BizException.error("该车辆类型已存在");
        }
        super.saveOrUpdate(po);
    }

    public void del(Long id) {
        List<VehiclePo> vehiclePoList = vehicleService.findByVehicleTypeId(id);
        if (CollectionUtils.isNotEmpty(vehiclePoList)) {
            throw BizException.error("该车辆类型已被车辆使用，不可删除");
        }
        super.removeById(id);
    }

    public VehicleTypeVo view(Long id) {
        VehicleTypePo po = super.getById(id);

        return VehicleTypeTransform.INSTANCE.po2Vo(po);
    }

    public Page<VehicleTypeVo> pages(VehicleTypePageReq req) {

        LambdaQueryWrapper<VehicleTypePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getTypeName()), VehicleTypePo::getTypeName, req.getTypeName());
        Page<VehicleTypePo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);

        return VehicleTypeTransform.INSTANCE.poPage2VoPage(poPage);
    }

    public List<VehicleTypeVo> all() {
        List<VehicleTypePo> poList = super.list();
        return VehicleTypeTransform.INSTANCE.poList2VoList(poList);
    }
}
