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
public enum DutyStateEnum {

    ON(1, "在岗"),
    OFF(2, "下班"),
    LEAVE(3, "请假"),
    OUT(4, "外勤"),


    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static DutyStateEnum getByCode(Integer code) {
        for (DutyStateEnum value : DutyStateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
