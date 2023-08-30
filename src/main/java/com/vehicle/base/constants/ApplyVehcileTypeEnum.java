package com.vehicle.base.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审批类型
 *
 * @author lijianbing
 * @date 2023/8/1 16:09
 */
@AllArgsConstructor
@Getter
public enum ApplyVehcileTypeEnum {

    NORMAL(1, "正常"),
    PRESSING(2, "紧急"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static ApplyVehcileTypeEnum getByCode(Integer code) {
        for (ApplyVehcileTypeEnum value : ApplyVehcileTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return NORMAL;
    }
}
