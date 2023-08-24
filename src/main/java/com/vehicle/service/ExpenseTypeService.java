package com.vehicle.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.ExpenseTypeMapper;
import com.vehicle.po.ExpenseTypePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class ExpenseTypeService extends ServiceImpl<ExpenseTypeMapper, ExpenseTypePo> {

    @Autowired
    private ExpenseTypeMapper ExpenseTypeMapper;
}
