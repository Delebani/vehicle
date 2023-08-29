package com.vehicle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vehicle.dto.req.ApplyLogPageReq;
import com.vehicle.dto.vo.ApplyLogVo;
import com.vehicle.po.ApplyLogPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 14:28
 */
@Mapper
public interface ApplyLogMapper extends BaseMapper<ApplyLogPo> {
    
    List<ApplyLogVo> listByReq(ApplyLogPageReq req);

    Integer countByReq(ApplyLogPageReq req);
}
