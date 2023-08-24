package com.vehicle.base.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 *
 * @author lijianbing
 * @date 2023/8/1 16:09
 */
@AllArgsConstructor
@Getter
public enum SexEnum {

    MAN(1, "男"),
    WOMAN(2, "女"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static SexEnum getByCode(Integer code) {
        for (SexEnum value : SexEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SexEnum.MAN;
    }
}
