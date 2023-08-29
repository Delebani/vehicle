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
public enum ApplyVehcileStateEnum {

    N(0, ""),
    NO(1, "未出车"),
    ING(2, "出车中"),
    COMPLETED(3, "已完成"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static ApplyVehcileStateEnum getByCode(Integer code) {
        for (ApplyVehcileStateEnum value : ApplyVehcileStateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return N;
    }
}
