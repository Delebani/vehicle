package com.vehicle.config.validator;

import com.vehicle.base.exception.BaseExceptionEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.base.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(BizException.class)
    public <T> Response<T> handleBizException(BizException e) {
        log.error(e.getMessage(), e);
        return response(e.getMessage());
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public Response<BaseExceptionEnum> exceptionHandler(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return response(BaseExceptionEnum.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Response<BaseExceptionEnum> exceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return response(e.getMessage());
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public Response<BaseExceptionEnum> exceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return response(e.getMessage());
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Response<BaseExceptionEnum> exceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return response(BaseExceptionEnum.INVALID_PARAM);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Response<BaseExceptionEnum> exceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return response(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Response<String> exceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation violation : violations) {
            builder.append(violation.getMessage());
            break;
        }
        return response(builder.toString());
    }

    protected <T> Response<T> response(String msg) {
        Response<T> res = new Response<>();
        res.setCode(BaseExceptionEnum.FAIL.getCode());
        res.setMessage(msg);
        res.setTime();

        return res;
    }

    protected <T> Response<T> response(BaseExceptionEnum exceptionEnum) {
        Response<T> res = new Response<>();
        res.setCode(exceptionEnum.getCode());
        res.setMessage(exceptionEnum.getMsg());
        res.setTime();

        return res;
    }

    protected <T> Response<T> response(int code, String message) {
        Response<T> res = new Response<>();
        res.setCode(code);
        res.setMessage(message);
        res.setTime();
        res.setData(null);

        return res;
    }
}
