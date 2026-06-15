package com.bx.config;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description 错误提示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ErrorMsg {

    private int code;
    private String message;
    public JSONObject fields;
    private Map fieldMap;//Maq结构特殊时间使用

    public static ErrorMsg error(String message) {
        return new ErrorMsg(-1, message, null, null);
    }

    public static ErrorMsg error(String message, Map fieldMap) {
        return new ErrorMsg(-1, message, null, fieldMap);
    }

    public static ErrorMsg success(String message) {
        return new ErrorMsg(0, message, null, null);
    }

    public static ErrorMsg success(String message, Map fieldMap) {
        return new ErrorMsg(0, message, null, fieldMap);
    }

    public static ErrorMsg success(String message, JSONObject fields) {
        return new ErrorMsg(0, message, fields, null);
    }


    public static ErrorMsg success(String message, Map fieldMap, JSONObject fields) {
        return new ErrorMsg(0, message, fields, fieldMap);
    }

    public static ErrorMsg unLogin(String message) {
        return new ErrorMsg(6, message, null, null);
    }

    public static ErrorMsg unAuthority(String message) {
        return new ErrorMsg(7, message, null, null);
    }

}
