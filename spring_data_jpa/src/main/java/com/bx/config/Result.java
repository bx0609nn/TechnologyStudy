package com.bx.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 15:17
 * @description 响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Map data;

    public static Result success() {
        return new Result(200, "success", null);
    }

    public static Result success(String message) {
        return new Result(200, message, null);
    }

    public static Result success(String message, Map data) {
        return new Result(200, message, data);
    }

    public static Result error(String message) {
        return new Result(500, message, null);
    }

    public static Result error(int code, String message) {
        return new Result(code, message, null);
    }

    public static Result error(int code, String message, Map data) {
        return new Result(code, message, data);
    }
}
