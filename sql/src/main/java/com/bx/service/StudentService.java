package com.bx.service;

import com.bx.entity.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/16 9:59
 * @description
 */
public interface StudentService {

    /**
     * @param conditionMap
     * @return Map<String,Object>
     * @description 分页查询
     */
    Map<String,Object> findStudentListForCondition(HashMap<String,Object> conditionMap);
}
