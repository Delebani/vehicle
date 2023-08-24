package com.vehicle.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.ExpenseMapper;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.po.ExpensePo;
import com.vehicle.po.VehiclePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class ExpenseService extends ServiceImpl<ExpenseMapper, ExpensePo> {

    @Autowired
    private ExpenseMapper expenseMapper;
}
