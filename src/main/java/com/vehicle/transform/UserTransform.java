package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.constants.SexEnum;
import com.vehicle.base.constants.StateEnum;
import com.vehicle.base.constants.UserTypeEnum;
import com.vehicle.dto.req.UserReq;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.po.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper(imports = {SexEnum.class, UserTypeEnum.class, StateEnum.class})
public interface UserTransform {

    UserTransform INSTANCE = Mappers.getMapper(UserTransform.class);

    UserPo req2Po(UserReq req);

    @Mappings({
            @Mapping(target = "sex", expression = "java(SexEnum.getByCode(po.getSex()).getDesc())"),
            @Mapping(target = "type", expression = "java(UserTypeEnum.getByCode(po.getType()).getDesc())"),
    })
    UserVo po2Vo(UserPo po);

    Page<UserVo> poPage2VoPage(Page<UserPo> poPage);

    CurrentUser po2CurrentUser(UserPo userPo);
}
