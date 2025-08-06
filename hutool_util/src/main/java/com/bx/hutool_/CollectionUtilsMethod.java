package com.bx.hutool_;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lili
 * @version 1.0
 * @date 2025/7/31 14:53
 * @description
 */
public class CollectionUtilsMethod {
    public static void main(String[] args) {
        ArrayList<String> string = CollUtil.newArrayList();
        ArrayList<Object> arrayList = null;
        System.out.println("ArrayUtil.isNotEmpty(string) = " + ArrayUtil.isEmpty(string));//false
        System.out.println("CollUtil.isNotEmpty(string) = " + CollUtil.isEmpty(string));//true

        System.out.println("ArrayUtil.isNotEmpty(arrayList) = " + ArrayUtil.isEmpty(arrayList));//true
        System.out.println("CollUtil.isNotEmpty(arrayList) = " + CollUtil.isEmpty(arrayList));//true

        HashMap<String, Integer> ageMap = MapUtil.of("age", 18);
        System.out.println("ageMap = " + ageMap);

        ArrayList<String> list1 = ListUtil.toList("1", "2", "3");
        System.out.println("list1 = " + list1);


        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        //会报ConcurrentModificationException异常，原因是在使用增强for循环（foreach）遍历集合时，同时修改了集合的结构。
        //是因为增强for的遍历底层使用迭代器，迭代器有一个expectedModCount属性，用来表示该集合预期被修改的次数，
        //还有一个modCount属性，用来表示该集合实际被修改的次数。
        //当集合执行iterator.hasNext()时都会检查两个的值是否相等，不相等就抛出该异常，
        //由于在增强for遍历的过程中集合使用自己的remove方法删除了元素导致modCount发生了变化，
        //但是迭代器的expectedModCount还是没变，导致下一次遍历检查不相等，所以报错
//        for (String name : list) {
//            if (name.equals("2")) {
//                list.remove(name);
//            }
//        }

        //正确方法
        list.removeIf(name -> name.equals("2"));//原生
        CollUtil.removeAny(list, "2");//工具类
        System.out.println(list);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("单位","");//为空转为String
        hashMap.put("净重 （千克)","");//转为Double
        hashMap.put("序号","");//转为Integer
        hashMap.put("总值 （美元)",null);//转为BigDecimal

        DecList decList = new DecList();
        String unit = MapUtil.getStr(hashMap, "单位");
        System.out.println("unit.toString() = " + unit.toString());
        decList.setgUnit(unit);

        Double weight = NumberUtils.toDouble(MapUtil.getStr(hashMap, "净重 （千克)"));
        System.out.println("weight.toString() = " + weight.toString());
        decList.setgQty(weight);

        Integer no = NumberUtils.toInt(MapUtil.getStr(hashMap, "序号"));
        System.out.println("no.toString() = " + no.toString());
        decList.setgNo(no);


        //报错java.lang.NumberFormatException: A blank string is not a valid number
        BigDecimal total = NumberUtils.toScaledBigDecimal(MapUtil.getStr(hashMap, "总值 （美元)"));
//        BigDecimal total = Convert.toBigDecimal(MapUtil.getStr(hashMap, "总值 （美元)"), BigDecimal.ZERO);
        System.out.println("total.toString() = " + total.toString());
        decList.setDeclTotal(total);
        System.out.println(decList.toString());

    }
}
