package com.bx.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lili
 * @version 1.0
 * @date 2024/12/16 10:01
 * @description
 */
public class Student {
    private Long id;
    private String name;
    private Integer age;
    private String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Student() {
    }

    public Student(Long id, String name, Integer age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
