package com.bx.annotation;

import com.bx.enumtype.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/10 11:23
 * @description 自定义注解，用来标识需要自动填充字段的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OperationType value();
}
