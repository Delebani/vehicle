package com.vehicle.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.constants.ApproveStateEnum;
import com.vehicle.base.constants.ApproveTypeEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.ApproveReq;
import com.vehicle.mapper.ApproveMapper;
import com.vehicle.po.ApprovePo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/30 0:02
 */
@Slf4j
@Service
public class ApproveService extends ServiceImpl<ApproveMapper, ApprovePo> {

    @Autowired
    @Lazy
    private ApplyLogService applyLogService;

    @Autowired
    @Lazy
    private ExpenseService expenseService;

    @Transactional(rollbackFor = Exception.class)
    public void approve(ApproveReq req) {
        if (CollectionUtils.isEmpty(req.getIdList()) || ObjectUtils.isNull(req.getApproveState())) {
            return;
        }
        Integer approveState = req.getApproveState();
        String approveNotes = req.getApproveNotes();
        req.getIdList().forEach(id -> {
            approve(id, approveState, approveNotes);
        });
    }

    public void approve(Long id, Integer approveSate, String approveNotes) {
        ApprovePo po = super.getById(id);
        if (null == po) {
            throw BizException.error("审核信息不存在，请您确认");
        }
        ApproveStateEnum stateEnum = ApproveStateEnum.getByCode(po.getApproveState());
        if (ApproveStateEnum.APPROVING != stateEnum) {
            throw BizException.error("申请单，审核状态为【" + stateEnum.getDesc() + "】，请您确认");
        }
        CurrentUser currentUser = UserHolder.get();
        po.setApproveUserId(currentUser.getId());
        po.setApproveUserName(currentUser.getName());
        po.setApproveState(approveSate);
        po.setApproveTime(LocalDateTime.now());
        po.setApproveNotes(approveNotes);
        super.updateById(po);
        ApproveTypeEnum approveTypeEnum = ApproveTypeEnum.getByCode(po.getApproveType());
        if (ApproveStateEnum.PASS.getCode() == approveSate) {
            switch (approveTypeEnum) {
                case VEHICLE:
                    applyLogService.approvePass(id);
                    break;
                case EXPENSE:
                    // 发送通知 TODO
                    break;
                case PERSON:
                    break;
                default:
                    throw BizException.error("审批类型不存在");
            }
        } else if (ApproveStateEnum.REFUSE.getCode() == approveSate) {
            // 发送通知 TODO
            po.getApplyUserId();
        }
    }
}
