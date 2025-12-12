package com.bx.service;

import com.bx.entity.Teacher;

import java.util.HashMap;

public interface TeacherService {

    /**
     * @param teacher 教师
     * @return Long 教师ID
     * @description 添加教师
     */
    Long addTeacher(Teacher teacher);

    /**
     * @param id 教师ID
     * @description 删除教师
     */
    void deleteTeacher(Long id);

    /**
     * @param map 查询条件
     * @return HashMap<String, Object> 教师列表
     * @description 查询教师列表
     */
    HashMap<String, Object> getTeacherList(HashMap<String, Object> map);

    /**
     * @param id 教师ID
     * @return Teacher 教师
     * @description 根据ID查询教师
     */
    Teacher getTeacherById(Long id);

    /**
     * @param teacher 教师
     * @return Long 教师ID
     * @description 更新教师
     */
    Long updateTeacher(Teacher teacher);
}