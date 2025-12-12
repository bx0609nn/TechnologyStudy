package com.bx.service;

import com.bx.entity.Subject;

import java.util.HashMap;

public interface SubjectService {

    /**
     * @param subject 课程
     * @return Long 课程ID
     * @description 添加课程
     */
    Long addSubject(Subject subject);

    /**
     * @param id 课程ID
     * @return void
     * @description 删除课程
     */
    void deleteSubject(Long id);

    /**
     * @param map 查询条件
     * @return HashMap<String, Object> 课程列表
     * @description 分页查询课程列表
     */
    HashMap<String, Object> getSubjectList(HashMap<String, Object> map);

    /**
     * @param id 课程ID
     * @return Subject 课程
     * @description 根据ID查询课程
     */
    Subject getSubjectById(Long id);

    /**
     * @param subject 课程
     * @return Long 课程ID
     * @description 更新课程
     */
    Long updateSubject(Subject subject);
}