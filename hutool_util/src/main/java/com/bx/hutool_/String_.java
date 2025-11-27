package com.bx.hutool_;

/**
 * @author lili
 * @version 1.0
 * @date 2025/10/15 11:18
 * @description
 */
public class String_ {
    public static void main(String[] args) {
//        //null，抛出 NullPointerException
        String s = null;
//        String[] split = s.split("-");

        //空字符串，返回包含1个空字符串的数组
        s = "";
        String[] split1 = s.split("-");
        System.out.println("split1(空字符串) = " + split1.length);
        System.out.println(split1[0]);

        //空白串，返回包含1个空白串的数组
        s = " ";
        String[] split2 = s.split("-");
        System.out.println("split2(空白串) = " + split2.length);
        System.out.println(split2[0]);

        //不带"-"，返回原字符串
        s = "ab";
        String[] split3 = s.split("-");
        System.out.println("split3(不带-) = " + split3.length);
        System.out.println(split3[0]);

        //"-"在两个字符中间，正常分割
        s = "a-b";
        String[] split4 = s.split("-");
        System.out.println("split4(-在中间) = " + split4.length);
        System.out.println(split4[0]);
        System.out.println(split4[1]);

        //"-"在末尾，不保留末尾空串(length=2)
        s = "a-b-";
        String[] split5 = s.split("-");
        System.out.println("split5(-在末尾) = " + split5.length);
        System.out.println(split5[0]);
        System.out.println(split5[1]);

        //连续两个"-"，"-"和"-"之间会分割出一个空白串
        s = "a--b";
        String[] split6 = s.split("-");
        System.out.println("split6(连续2个-) = " + split6.length);
        System.out.println(split6[0]);
        System.out.println(split6[1]);//"-"和"-"之间会分割出一个空白串
        System.out.println(split6[2]);

        //-在开头，"-"前面会分割出一个空白串
        s = "-a-b";
        String[] split7 = s.split("-");
        System.out.println("split7(-在开头) = " + split7.length);
        System.out.println(split7[0]);//"-"前面会分割出一个空白串
        System.out.println(split7[1]);
        System.out.println(split7[2]);

        //只有"-"，返回空数组(length=0)
        s = "--";
        String[] split8 = s.split("-");
        System.out.println("split8(只有-) = " + split8.length);

    }
}
