package com.vehicle.base.exception;

public interface ExceptionEnum {
    /**
     * 获取异常状态码
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 获取异常描述
     *
     * @return 异常描述
     */
    String getMsg();
}
