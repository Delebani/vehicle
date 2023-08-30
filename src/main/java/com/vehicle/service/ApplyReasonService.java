package com.vehicle.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.ApplyReasonPageReq;
import com.vehicle.dto.req.ApplyReasonReq;
import com.vehicle.dto.vo.ApplyReasonVo;
import com.vehicle.mapper.ApplyReasonMapper;
import com.vehicle.po.ApplyReasonPo;
import com.vehicle.transform.ApplyReasonTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/8/29 23:02
 */
@Service
@Slf4j
public class ApplyReasonService extends ServiceImpl<ApplyReasonMapper, ApplyReasonPo> {

    @Autowired
    @Lazy
    private ApplyLogService applyLogService;

    public void saveOrUpdate(ApplyReasonReq req) {

        ApplyReasonPo po = ApplyReasonTransform.INSTANCE.req2Po(req);
        LambdaQueryWrapper<ApplyReasonPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ApplyReasonPo::getReason, req.getReason());
        queryWrapper.last("limit 1");
        ApplyReasonPo reasonPo = super.getOne(queryWrapper);
        if (null == req.getId() && null != reasonPo) {
            throw BizException.error("该申请原因已存在，请确认");
        }
        if (null != req.getId() && null != reasonPo && req.getId() != reasonPo.getId()) {
            throw BizException.error("该申请原因已存在，请确认");
        }
        super.saveOrUpdate(po);
    }

    public void delById(Long id) {
        Integer count = applyLogService.findByApplyReasonId(id);
        if (count > 0) {
            throw BizException.error("申请原因已被使用，不可删除");
        }
        super.removeById(id);
    }

    public Page<ApplyReasonVo> pages(ApplyReasonPageReq req) {
        LambdaQueryWrapper<ApplyReasonPo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getReason()), ApplyReasonPo::getReason, req.getReason());
        Page<ApplyReasonPo> poPage = super.page(new Page<>(req.getCurrent(), req.getPageSize()), queryWrapper);
        return ApplyReasonTransform.INSTANCE.poPage2VoPage(poPage);
    }

    public List<ApplyReasonVo> all() {
        List<ApplyReasonPo> poList = super.list();
        return ApplyReasonTransform.INSTANCE.poList2VoList(poList);
    }
}
