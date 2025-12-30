package com.bx.exception.handler;

import com.bx.config.Result;
import com.bx.constant.MessageConstant;
import com.bx.exception.BsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/5 10:34
 * @description 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    /**
     * 业务异常 - 返回自定义的错误消息
     */
    @ExceptionHandler(BsException.class)
    public Result handleBsException(BsException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String supportedMethods = String.join(", ", e.getSupportedMethods());
        log.error("请求方法不支持：当前方法 {}，支持的方法：{}", e.getMethod(), supportedMethods);
        return Result.error("请求方法不支持");
    }

    /**
     * 参数自动校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        StringBuilder builder = new StringBuilder();
        List<String> errorList = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            builder.append(error.getDefaultMessage()).append("，");
            errorList.add(error.getField() + ": " + error.getDefaultMessage());
        });
        log.error("参数校验失败：{}", errorList);
        builder.deleteCharAt(builder.length() - 1);
        return Result.error(builder.toString());
    }

    /**
     * 其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(MessageConstant.EXCEPTION, e);
        return Result.error(MessageConstant.EXCEPTION);
    }

    /**
     * Error 级别错误
     */
    @ExceptionHandler(Error.class)
    public Result handleError(Error e) {
        log.error(MessageConstant.UNKNOWN_ERROR, e);
        return Result.error(MessageConstant.UNKNOWN_ERROR + "，请联系管理员");
    }
}
