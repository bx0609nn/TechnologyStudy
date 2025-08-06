package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/13 9:34
 */

@Slf4j
public class Convert_ {
    public static void main(String[] args) {
        //转为String
        int i=12;
        String str = Convert.toStr(i);//是什么转成什么

        //转为Integer
        Integer integer = Convert.toInt(str);//不能转的会转为null，会自动去前后空格，浮点数转Integer会截取整数部分，去掉小数
        Integer integer1 = Convert.toInt(str, 0);////不能转的会转为默认值0
        System.out.println("Convert.toStr(i) = " + str);
        System.out.println("Convert.toInt(str) = " + integer);

        //转为Double
        Double aDouble = Convert.toDouble(str);//不能转的会转为null，会自动去前后空格

        //转为BigDecimal
        BigDecimal bigDecimal = Convert.toBigDecimal(str);//不能转的会转为null，会自动去前后空格

    }
}
