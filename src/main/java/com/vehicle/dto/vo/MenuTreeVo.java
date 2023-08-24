package com.vehicle.dto.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MenuTreeVo 对象", description = "MenuTreeVo 响应对象")
public class MenuTreeVo implements Serializable {

    private static final long serialVersionUID = 3815014119292812807L;

    private Long id;

    private Long parentId;

    private String menuName;

    private String menuUrl;

    private Integer menuSort;

    private List<MenuTreeVo> Children;
}
