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


public class MenuReq implements Serializable {

    private static final long serialVersionUID = -6401031147260639474L;

    private Long id;

    private Long parentId;

    @NotBlank(message = "请填写菜单名称")
    private String menuName;

    @NotBlank(message = "请填写菜单地址")
    private String menuUrl;

    private Integer sort;
}
