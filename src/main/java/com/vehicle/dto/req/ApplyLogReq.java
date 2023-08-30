package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/8/1 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ApplyLogReq 对象", description = "ApplyLogReq 请求对象")
public class ApplyLogReq implements Serializable {

    private static final long serialVersionUID = 2447268894709225256L;

    private Long applyUserId;

    @NotNull(message = "请填写申请类型")
    private Integer applyType;

    @NotNull(message = "请填写申请车辆类型")
    private Long vehicleTypeId;

    private Long vehicleId;

    private LocalDateTime applyTime;

    @NotBlank(message = "请填写出发地")
    private String departure;

    @NotBlank(message = "请填写目的地")
    private String dest;

    @NotNull(message = "请填写用车开始时间")
    private LocalDateTime startTime;

    @NotNull(message = "请填写用车结束时间")
    private LocalDateTime endTime;

    @NotNull(message = "请填写用车人数")
    private Integer peopleNum;

    @NotNull(message = "请填写申请原因")
    private Long applyReasonId;

    private String remark;

    private Long driverUserId;

    private LocalDateTime returnTime;

    private BigDecimal mileage;

    private Long approveId;
}
