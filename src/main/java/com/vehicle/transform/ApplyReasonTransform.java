package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.dto.req.ApplyReasonReq;
import com.vehicle.dto.vo.ApplyReasonVo;
import com.vehicle.po.ApplyReasonPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface ApplyReasonTransform {

    ApplyReasonTransform INSTANCE = Mappers.getMapper(ApplyReasonTransform.class);

    ApplyReasonPo req2Po(ApplyReasonReq req);

    Page<ApplyReasonVo> poPage2VoPage(Page<ApplyReasonPo> poPage);

    List<ApplyReasonVo> poList2VoList(List<ApplyReasonPo> poList);
}
