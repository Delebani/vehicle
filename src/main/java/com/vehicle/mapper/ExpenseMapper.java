package com.vehicle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vehicle.po.ExpensePo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lijianbing
 * @date 2023/7/31 14:28
 */
@Mapper
public interface ExpenseMapper extends BaseMapper<ExpensePo> {
}
