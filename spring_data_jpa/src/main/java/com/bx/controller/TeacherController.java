package com.bx.controller;

import com.bx.config.Result;
import com.bx.constant.MessageConstant;
import com.bx.entity.Teacher;
import com.bx.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    /**
     * @author lili
     * @date 2025/12/12 16:23
     * @param teacher 教师
     * @return Result 教师ID
     * @description 添加教师
     */
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody Teacher teacher) {
        Long id = teacherService.addTeacher(teacher);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.ADD_SUCCESS, data);
    }
    /**
     * @author lili
     * @date 2025/12/12 16:24
     * @param map 教师ID
     * @return Result
     * @description 删除教师
     */
    @PostMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        teacherService.deleteTeacher(id);
        return Result.success(MessageConstant.DELETING_SUCCESS);
    }

    /**
     * @author lili
     * @date 2025/12/12 16:25
     * @param map 查询条件
     * @return Result 教师列表
     * @description 分页查询教师列表
     */
    @PostMapping("/getTeacherList")
    public Result getTeacherList(@RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> data = teacherService.getTeacherList(map);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/12 16:33
     * @param map 教师ID
     * @return Result 教师
     * @description 根据ID查询教师
     */
    @PostMapping("/getTeacherById")
    public Result getTeacherById(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        Teacher teacher = teacherService.getTeacherById(id);
        HashMap<String, Teacher> data = new HashMap<>();
        data.put("teacher", teacher);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }
    /**
     * @author lili
     * @date 2025/12/12 16:35
     * @param teacher 教师
     * @return Result 教师ID
     * @description 修改教师
     */
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        Long id = teacherService.updateTeacher(teacher);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.UPDATE_SUCCESS, data);
    }

}