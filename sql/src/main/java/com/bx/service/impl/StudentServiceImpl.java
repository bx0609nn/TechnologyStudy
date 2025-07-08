package com.bx.service.impl;

import com.bx.dao.BaseDAO;
import com.bx.entity.Student;
import com.bx.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/16 9:59
 * @description
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private BaseDAO baseDAO;

    /**
     * @param conditionMap
     * @return Map<String,Object>
     * @description 分页查询
     */
    @Override
    public Map<String,Object> findStudentListForCondition(HashMap<String, Object> conditionMap) {

        //如果是用集合循环手动构建并赋值查询条件，必须确保存入时存入的key和取出的一致，不然赋值时顺序会错乱，所以使用LinkedHashMap

        /*一组键值对：Map
        * 键无序：HashMap[底层是：哈希表 jdk7:数组+链表，jdk8:数组+链表+红黑树]
        * 键排序：TreeMap
        * 键插入和取出顺序一致：LinkedHashMap
        * 读取文件：Properties
        * */

        LinkedHashMap condition =(LinkedHashMap) conditionMap.get("condition");

        Integer pageNum =(Integer) Optional.ofNullable(conditionMap.get("pageNum")).orElse(1);
        Integer pageSize =(Integer) Optional.ofNullable(conditionMap.get("pageSize")).orElse(10);

        //如果是用集合循环手动构建并赋值查询条件，必须确保存入时存入的key和取出的一致，不然赋值时顺序会错乱，所以使用LinkedHashMap
        LinkedHashMap<String, Object> orderByMap = new LinkedHashMap<>();
        orderByMap.put("age","asc");
        orderByMap.put("name","desc");

        Map result = baseDAO.getObjectsForCondition(Student.class, condition, orderByMap, pageNum, pageSize);

        return result;
    }
}
