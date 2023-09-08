package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("user")
@Data
public class UserPo implements Serializable {

    private static final long serialVersionUID = 5134633716943393613L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer type;

    private String mobile;

    private String headUrl;

    private String idNo;

    private String department;

    private String duty;

    private String post;

    private String password;

    private String openId;

    private String token;

    private Integer state;

    private Integer dutyState;

    private Integer userSort;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
