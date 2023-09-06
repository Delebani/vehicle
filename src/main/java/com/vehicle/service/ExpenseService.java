package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.constants.ApproveStateEnum;
import com.vehicle.base.constants.ApproveTypeEnum;
import com.vehicle.dto.req.ExpensePageReq;
import com.vehicle.dto.req.ExpenseReq;
import com.vehicle.dto.vo.ExpenseVo;
import com.vehicle.mapper.ExpenseMapper;
import com.vehicle.po.ApprovePo;
import com.vehicle.po.ExpensePo;
import com.vehicle.transform.ExpenseTransform;
import com.vehicle.utils.ApplyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:38
 */
@Service
public class ExpenseService extends ServiceImpl<ExpenseMapper, ExpensePo> {

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private ApproveService approveService;


    @Transactional(rollbackFor = Exception.class)
    public void submit(ExpenseReq req) {
        CurrentUser currentUser = UserHolder.get();
        ExpensePo po = ExpenseTransform.INSTANCE.req2Po(req);
        po.setExpenseNo(ApplyUtil.getNo(ApproveTypeEnum.EXPENSE));
        po.setExpenseUserId(currentUser.getId());
        po.setExpenseTime(LocalDateTime.now());
        // 提交审批
        ApprovePo approvePo = new ApprovePo();
        approvePo.setApplyUserId(currentUser.getId());
        approvePo.setApproveType(ApproveTypeEnum.EXPENSE.getCode());
        approvePo.setApproveState(ApproveStateEnum.APPROVING.getCode());
        approveService.save(approvePo);
        po.setApproveId(approvePo.getId());
        super.save(po);


    }

    public Page<ExpenseVo> pages(ExpensePageReq req) {
        Page<ExpenseVo> page = new Page<>(req.getCurrent(), req.getPageSize());
        List<ExpenseVo> voList = expenseMapper.listByReq(req);
        voList.forEach(o -> {
            ApproveStateEnum approveStateEnum = ApproveStateEnum.getByCode(o.getApproveState());
            if (null != approveStateEnum) {
                o.setApproveStateName(approveStateEnum.getDesc());
            }
        });
        Long count = expenseMapper.countByReq(req);
        page.setRecords(voList);
        page.setTotal(count);
        return page;
    }

    public List<ExpensePo> findByExpenseTypeId(Long expenseTypeId) {
        LambdaQueryWrapper<ExpensePo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ExpensePo::getExpenseTypeId, expenseTypeId);
        return super.list(queryWrapper);
    }
}
