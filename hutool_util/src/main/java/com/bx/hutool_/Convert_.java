package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/13 9:34
 */

@Slf4j
public class Convert_ {
    public static void main(String[] args) {
        //类型转换
        int i=12;
        String str = Convert.toStr(i);
        Integer integer = Convert.toInt(str);
        System.out.println("Convert.toStr(i) = " + str);
        System.out.println("Convert.toInt(str) = " + integer);

        String convert = Convert.convert(String.class, i);
        System.out.println("Convert.convert(String.class, i) = " + convert);
        Integer integer1 = Convert.toInt(str);

    }
}
