package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author lili
 * @version 1.0
 * @date 2025/7/29 9:28
 * @description String转成数值
 */
public class StringToNumber {
    public static void main(String[] args) {
        String str =null;
        String blank ="";
        String t ="  ";
        String number =" 111 ";
        String string ="hfsd";
        Integer integer = NumberUtils.toInt(blank);//能转的会转为数值，不能转的会转为0

//        Integer integer = Convert.toInt(blank);//能转的会转为数值，不能转的会转为null
        integer.toString();
        System.out.println("integer = " + integer);

        //总结：
        //整数
        // 1.1基本类型整数：NumberUtils.toInt(string)
        // 2.1包装类型整数转为0，NumberUtils.toInt(string)//如果是浮点数也会转为0
        // 2.2包装类型整数保持null，Convert.toInt(string)//如果是浮点数会转为整数部分，抛弃小数


        //浮点数
        // 1.1基本类型整数：NumberUtils.toDouble(string)
        // 2.1包装类型整数转为0.0，NumberUtils.toDouble(string)
        // 2.2包装类型整数保持null，Convert.toDouble(string)

    }
}
