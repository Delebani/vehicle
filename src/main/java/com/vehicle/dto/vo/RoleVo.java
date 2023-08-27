package com.vehicle.dto.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleVo 对象", description = "RoleVo 响应对象")
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -5570869861570307359L;

    private Long id;

    private String roleName;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;

    private List<Long> menuIdList = Lists.newArrayList();
}
