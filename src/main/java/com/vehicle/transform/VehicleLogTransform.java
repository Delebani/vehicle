package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.constants.VehicleStateEnum;
import com.vehicle.dto.vo.VehicleLogVo;
import com.vehicle.po.VehicleLogPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper(imports = VehicleStateEnum.class)
public interface VehicleLogTransform {

    VehicleLogTransform INSTANCE = Mappers.getMapper(VehicleLogTransform.class);


    @Mappings({
    })
    VehicleLogVo po2Vo(VehicleLogPo po);

    Page<VehicleLogVo> poPage2VoPage(Page<VehicleLogPo> poPage);
}
