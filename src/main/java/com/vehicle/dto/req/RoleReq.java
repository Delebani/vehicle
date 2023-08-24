package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/1 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoleReq 对象", description = "RoleReq 请求对象")
public class RoleReq implements Serializable {

    private static final long serialVersionUID = 170433648778293158L;
    
    private Long id;

    @NotBlank(message = "请填写角色名称")
    private String roleName;
}
