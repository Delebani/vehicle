package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/29 23:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplyReasonVo 对象", description = "ApplyReasonVo 响应对象")
public class ApplyReasonVo implements Serializable {

    private static final long serialVersionUID = -1680263590336476952L;

    private Long id;

    private String reason;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
