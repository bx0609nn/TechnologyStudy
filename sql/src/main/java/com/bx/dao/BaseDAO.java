package com.bx.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/16 9:58
 * @description
 */
public interface BaseDAO<T, ID extends Serializable> {

    /**
     * @param clazz,queryCondition,orderByCondition,pageNum,pageSize
     * @return Map
     * @description 条件分页查询并排序
     */
    Map getObjectsForCondition(Class clazz, LinkedHashMap<String,Object> queryCondition, LinkedHashMap<String, Object> orderByCondition, Integer pageNum, Integer pageSize);
}
