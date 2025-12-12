package com.bx.controller;

import com.bx.config.Result;
import com.bx.constant.MessageConstant;
import com.bx.entity.Subject;
import com.bx.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * @author lili
     * @date 2025/12/12 16:03
     * @param subject 课程
     * @return Result 课程ID
     * @description 添加课程
     */
    @PostMapping("/addSubject")
    public Result addSubject(@RequestBody Subject subject) {
        Long id = subjectService.addSubject(subject);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.ADD_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/12 16:05
     * @param map 课程ID
     * @return Result
     * @description 删除课程
     */
    @PostMapping("/deleteSubject")
    public Result deleteSubject(@RequestBody HashMap<String, Long> map) {
        subjectService.deleteSubject(map.get("id"));
        return Result.success(MessageConstant.DELETING_SUCCESS);
    }

    /**
     * @author lili
     * @date 2025/12/12 16:12
     * @param map 查询条件
     * @return Result 课程列表
     * @description 分页查询课程列表
     */
    @PostMapping("/getSubjectList")
    public Result getSubjectList(@RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> data = subjectService.getSubjectList(map);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/12 16:17
     * @param map 课程ID
     * @return Result 课程
     * @description 根据ID查询课程
     */
    @PostMapping("/getSubjectById")
    public Result getSubjectById(@RequestBody HashMap<String, Long> map) {
        Subject subject = subjectService.getSubjectById(map.get("id"));
        HashMap<String, Subject> data = new HashMap<>();
        data.put("subject", subject);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/12 16:20
     * @param subject 课程
     * @return Result 课程ID
     * @description 修改课程
     */
    @PostMapping("/updateSubject")
    public Result updateSubject(@RequestBody Subject subject) {
        Long id = subjectService.updateSubject(subject);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.UPDATE_SUCCESS, data);
    }

}