package com.vehicle.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.constants.ApproveStateEnum;
import com.vehicle.base.constants.ApproveTypeEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.mapper.ApproveMapper;
import com.vehicle.po.ApprovePo;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id, Integer approveSate) {
        ApprovePo po = super.getById(id);
        if (null == po) {
            throw BizException.error("审核信息不存在，请刷新确认");
        }
        if (ApproveStateEnum.APPROVING.getCode() == approveSate) {
            ApproveStateEnum approveStateEnum = ApproveStateEnum.getByCode(approveSate);
            throw BizException.error("该审批【" + approveStateEnum.getDesc() + "】，请刷新确认");
        }
        CurrentUser currentUser = UserHolder.get();
        po.setApproveUserId(currentUser.getId());
        po.setApproveUserName(currentUser.getName());
        po.setApproveState(approveSate);
        po.setApproveTime(LocalDateTime.now());
        super.updateById(po);
        ApproveTypeEnum approveTypeEnum = ApproveTypeEnum.getByCode(po.getApproveType());
        if(ApproveStateEnum.PASS.getCode() == approveSate){
            switch (approveTypeEnum) {
                case VEHICLE:
                    applyLogService.approvePass(id);
                    break;
                case EXPENSE:
                    break;
                case PERSON:
                    break;
                default:
                    throw BizException.error("审批类型不存在");
            }
        }
    }
}
