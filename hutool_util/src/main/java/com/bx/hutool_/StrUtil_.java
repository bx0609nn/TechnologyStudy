package com.bx.hutool_;

import cn.hutool.core.util.StrUtil;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/13 9:54
 */
public class StrUtil_ {
    //StrUtil 字符串工具类
    public static void main(String[] args) {
        //1、isEmpty()：判断字符串是否为空，不能判断全是空白串的情况
        //2、isBlank()：判断字符串“是否为空”或“是否为空白串”
        String s = new String();
        System.out.println("StrUtil.isEmpty(s) = " + StrUtil.isEmpty(s));
        System.out.println("StrUtil.isBlank(s) = " + StrUtil.isBlank(s));

        String blankS="     ";
        System.out.println("StrUtil.isEmpty(blankS) = " + StrUtil.isEmpty(blankS));
        System.out.println("StrUtil.isBlank(blankS) = " + StrUtil.isBlank(blankS));

        //5、trim()：去除字符串的首尾空白
        String s1 = new String("    jfjd    jkfj    ");
        System.out.println("StrUtil.trim(s1) = " + StrUtil.trim(s1));

        //6、分割字符串
        String s2 = new String("a,b,c,d");
        StrUtil.split(s2, ',').forEach(System.out::println);//StrUtil.split(s2, ',')按,分割s2字符串
        System.out.println("==================================");
        StrUtil.split(s2, ',', 3).forEach(System.out::println);//StrUtil.split(s2, ',', 3)按,分割s2，最多分成3个

    }
}
