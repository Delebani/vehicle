package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/28 21:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplyLogVo 对象", description = "ApplyLogVo 响应对象")
public class ApplyLogVo implements Serializable {

    private static final long serialVersionUID = -3840123522079264254L;

    private Long id;

    private Long applyUserId;

    private String applyUserName;

    private Long vehicleTypeId;

    private String vehicleTypeName;

    private Long vehicleId;

    private String plateNo;

    private LocalDateTime applyTime;

    private String departure;

    private String dest;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer peopleNum;

    private Integer applyReasonId;

    private String applyReason;

    private String remark;

    private Long driverUserId;

    private String driverUserName;

    private LocalDateTime returnTime;

    private BigDecimal mileage;

    private Long approveId;

    private Long approveUsreId;

    private String approveUsreName;

    private Integer approveState;

    private String approveStateName;

    private String approveNotes;

    private LocalDateTime approveTime;

    private Integer state;

    private String stateName;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
