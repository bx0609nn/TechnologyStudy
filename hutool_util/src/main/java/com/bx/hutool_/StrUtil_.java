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


//----------------------------------------------------------------------------------------------------------------------

        //null，返回空数组(length=0)
        s = null;
        String[] split = StrUtil.split(s, "-");
        System.out.println("split(null) = " + split.length);
//        System.out.println(split[0]);
        for(String str : split){
            System.out.println("split(null)增强for" + str);
        }

        //空字符串，返回空数组(length=0)
        s = "";
        String[] split1 = StrUtil.split(s, "-");
        System.out.println("split1(空字符串) = " + split1.length);
//        System.out.println(split1[0]);
        for(String str : split1){
            System.out.println("split1(空字符串)增强for" + str);
        }

        //空白串，返回包含1个空白串的数组
        s = " ";
        String[] split2 = StrUtil.split(s, "-");
        System.out.println("split2(空白串) = " + split2.length);
        System.out.println(split2[0]);
        for (String str : split2) {
            System.out.println("split2(空白串)增强for" + str);
        }

        //不带"-"，返回原字符串
        s = "ab";
        String[] split3 = StrUtil.split(s, "-");
        System.out.println("split3(不带-) = " + split3.length);
        System.out.println(split3[0]);

        //"-"在两个字符中间，正常分割
        s = "a-b";
        String[] split4 = StrUtil.split(s, "-");
        System.out.println("split4(-在中间) = " + split4.length);
        System.out.println(split4[0]);
        System.out.println(split4[1]);

        //"-"在末尾，"-"后面还会分割出一个空白串
        s = "a-b-";
        String[] split5 = StrUtil.split(s, "-");
        System.out.println("split5(-在末尾) = " + split5.length);
        System.out.println(split5[0]);
        System.out.println(split5[1]);
        System.out.println(split5[2].toString());//"-"后面还会分割出一个空白串

        //连续两个"-"，"-"和"-"之间会分割出一个空白串
        s = "a--b";
        String[] split6 = StrUtil.split(s, "-");
        System.out.println("split6(连续2个-) = " + split6.length);
        System.out.println(split6[0]);
        System.out.println(split6[1]);//"-"和"-"之间会分割出一个空白串
        System.out.println(split6[2]);

        //-在开头，"-"前面会分割出一个空白串
        s = "-a-b";
        String[] split7 = StrUtil.split(s, "-");
        System.out.println("split7(-在开头) = " + split7.length);
        System.out.println(split7[0]);//"-"前面会分割出一个空白串
        System.out.println(split7[1]);
        System.out.println(split7[2]);

        //只有"-"，前、后、中间都会分割空字符串
        s = "--";
        String[] split8 = StrUtil.split(s, "-");
        System.out.println("split8(只有-) = " + split8.length);
        System.out.println(split8[0]);
        System.out.println(split8[1]);
        System.out.println(split8[2]);

    }
}
