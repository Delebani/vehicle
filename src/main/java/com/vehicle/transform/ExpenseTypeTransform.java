package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.dto.req.ExpenseTypeReq;
import com.vehicle.dto.vo.ExpenseTypeVo;
import com.vehicle.po.ExpenseTypePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface ExpenseTypeTransform {

    ExpenseTypeTransform INSTANCE = Mappers.getMapper(ExpenseTypeTransform.class);

    ExpenseTypePo req2Po(ExpenseTypeReq req);

    ExpenseTypeVo po2Vo(ExpenseTypePo po);

    Page<ExpenseTypeVo> poPage2VoPage(Page<ExpenseTypePo> poPage);

    List<ExpenseTypeVo> poList2VoList(List<ExpenseTypePo> poList);
}
