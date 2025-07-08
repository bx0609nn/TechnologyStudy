package com.bx.dao.impl;

import cn.hutool.core.collection.CollUtil;
import com.bx.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/16 9:58
 * @description
 */

@Repository
public class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID> {

    @Autowired
    private EntityManager entityManager;

    //如果是用集合循环手动构建并赋值查询条件，必须确保存入时存入的key和取出的一致，不然赋值时顺序会错乱，所以使用LinkedHashMap
    @Override
    public Map getObjectsForCondition(Class clazz, LinkedHashMap<String, Object> queryCondition, LinkedHashMap<String, Object> orderByCondition, Integer pageNum, Integer pageSize) {

        //获取条件查询语句
        String querySql = getQuerySql(clazz, queryCondition, orderByCondition);
        Query query = entityManager.createQuery(querySql);
        //条件赋值
        if (CollUtil.isNotEmpty(queryCondition)) {
            int i = 1;
            for (String key : queryCondition.keySet()) {
                query.setParameter(i, queryCondition.get(key));
                i++;
            }
        }

        //分页查询
        List list = query.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).getResultList();
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);

        //获取条件查询总条数语句
        String countSql = getCountSql(clazz, queryCondition);
        Query countQuery = entityManager.createQuery(countSql);
        if (CollUtil.isNotEmpty(queryCondition)) {
            int i = 1;
            for (String key : queryCondition.keySet()) {
                countQuery.setParameter(i, queryCondition.get(key));
                i++;
            }
        }
        Long recordCount = (Long) countQuery.getSingleResult();
        result.put("recordCount", recordCount);

        //计算总页数
        Long totalPages = (Long) (recordCount - 1 + pageSize) / pageSize;

        result.put("totalPages", totalPages);

        return result;
    }


    /**
     * @author lili
     * @date 2025/1/17 15:20
     * @param clazz,queryCondition,orderByCondition
     * @return String
     * @description 根据条件查询和排序
     */

    private String getQuerySql(Class clazz, LinkedHashMap<String, Object> queryCondition, LinkedHashMap<String, Object> orderByCondition) {
        StringBuilder sql = new StringBuilder();
        sql.append("from " + clazz.getSimpleName() + " where 1=1");

        //拼接条件
        if (CollUtil.isNotEmpty(queryCondition)) {
            int i = 1;
            for (String key : queryCondition.keySet()) {
                sql.append(" and ").append(key).append(" = ?").append(i);
                i++;
            }
        }

//        if (CollUtil.isNotEmpty(orderByCondition)) {
//            sql.append(" order by ");
//            String orderBySql = "";
//            for (String key : orderByCondition.keySet()) {
//                if ("".equals(orderBySql)) {
//                    orderBySql = key + " " + orderByCondition.get(key);
//                } else {
//                    orderBySql = orderBySql + ", " + key + " " + orderByCondition.get(key);
//                }
//            }
//            sql.append(orderBySql);
//        }

        //拼接排序
        if (CollUtil.isNotEmpty(orderByCondition)) {
            sql.append(" order by ");
            StringBuilder orderBySql = new StringBuilder();
            for (String key : orderByCondition.keySet()) {
                if (orderBySql.length() == 0) {
                    orderBySql.append(key).append(" ").append(orderByCondition.get(key));
                } else {
                    orderBySql.append(", ").append(key).append(" ").append(orderByCondition.get(key));
                }
            }
            sql.append(orderBySql);
        }

        return sql.toString();
    }

    /**
     * @author lili
     * @date 2025/1/17 15:19
     * @param clazz,queryCondition
     * @return String
     * @description 根据条件查询总条数语句
     */
    private String getCountSql(Class clazz, LinkedHashMap<String, Object> queryCondition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from " + clazz.getSimpleName() + " where 1=1");

        if (CollUtil.isNotEmpty(queryCondition)) {
            int i = 1;
            for (String key : queryCondition.keySet()) {
                sql.append(" and ").append(key).append(" = ?").append(i);
                i++;
            }
        }

        return sql.toString();
    }
}
