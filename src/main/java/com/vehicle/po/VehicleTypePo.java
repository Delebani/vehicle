package com.vehicle.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 车辆类型表
 *
 * @author lijianbing
 * @date 2023/7/31 13:49
 */
@TableName("vehicle_type")
@Data
public class VehicleTypePo implements Serializable {


    private static final long serialVersionUID = 2756004677571286759L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String typeName;

}
