package com.bx.exception;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/5 10:32
 * @description 自定义异常类
 */
public class BsException extends RuntimeException {

    public BsException() {
        super();
    }

    public BsException(String message) {
        super(message);
    }

    public BsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BsException(Throwable cause) {
        super(cause);
    }

    protected BsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
