package com.bx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/9 17:18
 * @description
 */
public enum ZeroOneEnum {

    ZERO("0"),
    ONE("1");

    private String value;

    ZeroOneEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String find(String value){

        for (ZeroOneEnum zeroOneEnum: ZeroOneEnum.values()) {

            if (zeroOneEnum.getValue().equals(value)){
                return zeroOneEnum.getValue();
            }
        }
        return null;
    }
}
