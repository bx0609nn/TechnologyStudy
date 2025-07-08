package com.bx.hutool_;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.bx.entity.Student;

import java.util.*;

/**
 * @author lili
 * @version 1.0
 * @date 2024/10/30 16:24
 * @description
 */
public class ObjectUtil_ {
    public static void main(String[] args) {
        //字符串
        String str1 = null;
        String str2 =new String();
        String str3 = "Hutool";


        // 判断是否为null
        System.out.println(ObjectUtil.isNull(str1));  // true
        System.out.println(ObjectUtil.isNull(str2));  // false
        System.out.println(ObjectUtil.isNull(str3));  // false

        // 判断是否为null或者为空对象
//        public static boolean isEmpty(Object obj) {
//            if (null == obj) {
//                return true;
//            } else if (obj instanceof CharSequence) {
//                return StrUtil.isEmpty((CharSequence)obj);
//            } else if (obj instanceof Map) {
//                return MapUtil.isEmpty((Map)obj);
//            } else if (obj instanceof Iterable) {
//                return IterUtil.isEmpty((Iterable)obj);
//            } else if (obj instanceof Iterator) {
//                return IterUtil.isEmpty((Iterator)obj);
//            } else {
//                return ArrayUtil.isArray(obj) ? ArrayUtil.isEmpty(obj) : false;
//            }
//        }
        System.out.println("String-----------------------------------");
        System.out.println(ObjectUtil.isEmpty(str1));  // true
        System.out.println(ObjectUtil.isEmpty(str2));  // true，对于不为null的字符串判空的规则是 str3.length() == 0;，所以返回true
        System.out.println(ObjectUtil.isEmpty(str3));  // false

        //数组
        int[] intArray1 = null;
        int[] intArray2 = {};
        int[] intArray3 = {1, 2, 3};

        System.out.println("数组-----------------------------------");
        System.out.println(ObjectUtil.isEmpty(intArray1)); // true (null 数组)
        System.out.println(ObjectUtil.isEmpty(intArray2)); // true (空数组)
        System.out.println(ObjectUtil.isEmpty(intArray3)); // false (非空数组)

        //集合
        List<String> list1 = null;
        List<String> list2 = new ArrayList<>();
        List<String> list3 = Arrays.asList("Hutool", "Java");

        System.out.println("集合-----------------------------------");
        System.out.println(ObjectUtil.isEmpty(list1)); // true (null 集合)
        System.out.println(ObjectUtil.isEmpty(list2)); // true (空集合)
        System.out.println(ObjectUtil.isEmpty(list3)); // false (非空集合)

        //Map集合
        Map<String, String> map1 = null;
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> map3 = new HashMap<>();
        map3.put("key", "value");

        System.out.println("Map-----------------------------------");
        System.out.println(ObjectUtil.isEmpty(map1)); // true (null Map)
        System.out.println(ObjectUtil.isEmpty(map2)); // true (空 Map)
        System.out.println(ObjectUtil.isEmpty(map3)); // false (非空 Map)

        //除String、数组、集合、Map之外的其他类对象
        Student student1 = null;
        Student student2 = new Student();
        Student student3 = new Student(1l,"张三",25,"男");

        System.out.println("除String、数组、集合、Map之外的其他类对象-----------------------------------");
        System.out.println(ObjectUtil.isEmpty(student1));  // true
        System.out.println(ObjectUtil.isEmpty(student2));  // false
        System.out.println(ObjectUtil.isEmpty(student3));  // false

        //总结
        //数组：null 或长度为 0 的数组会被认为是空，包含元素的数组（即使有 null）会被认为非空。
        //集合：null 或无元素的集合会被认为是空，包含元素的集合会被认为非空。
        //Map：null 或无键值对的 Map 会被认为是空，包含键值对的 Map 会被认为非空。

    }
}
