package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.constants.VehicleStateEnum;
import com.vehicle.dto.req.VehicleReq;
import com.vehicle.dto.vo.VehicleVo;
import com.vehicle.po.VehiclePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper(imports = VehicleStateEnum.class)
public interface VehicleTransform {

    VehicleTransform INSTANCE = Mappers.getMapper(VehicleTransform.class);

    VehiclePo req2Po(VehicleReq req);

    @Mappings({
            @Mapping(target = "stateName", expression = "java(VehicleStateEnum.getByCode(po.getState().intValue()).getDesc())"),
    })
    VehicleVo po2Vo(VehiclePo po);

    Page<VehicleVo> poPage2VoPage(Page<VehiclePo> poPage);

    List<VehicleVo> poList2VoList(List<VehiclePo> poList);
}
