package com.bx.hutool_;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/13 14:44
 */
public class CollUtil_ {
    //集合工具类
    public static void main(String[] args) {
        // 1、创建list集合
        ArrayList<Integer> list = CollUtil.newArrayList();
        Collection<Object> collection = CollUtil.create(ArrayList.class);

        // 2、创建list并添加元素
        ArrayList<String> stringArrayList = CollUtil.newArrayList("aaa", "bbb", "ccc");
        System.out.println("stringArrayList = " + stringArrayList);

        // 3、创建map集合
        Map<Object, Object> map = CollUtil.createMap(HashMap.class);
        HashMap<Object, Object> hashMap = CollUtil.newHashMap();


        // 4、集合判空
        //注意：新new出来的没有添加任何元素的集合使用==判空时，此时集合是不为null的
        //所以当前端传过来的是大括号{}，此时也不为空

        System.out.println(map == null);

        System.out.println("map.isEmpty() = " + map.isEmpty());

        System.out.println("CollUtil.isEmpty(list) = " + CollUtil.isEmpty(list));
        System.out.println("CollUtil.isEmpty(map) = " + CollUtil.isEmpty(map));

        list.add(1);
        list.add(2);
        list.add(5);
        list.add(2);
        list.add(3);

        ArrayList<Integer> list1 = CollUtil.newArrayList(5, 6, 2, 8);

        //sub()：截取，左闭右开
        System.out.println("list截取[0,3)-------------------------------------");
        CollUtil.sub(list, 0, 3).forEach(System.out::println);

        //并集
        System.out.println("并集-------------------------------------");
        CollUtil.union(CollUtil.distinct(list), list1).forEach(System.out::println);

        //交集
        System.out.println("交集*************************************");
        CollUtil.intersection(list, list1).forEach(System.out::println);

        //差集，两个集合取并集然后去掉交集
        System.out.println("差集=====================================");
        CollUtil.disjunction(CollUtil.distinct(list), list1).forEach(System.out::println);

        //去重
        System.out.println("去重.....................................");
        CollUtil.distinct(list).forEach(System.out::println);
    }
}
