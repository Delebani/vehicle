package com.vehicle.base.web;

import com.vehicle.base.exception.BaseExceptionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author youzhipeng
 */
@Builder
@AllArgsConstructor
@ApiModel(value = "Response响应对象", description = "Response响应对象")
public class Response<T> {
    @ApiModelProperty(value = "业务状态码")
    private int code;
    @ApiModelProperty(value = "描述信息")
    private String message;
    @ApiModelProperty(value = "响应时间")
    private double time;
    @ApiModelProperty(value = "响应体")
    private T data;

    public Response() {
        this.setCode(BaseExceptionEnum.OK.getCode());
        this.setMessage(BaseExceptionEnum.OK.getMsg());
        this.setData(null);
        this.setTime();
    }

    public Response(T data) {
        if (data instanceof BaseExceptionEnum) {
            this.setCode(((BaseExceptionEnum) data).getCode());
            this.setMessage(((BaseExceptionEnum) data).getMsg());
            this.setData(null);
        } else {
            this.setCode(BaseExceptionEnum.OK.getCode());
            this.setMessage(BaseExceptionEnum.OK.getMsg());
            this.setData(data);
        }
        this.setTime();
    }

    public Response(BaseExceptionEnum e, T data) {
        this.setCode(e.getCode());
        this.setMessage(e.getMsg());
        this.setData(data);
        this.setTime();
    }

    public Response(BaseExceptionEnum e) {
        this.setCode(e.getCode());
        this.setMessage(e.getMsg());
        this.setTime();
    }

    public Response(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(null);
        this.setTime();
    }

    public Response(int code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
        this.setTime();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public double getTime() {
        return time;
    }

    public void setTime() {
        long curTime = System.currentTimeMillis() * 1000;
        long nanoTime = System.nanoTime();
        long microTime = curTime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;

        this.time = microTime / 1000000d;
    }

    public static <T> Response<T> success() {
        return new Response();
    }

    public static <T> Response<T> success(T data) {
        return new Response(data);
    }

    public static <T> Response<T> fail(BaseExceptionEnum e) {
        return new Response(e);
    }

    public static <T> Response<T> fail(int code, String message) {
        return new Response(code, message);
    }

    public static <T> Response<T> fail(String message) {
        return new Response(BaseExceptionEnum.FAIL.getCode(), message);
    }
}
