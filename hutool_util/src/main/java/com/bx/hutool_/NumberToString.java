package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/9 11:05
 * @description Number转换为String
 */
public class NumberToString {
    public static void main(String[] args) {
        //1、整数转为字符串
        int i = 1;//基本类型整数
        Integer integerNull = null;//包装类型整数，为null时
        Integer integer = 10;//包装类型整数，不为null


        //1.1 String.valueOf()
        //基本整数
        String iString = String.valueOf(i);
        System.out.println("iString = " + iString);
        //包装整数null
        String integerNullString = String.valueOf(integerNull);//字符串"null"
        System.out.println("integerNullString = " + integerNullString);
        //包装整数不为null
        String integerString = String.valueOf(integer);
        System.out.println("integerString = " + integerString);


        //1.2 Optional.ofNullable().map(String::valueOf).orElse("0");
        System.out.println("-------------------------------------------\n");
        String s = Optional.ofNullable(i).map(String::valueOf).orElse("0");
        System.out.println("s = " + s);
        String s1 = Optional.ofNullable(integerNull).map(String::valueOf).orElse("0");
        System.out.println("s1 = " + s1);
        String s2 = Optional.ofNullable(integer).map(String::valueOf).orElse("0");
        System.out.println("s2 = " + s2);



        //1.3 hutool的Convert.toStr()
        System.out.println("-------------------------------------------\n");
        //基本整数
        String str = Convert.toStr(i,"0");
        System.out.println("str = " + str);
        //包装整数null
        String str2 = Convert.toStr(integerNull);//保持null
        System.out.println("str2 = " + str2);
        //包装整数不为null
        String str3 = Convert.toStr(integer,"0");
        System.out.println("str3 = " + str3);



        //1.4 Objects.toString()
        System.out.println("-------------------------------------------\n");
        //基本整数
        String string = Objects.toString(i);
        System.out.println("string = " + string);
        //包装整数null
        String string2 = Objects.toString(integerNull);//如果为null，则转为"null"
        String string21 = Objects.toString(integerNull,"0");//如果为null，则转为"0"
        System.out.println("string2 = " + string2);
        System.out.println("string21 = " + string21);
        //包装整数不为null
        String string3 = Objects.toString(integer,"0");
        System.out.println("string3 = " + string3);

        //总结：
        //1、基本类型整数，使用String.valueOf(i)
        //2、包装类型整数：
        //   2.1、不需要特殊处理null，使用String.valueOf(i)
        //   2.2、需要将null转为"0"，使用Objects.toString(i, "0")
        //   2.3、需要将null转为""，使用commons.lang3的  ObjectUtils.toString(i)
        //   2.4、需要保持null，使用hutool的  Convert.toStr(i)




        //2、浮点数类型转换为String时特别注意，如果数值类型是包装类为null时
        System.out.println("-------------------------------------------\n");
        Double d = null;
//        String dString = String.valueOf(new BigDecimal(d));// 抛出 NullPointerException，因为 new BigDecimal(null) 不允许。
//        String dString = String.valueOf(BigDecimal.valueOf(d));// 抛出 NullPointerException，因为 BigDecimal.valueOf(null) 不允许。
//        String dString = String.valueOf(d);//输出字符串"null"
        String dString = Optional.ofNullable(d).map(String::valueOf).orElse("0.0");//有值输出其值，为null默认为0.0
        System.out.println("包装类型null，dString = " + dString);
        dString.toString();

        //3、如果d为double或Double类型,当值为123.45时
        d = 123.45;
        double d1 = 123.45678;
        String result1 = String.valueOf(new BigDecimal(d));
        System.out.println("result1 = " + result1);//输出 "123.4500000000000028421709430404007434844970703125"
        String result2 = String.valueOf(new BigDecimal(d1));
        System.out.println("result2 = " + result2);//输出 "123.4567799999999948568074614740908145904541015625"
        //原因：new BigDecimal(double)时，`double` 和`Double`转换为 `BigDecimal` 时会保留浮点误差


        //4、3的解决办法：
        String result3 = Optional.ofNullable(d).map(String::valueOf).orElse("0.0");
        System.out.println("result3 = " + result3);
        String result4 = Optional.ofNullable(d1).map(String::valueOf).orElse("0.0");
        System.out.println("result4 = " + result4);

        String result5 = Objects.toString(d,"0.0");
        System.out.println("result5 = " + result5);
        String result6 = Objects.toString(d1,"0.0");
        System.out.println("result6 = " + result6);




        //总结：
        //1、基本类型浮点数，使用String.valueOf(d);
        //2、如果是包装类型浮点数
        //   2.1、不需要特殊处理null，使用String.valueOf(i)
        //   2.2、需要将null转为"0.0"，使用Objects.toString(i, "0.0")
        //   2.3、需要将null转为""，使用commons.lang3的  ObjectUtils.toString(i)
        //   2.4、需要保持null，使用hutool的  Convert.toStr(i)

    }
}
