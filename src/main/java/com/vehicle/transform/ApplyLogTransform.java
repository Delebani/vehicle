package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.constants.VehicleStateEnum;
import com.vehicle.dto.req.ApplyLogReq;
import com.vehicle.dto.vo.ApplyLogVo;
import com.vehicle.po.ApplyLogPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper(imports = VehicleStateEnum.class)
public interface ApplyLogTransform {

    ApplyLogTransform INSTANCE = Mappers.getMapper(ApplyLogTransform.class);


    @Mappings({
    })
    ApplyLogVo po2Vo(ApplyLogPo po);

    Page<ApplyLogVo> poPage2VoPage(Page<ApplyLogPo> poPage);

    ApplyLogPo req2po(ApplyLogReq req);
}
