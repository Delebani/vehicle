package com.vehicle.base.exception;

import com.vehicle.base.web.Response;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 业务异常基类
 */
@NoArgsConstructor
public class BizException extends RuntimeException {

    private int code;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static BizException error(Integer code, String msg) {
        return new BizException(code, msg);
    }

    public static BizException error(String msg) {
        return error(BaseExceptionEnum.FAIL.getCode(), msg);
    }

    public static BizException error(ExceptionEnum e) {
        return error(e.getCode(), e.getMsg());
    }

    public static BizException error(Response res) {
        return error(res.getCode(), res.getMessage());
    }

    public static BizException error(ExceptionEnum e, String msg) {
        return error(e.getCode(), msg);
    }

    /**
     * 如果这个 boolean 为 true 则抛出异常
     *
     * @param expression boolean 值
     * @param message    消息
     */
    public static void isTrue(boolean expression, String message, Object... params) {
        if (expression) {
            throw error(String.format(message, params));
        }
    }

    /**
     * 如果这个 boolean 为 true 则抛出异常
     *
     * @param expression boolean 值
     * @param e          异常枚举
     */
    public static void isTrue(boolean expression, ExceptionEnum e) {
        if (expression) {
            throw error(e);
        }
    }

    /**
     * 如果这个 boolean 为 false 则抛出异常
     *
     * @param expression boolean 值
     * @param message    消息
     */
    public static void isFalse(boolean expression, String message, Object... params) {
        isTrue(!expression, message, params);
    }

    /**
     * 如果这个 boolean 为 false 则抛出异常
     *
     * @param expression boolean 值
     * @param e          异常枚举
     */
    public static void isFalse(boolean expression, ExceptionEnum e) {
        isTrue(!expression, e);
    }

    /**
     * 如果这个 object 为 null 则抛异常
     *
     * @param object  对象
     * @param message 消息
     */
    public static void isNull(Object object, String message, Object... params) {
        isTrue(object == null, message, params);
    }

    /**
     * 如果这个 object 为 null 则抛异常
     *
     * @param object 对象
     * @param e      异常枚举
     */
    public static void isNull(Object object, ExceptionEnum e) {
        isTrue(object == null, e);
    }

    /**
     * 如果这个 object 不为 null 则抛异常
     *
     * @param object  对象
     * @param message 消息
     */
    public static void notNull(Object object, String message, Object... params) {
        isTrue(object != null, message, params);
    }

    /**
     * 如果这个 object 不为 null 则抛异常
     *
     * @param object 对象
     * @param e      异常枚举
     */
    public static void notNull(Object object, ExceptionEnum e) {
        isTrue(object != null, e);
    }

    /**
     * 如果这个 value 为 空 则抛异常
     *
     * @param value   字符串
     * @param message 消息
     */
    public static void isBlank(String value, String message, Object... params) {
        isTrue(StringUtils.isBlank(value), message, params);
    }

    /**
     * 如果这个 value 不为 空 则抛异常
     *
     * @param value   字符串
     * @param message 消息
     */
    public static void notBlank(String value, String message, Object... params) {
        isTrue(StringUtils.isNotBlank(value), message, params);
    }

    /**
     * 如果这个 value 为 empty 则抛异常
     *
     * @param value 字符串
     * @param e     异常枚举
     */
    public static void isBlank(String value, ExceptionEnum e) {
        isTrue(StringUtils.isBlank(value), e);
    }

    /**
     * 如果这个 value 不为 empty 则抛异常
     *
     * @param value 字符串
     * @param e     异常枚举
     */
    public static void notBlank(String value, ExceptionEnum e) {
        isTrue(StringUtils.isNotBlank(value), e);
    }

    /**
     * 如果这个 collection 为 empty 则抛异常
     *
     * @param collection 集合
     * @param message    消息
     */
    public static void isEmpty(Collection<?> collection, String message, Object... params) {
        isTrue(CollectionUtils.isEmpty(collection), message, params);
    }

    /**
     * 如果这个 collection 不为 empty 则抛异常
     *
     * @param collection 集合
     * @param message    消息
     */
    public static void notEmpty(Collection<?> collection, String message, Object... params) {
        isTrue(CollectionUtils.isNotEmpty(collection), message, params);
    }

    /**
     * 如果这个 collection 为 empty 则抛异常
     *
     * @param collection 集合
     * @param e          异常枚举
     */
    public static void isEmpty(Collection<?> collection, ExceptionEnum e) {
        isTrue(CollectionUtils.isEmpty(collection), e);
    }

    /**
     * 如果这个 collection 不为 empty 则抛异常
     *
     * @param collection 集合
     * @param e          异常枚举
     */
    public static void notEmpty(Collection<?> collection, ExceptionEnum e) {
        isTrue(CollectionUtils.isNotEmpty(collection), e);
    }

    /**
     * 如果这个 map 为 empty 则抛异常
     *
     * @param map     集合
     * @param message 消息
     */
    public static void isEmpty(Map<?, ?> map, String message, Object... params) {
        isTrue(MapUtils.isEmpty(map), message, params);
    }

    /**
     * 如果这个 map 不为 empty 则抛异常
     *
     * @param map     集合
     * @param message 消息
     */
    public static void notEmpty(Map<?, ?> map, String message, Object... params) {
        isTrue(MapUtils.isNotEmpty(map), message, params);
    }

    /**
     * 如果这个 map 为 empty 则抛异常
     *
     * @param map 集合
     * @param e   异常枚举
     */
    public static void isEmpty(Map<?, ?> map, ExceptionEnum e) {
        isTrue(MapUtils.isEmpty(map), e);
    }

    /**
     * 如果这个 map 不为 empty 则抛异常
     *
     * @param map 集合
     * @param e   异常枚举
     */
    public static void notEmpty(Map<?, ?> map, ExceptionEnum e) {
        isTrue(MapUtils.isNotEmpty(map), e);
    }

    /**
     * 如果这个 数组 为 empty 则抛异常
     *
     * @param array   数组
     * @param message 消息
     */
    public static void isEmpty(Object[] array, String message, Object... params) {
        isTrue(ArrayUtils.isEmpty(array), message, params);
    }

    /**
     * 如果这个 数组 不为 empty 则抛异常
     *
     * @param array   数组
     * @param message 消息
     */
    public static void notEmpty(Object[] array, String message, Object... params) {
        isTrue(ArrayUtils.isNotEmpty(array), message, params);
    }

    /**
     * 如果这个 数组 为 empty 则抛异常
     *
     * @param array 数组
     * @param e     异常枚举
     */
    public static void isEmpty(Object[] array, ExceptionEnum e) {
        isTrue(ArrayUtils.isEmpty(array), e);
    }

    /**
     * 如果这个 数组 不为 empty 则抛异常
     *
     * @param array 数组
     * @param e     异常枚举
     */
    public static void notEmpty(Object[] array, ExceptionEnum e) {
        isTrue(ArrayUtils.isNotEmpty(array), e);
    }
}
