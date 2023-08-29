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
public enum ApproveTypeEnum {

    VEHICLE(1, "用车申请"),
    EXPENSE(2, "费用申请"),
    PERSON(3, "个人信息修改申请"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static ApproveTypeEnum getByCode(Integer code) {
        for (ApproveTypeEnum value : ApproveTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
