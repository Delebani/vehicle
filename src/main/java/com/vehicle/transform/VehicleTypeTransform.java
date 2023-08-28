package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.dto.req.VehicleTypeReq;
import com.vehicle.dto.vo.VehicleTypeVo;
import com.vehicle.po.VehicleTypePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface VehicleTypeTransform {

    VehicleTypeTransform INSTANCE = Mappers.getMapper(VehicleTypeTransform.class);

    VehicleTypePo req2Po(VehicleTypeReq req);

    VehicleTypeVo po2Vo(VehicleTypePo po);

    Page<VehicleTypeVo> poPage2VoPage(Page<VehicleTypePo> poPage);

    List<VehicleTypeVo> poList2VoList(List<VehicleTypePo> poList);
}
