package com.bx.controller;

import com.bx.config.Result;
import com.bx.constant.MessageConstant;
import com.bx.entity.Student;
import com.bx.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * @author lili
     * @date 2025/12/10 16:57
     * @param student 学生
     * @return Result 学生ID
     * @description 添加学生
     */
    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody Student student) {
        Long id = studentService.addStudent(student);
        HashMap<String, Long> result = new HashMap<>();
        result.put("id", id);
        return Result.success(MessageConstant.ADD_SUCCESS, result);
    }

    /**
     * @author lili
     * @date 2025/12/10 9:38
     * @param map 学生ID
     * @return Result
     * @description 删除学生
     */
    @PostMapping("/deleteStudent")
    public Result deleteStudent(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        studentService.deleteStudent(id);
        return Result.success(MessageConstant.DELETING_SUCCESS);
    }

    /**
     * @author lili
     * @date 2025/12/10 10:18
     * @param map 查询条件
     * @return Result 学生列表
     * @description 查询学生列表
     */
    @PostMapping("/getStudentList")
    public Result getStudentList(@RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> data = studentService.getStudentList(map);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/10 10:19
     * @param map 学生ID
     * @return Result 学生
     * @description 根据ID查询学生
     */
    @PostMapping("/getStudentById")
    public Result getStudentById(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        Student student = studentService.getStudentById(id);
        HashMap<String, Student> data = new HashMap<>();
        data.put("student", student);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/10 17:19
     * @param student 学生
     * @return Result 学生ID
     * @description 修改学生
     */
    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student) {
        Long id = studentService.updateStudent(student);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.UPDATE_SUCCESS, data);
    }

}