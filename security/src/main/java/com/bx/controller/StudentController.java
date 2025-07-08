package com.bx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lili
 * @version 1.0
 * @date 2024/11/11 9:28
 * @Description
 */
@RestController
public class StudentController {

    @GetMapping("/student")
    public String getStudent(){
        return "你好";
    }
}
