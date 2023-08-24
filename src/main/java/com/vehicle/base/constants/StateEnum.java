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
public enum StateEnum {

    OFF_DUTY(0, "不在岗"),
    ON_DUTY(1, "在岗"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static StateEnum getByCode(Integer code) {
        for (StateEnum value : StateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return StateEnum.OFF_DUTY;
    }
}
