package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.dto.req.RoleReq;
import com.vehicle.dto.vo.RoleVo;
import com.vehicle.po.RolePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface RoleTransform {

    RoleTransform INSTANCE = Mappers.getMapper(RoleTransform.class);

    RolePo req2Po(RoleReq req);

    RoleVo po2Vo(RolePo po);

    Page<RoleVo> poPage2VoPage(Page<RolePo> poPage);
}
