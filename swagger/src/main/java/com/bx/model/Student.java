package com.bx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lili
 * @version 1.0
 * @date 2024/11/5 10:02
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;
    private String name;
    private Integer age;

    private String sex;
}
