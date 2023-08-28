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
public enum UserStateEnum {

    FREEZE(0, "已冻结"),
    ENABLE(1, "已启用"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static UserStateEnum getByCode(Integer code) {
        for (UserStateEnum value : UserStateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return UserStateEnum.FREEZE;
    }
}
