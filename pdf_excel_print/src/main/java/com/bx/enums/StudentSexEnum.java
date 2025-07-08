package com.bx.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/9 17:10
 * @description
 */

public enum StudentSexEnum {

    MAN("男"),
    WOMEN("女");

    private String value;

    public String getValue() {
        return value;
    }

    StudentSexEnum(String value) {
        this.value = value;
    }
}
