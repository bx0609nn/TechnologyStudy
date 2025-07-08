package com.bx.conver;

import com.bx.enums.ZeroOneEnum;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/9 11:05
 * @description 其他类型转换为String
 */
public class TypeConverToString {
    public static void main(String[] args) {
        //1、数值类型转换为String时特别注意，如果数值类型是包装类为null时
        Double d=null;
//        String result = String.valueOf(new BigDecimal(d));// 抛出 NullPointerException，因为 new BigDecimal(null) 不允许。
//        System.out.println("包装类型null，result = " + result);

        //解决办法：使用 Optional 或 null 检查
        String result1 = String.valueOf(d == null ? BigDecimal.ZERO : BigDecimal.valueOf(d));
        String result2 = String.valueOf(BigDecimal.valueOf(Optional.ofNullable(d).orElse(0.0)));
        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);


        //2、如果grossWeight为double或Double类型,当值为123.45时
        d=123.45;
        double d1=123.45;
        String result3 = String.valueOf(new BigDecimal(d));
        String result4 = String.valueOf(new BigDecimal(d1));
        System.out.println("result3 = " + result3);//输出 "123.4500000000000028421709430404007434844970703125"
        System.out.println("result4 = " + result4);//输出 "123.4500000000000028421709430404007434844970703125"
        //原因：new BigDecimal(double)时，`double` 转换为 `BigDecimal` 时会保留浮点误差
        //原因：new BigDecimal(Double)时，`Double` 转换为 `BigDecimal` 时会保留浮点误差

        //3、2的解决办法，但是BigDecimal.valueOf()也不能解决null的情况
        String result5 = String.valueOf(BigDecimal.valueOf(d));
        System.out.println("result5 = " + result5);
        String result6 = String.valueOf(BigDecimal.valueOf(d1));
        System.out.println("result6 = " + result6);
        //BigDecimal.valueOf(double) 内部使用了字符串表示的转换，避免了直接用 double 构造时的精度问题和空指针问题

        //总结：如果是基本类型的数值，使用String.valueOf(BigDecimal.valueOf(d));
        //     如果是包装类型的数值，使用String.valueOf(BigDecimal.valueOf(Optional.ofNullable(d).orElse(0.0)));


        System.out.println("ZeroOneEnum.find(\"2\") = " + ZeroOneEnum.find("1"));
    }
}
