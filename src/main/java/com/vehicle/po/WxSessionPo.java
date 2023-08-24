package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wx_session")
@ApiModel(value = "WxSessionPo对象", description = "用户")
public class WxSessionPo implements Serializable {

    private static final long serialVersionUID = 950281162009039829L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 微信openid
    */
    @ApiModelProperty(value = "微信openid")
    private String openid;

    /**
    * 微信session_key
    */
    @ApiModelProperty(value = "微信session_key")
    private String sessionKey;

    /**
    * 微信union_id
    */
    @ApiModelProperty(value = "微信union_id")
    private String unionId;

    /**
    * 创建时间
    */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
