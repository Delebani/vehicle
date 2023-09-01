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

    APPLYING(0, "申请中"),
    NO(1, "未出车"),
    ING(2, "出车中"),
    COMPLETED(3, "已完成"),
    CANCEL(4, "已取消"),

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
        return APPLYING;
    }
}
