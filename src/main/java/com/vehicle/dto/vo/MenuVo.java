package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/27 23:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MenuVo 对象", description = "MenuVo 响应对象")
public class MenuVo implements Serializable {

    private static final long serialVersionUID = -1675903957987408852L;

    private Long id;

    private Long parentId;

    private String parentName;

    private String menuName;

    private String menuUrl;

    private Integer menuSort;
}
