package com.bx.service;

import com.bx.entity.Student;

import java.util.HashMap;

public interface StudentService {

    /**
     * @param student 学生
     * @return Long 学生ID
     * @description 添加学生
     */
    Long addStudent(Student student);

    /**
     * @param id 学生ID
     * @return void
     * @description 删除学生
     */
    void deleteStudent(Long id);

    /**
     * @param map 查询条件
     * @return HashMap<String, Object> 学生列表
     * @description 查询学生列表
     */
    HashMap<String, Object> getStudentList(HashMap<String, Object> map);

    /**
     * @param id 学生ID
     * @return Long 学生
     * @description 根据ID查询学生
     */
    Student getStudentById(Long id);

    /**
     * @param student 学生
     * @return Long 学生ID
     * @description 修改学生
     */
    Long updateStudent(Student student);

    /**
     * @param map 学生ID，账户ID，原密码，新密码
     * @return void
     * @description 修改账户密码
     */
    void updateAccountPassword(HashMap<String, Object> map);
}