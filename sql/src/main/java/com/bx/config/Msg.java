package com.bx.config;

import java.util.Map;
import java.util.Objects;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/16 10:12
 * @description
 */
public class Msg {
     int code;
     String message;
     Map data;

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

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }


    public Msg() {
    }

    public Msg(int code, String message, Map data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Msg)) return false;
        Msg msg = (Msg) o;
        return code == msg.code && Objects.equals(message, msg.message) && Objects.equals(data, msg.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }
}
