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
public enum ApproveStateEnum {

    APPROVING(1, "审核中"),
    PASS(2, "已通过"),
    REFUSE(3, "被拒绝"),
    STOP(4, "终止"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static ApproveStateEnum getByCode(Integer code) {
        for (ApproveStateEnum value : ApproveStateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
