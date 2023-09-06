package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.ExpenseTypePageReq;
import com.vehicle.dto.req.ExpenseTypeReq;
import com.vehicle.dto.vo.ExpenseTypeVo;
import com.vehicle.mapper.ExpenseTypeMapper;
import com.vehicle.po.ExpensePo;
import com.vehicle.po.ExpenseTypePo;
import com.vehicle.transform.ExpenseTypeTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class ExpenseTypeService extends ServiceImpl<ExpenseTypeMapper, ExpenseTypePo> {

    @Autowired
    private ExpenseTypeMapper ExpenseTypeMapper;

    @Autowired
    @Lazy
    private ExpenseService expenseService;

    public void saveOrUpdate(ExpenseTypeReq req) {
        ExpenseTypePo po = ExpenseTypeTransform.INSTANCE.req2Po(req);
        LambdaQueryWrapper<ExpenseTypePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ExpenseTypePo::getTypeName, req.getTypeName());
        queryWrapper.last("limit 1");
        ExpenseTypePo namePo = super.getOne(queryWrapper);
        if (null == req.getId() && null != namePo) {
            throw BizException.error("该费用类型已存在");
        }
        if (null != req.getId() && null != namePo && req.getId() != namePo.getId()) {
            throw BizException.error("该费用类型已存在");
        }
        super.saveOrUpdate(po);
    }

    public void del(Long id) {
        List<ExpensePo> expensePoList = expenseService.findByExpenseTypeId(id);
        if (CollectionUtils.isNotEmpty(expensePoList)) {
            throw BizException.error("该费用类型已被使用，不可删除");
        }
        super.removeById(id);
    }

    public ExpenseTypeVo view(Long id) {
        ExpenseTypePo po = super.getById(id);
        return ExpenseTypeTransform.INSTANCE.po2Vo(po);
    }

    public Page<ExpenseTypeVo> pages(ExpenseTypePageReq req) {
        LambdaQueryWrapper<ExpenseTypePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getTypeName()), ExpenseTypePo::getTypeName, req.getTypeName());
        Page<ExpenseTypePo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);

        return ExpenseTypeTransform.INSTANCE.poPage2VoPage(poPage);
    }

    public List<ExpenseTypeVo> all() {
        List<ExpenseTypePo> poList = super.list();
        return ExpenseTypeTransform.INSTANCE.poList2VoList(poList);
    }
}
