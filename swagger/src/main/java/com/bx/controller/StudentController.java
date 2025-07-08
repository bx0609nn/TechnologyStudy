package com.bx.controller;

import com.bx.model.Student;
import org.springframework.web.bind.annotation.*;

import static com.bx.constant.SexConstant.MAN;

/**
 * @author lili
 * @version 1.0
 * @date 2024/11/4 16:35
 * @description
 */
@RestController
public class StudentController {

    //配置实体类，只要返回值为实体类类型，swagger就会自动扫描到
    @GetMapping("/student/{id}")
    public Student queryStudent(@PathVariable Long id) {
        Student student = new Student(1L, "张三", 25, MAN);
        return student;
    }

    @PostMapping("/studentForPage")
    public String studentForPage(@RequestBody Student student) {
        return student.getName();
    }

}
