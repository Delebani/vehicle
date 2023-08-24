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
public enum UserTypeEnum {

    ADMINISTRATORS(1, "管理员"),
    USE_VEHICLE(2, "用车人员"),
    DRIVER(3, "司机"),
    ORDINARY(4, "普通用户"),
    OTHER(10, "其他"),

    ;

    @EnumValue
    private Integer code;

    private String desc;

    public static UserTypeEnum getByCode(Integer code) {
        for (UserTypeEnum value : UserTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return UserTypeEnum.OTHER;
    }
}
