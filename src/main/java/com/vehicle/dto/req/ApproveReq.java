package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijianbing
 * @date 2023/9/1 22:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApproveReq 对象", description = "ApproveReq 响应对象")
public class ApproveReq implements Serializable {

    private static final long serialVersionUID = 8002295467619059821L;

    @NotNull(message = "审核id不可为空")
    private List<Long> idList;

    @NotNull(message = "请填写审核意见")
    private Integer approveState;

    private String approveNotes;
}
