package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijianbing
 * @date 2023/9/2 2:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserAddressVo 对象", description = "UserAddressVo 响应对象")
public class UserAddressVo implements Serializable {

    private static final long serialVersionUID = 7422795283961122577L;

    private Long id;

    private Long userId;

    private String address;

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;
}
