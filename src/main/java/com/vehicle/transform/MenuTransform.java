package com.vehicle.transform;

import com.vehicle.dto.req.MenuReq;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.po.MenuPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface MenuTransform {

    MenuTransform INSTANCE = Mappers.getMapper(MenuTransform.class);

    MenuPo req2Po(MenuReq req);

    List<MenuTreeVo> poList2TreeVoList(List<MenuPo> poList);
}
