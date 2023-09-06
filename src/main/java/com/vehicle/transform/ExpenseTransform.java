package com.vehicle.transform;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.dto.req.ExpenseReq;
import com.vehicle.dto.req.ExpenseTypeReq;
import com.vehicle.dto.vo.ExpenseTypeVo;
import com.vehicle.dto.vo.ExpenseVo;
import com.vehicle.po.ExpensePo;
import com.vehicle.po.ExpenseTypePo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/1 15:58
 */
@Mapper
public interface ExpenseTransform {

    ExpenseTransform INSTANCE = Mappers.getMapper(ExpenseTransform.class);

    ExpensePo req2Po(ExpenseReq req);

    ExpenseTypeVo po2Vo(ExpensePo po);

    Page<ExpenseVo> poPage2VoPage(Page<ExpensePo> poPage);

    List<ExpenseVo> poList2VoList(List<ExpensePo> poList);
}
