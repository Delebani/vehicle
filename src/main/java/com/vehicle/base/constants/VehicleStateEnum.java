package com.vehicle.base.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 *
 * @author lijianbing
 * @date 2023/8/1 16:09
 */
@AllArgsConstructor
@Getter
public enum VehicleStateEnum {

    NO(0, "未出车"),
    YES(1, "已出车"),
    UPKEEP(2, "保养中"),
    REPAIR(3, "维修中"),
    CEASE(4, "已停运"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static VehicleStateEnum getByCode(Integer code) {
        for (VehicleStateEnum value : VehicleStateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return VehicleStateEnum.NO;
    }
}
