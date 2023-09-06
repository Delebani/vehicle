package com.vehicle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vehicle.dto.req.ExpensePageReq;
import com.vehicle.dto.vo.ExpenseVo;
import com.vehicle.po.ExpensePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:28
 */
@Mapper
public interface ExpenseMapper extends BaseMapper<ExpensePo> {
    List<ExpenseVo> listByReq(ExpensePageReq req);

    Long countByReq(ExpensePageReq req);
}
