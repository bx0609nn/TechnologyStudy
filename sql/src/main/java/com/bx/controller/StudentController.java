package com.bx.controller;

import com.bx.config.Msg;
import com.bx.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/16 9:59
 * @description
 */

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * @param conditionMap
     * @return Msg
     * @author lili
     * @date 2025/1/16 10:09
     * @description
     */
    @PostMapping("list")
    public Msg findStudentList(@RequestBody HashMap<String, Object> conditionMap){

       Map<String,Object> data = studentService.findStudentListForCondition(conditionMap);

        return new Msg(200,"查询学生列表成功",data);
    }
}
