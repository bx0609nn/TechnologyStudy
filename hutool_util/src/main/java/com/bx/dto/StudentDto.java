package com.bx.dto;

/**
 * @author lili
 * @version 1.0
 * @date 2024/12/16 10:01
 * @description
 */
public class StudentDto {
    private Long id;
    private String name;

    private String sex;

    public StudentDto() {
    }

    public StudentDto(Long id, String name, Integer age, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
