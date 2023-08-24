package com.vehicle.base.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BaseExceptionEnum implements ExceptionEnum {

    OK(0, "成功"),

    FAIL(1, "失败"),

    INVALID_PARAM(101, "非法参数"),

    NOT_FOUND(102, "请求资源不存在"),

    RELOGIN(999, "登录超时，请重新登录"),

    ;

    private final int code;
    private final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
