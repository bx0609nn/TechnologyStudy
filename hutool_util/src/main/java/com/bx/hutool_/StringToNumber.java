package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

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
        String space ="  ";
        String number =" 12 ";
        String string ="hfsd";
        //Integer
        Integer integer = NumberUtils.toInt(number);//能转的会转为数值，不能转的会转为0。1.不会自动去掉前后空格，数字前后有空格会转为0。2.如果是浮点数也会转为0
        System.out.println("integer.toString = " + integer.toString());

        Integer integer1 = Convert.toInt(number);//能转的会转为数值，不能转的会转为null。1.会自动去掉前后空格。2.如果是浮点数会转为整数部分，抛弃小数
        System.out.println("integer1.toString = " + integer1.toString());

        //Double
        Double aDouble = NumberUtils.toDouble(number);//NumberUtils转Double时又能去前后空格
        System.out.println("aDouble.toString() = " + aDouble);

        Double aDouble1 = Convert.toDouble(number);
        System.out.println("aDouble1.toString() = " + aDouble1);

        //BigDecimal
        BigDecimal bigDecimal = Convert.toBigDecimal(number);
        System.out.println("bigDecimal.toString = " + bigDecimal.toString());

//        BigDecimal bigDecimal1 = NumberUtils.toScaledBigDecimal(number);//只能转null和数值。空串、空白串、非数值都不能转会报错
//        System.out.println("bigDecimal1.toString = " + bigDecimal1.toString());

        //总结：
        //1、Integer
        // 1.1基本类型整数：NumberUtils.toInt(string)
        // 2.1包装类型整数转为0，NumberUtils.toInt(string)//1.不会自动去掉前后空格，数字前后有空格会转为0。2.如果是浮点数也会转为0
        // 2.2包装类型整数保持null，Convert.toInt(string)//1.会自动去掉前后空格。2.如果是浮点数会转为整数部分，抛弃小数


        //2、Double
        // 1.1基本类型整数：NumberUtils.toDouble(string)//又能自动去前后空格
        // 2.1包装类型整数转为0.0，NumberUtils.toDouble(string)
        // 2.2包装类型整数保持null，Convert.toDouble(string)

        //3、BigDecimal
        //为什么不使用NumberUtils.toScaledBigDecimal(string)，因为它只能转null和数值。空串、空白串、非数值都不能转会报错
        //2.3转为BigDecimal，Convert.toBigDecimal(string, BigDecimal.ZERO)


        String date = "";
        DateTime dateTime = DateUtil.parseDate(date);//java.lang.IllegalArgumentException: Date String must be not blank !
        System.out.println("dateTime.toString() = " + dateTime.toString());

        String date1 = "sdfsf";
        DateTime dateTime1 = DateUtil.parseDate(date1);//Parse [null] with format [yyyy-MM-dd] error!
        System.out.println("dateTime1.toString() = " + dateTime1.toString());

    }
}
