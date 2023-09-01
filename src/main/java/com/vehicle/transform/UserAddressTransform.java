package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.dto.req.UserAddressReq;
import com.vehicle.dto.vo.UserAddressVo;
import com.vehicle.po.UserAddressPo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface UserAddressTransform {

    UserAddressTransform INSTANCE = Mappers.getMapper(UserAddressTransform.class);

    UserAddressPo req2Po(UserAddressReq req);

    UserAddressVo po2Vo(UserAddressPo po);

    Page<UserAddressVo> poPage2VoPage(Page<UserAddressPo> poPage);

    List<UserAddressVo> poList2VoList(List<UserAddressPo> poList);
}
