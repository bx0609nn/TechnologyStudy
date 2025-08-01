package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/7/30 10:15
 * @description
 */
public class ToStringPerformanceComparison {

    public static void main(String[] args) {
        // 测试数据
        Integer nullValue = null;
        Integer intValue = 12345;
        Double nullDouble = null;
        Double doubleValue = 123.45;

        System.out.println("=== 功能对比 ===");
        functionalComparison(nullValue, intValue, nullDouble, doubleValue);

        System.out.println("\n=== 性能测试 ===");
        performanceTest();
    }

    public static void functionalComparison(Integer nullInt, Integer validInt,
                                            Double nullDouble, Double validDouble) {
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "方法", "nullInt", "validInt", "nullDouble", "validDouble");
        System.out.println("-----|---------------|---------------|---------------|---------------");

        // 1. 原生String.valueOf()
        String method1_1 = String.valueOf(nullInt);
        String method1_2 = String.valueOf(validInt);
        String method1_3 = String.valueOf(nullDouble);
        String method1_4 = String.valueOf(validDouble);
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "String.valueOf", method1_1, method1_2, method1_3, method1_4);

        // 2. Objects.toString()
        String method2_1 = Objects.toString(nullInt,"0");
        String method2_2 = Objects.toString(validInt,"0");
        String method2_3 = Objects.toString(nullDouble,"0");
        String method2_4 = Objects.toString(validDouble,"0");
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "Objects.toString", method2_1, method2_2, method2_3, method2_4);

        // 3. Hutool Convert.toStr()
        String method3_1 = Convert.toStr(nullInt, "0");
        String method3_2 = Convert.toStr(validInt, "0");
        String method3_3 = Convert.toStr(nullDouble, "0.0");
        String method3_4 = Convert.toStr(validDouble, "0.0");
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "Convert.toStr", method3_1, method3_2, method3_3, method3_4);

        // 3. Hutool Convert.toStr()
        String method4_1 = Convert.toStr(nullInt);
        String method4_2 = Convert.toStr(validInt);
        String method4_3 = Convert.toStr(nullDouble);
        String method4_4 = Convert.toStr(validDouble);
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "Convert.toStrNull", method4_1, method4_2, method4_3, method4_4);

        // 4. 自定义高性能方法
        String method5_1 = fastToString(nullInt, "0");
        String method5_2 = fastToString(validInt, "0");
        String method5_3 = fastToString(nullDouble, "0.0");
        String method5_4 = fastToString(validDouble, "0.0");
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "自定义fastToString", method5_1, method5_2, method5_3, method5_4);

        // 5. Optional.ofNullable(i).map(String::valueOf).orElse("0")
        String method6_1 = Optional.ofNullable(nullInt).map(String::valueOf).orElse("0");
        String method6_2 = Optional.ofNullable(validInt).map(String::valueOf).orElse("0");
        String method6_3 = Optional.ofNullable(nullDouble).map(String::valueOf).orElse("0");
        String method6_4 = Optional.ofNullable(validDouble).map(String::valueOf).orElse("0");
        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s%n",
                "自定义fastToString", method6_1, method6_2, method6_3, method6_4);
    }

    /**
     * 自定义高性能toString方法
     */
    public static String fastToString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : obj.toString();
    }

    /**
     * 性能测试
     */
    public static void performanceTest() {
        Integer[] testData = {123, 456, null, 789, null, 0, -123, 123, 43535, null, 65654, null, 0, -242};
        int iterations = 1_000_000;

        // 预热
        for (int i = 0; i < 10000; i++) {
            Integer test = testData[i % testData.length];
            String.valueOf(test);
            ObjectUtils.toString(test, "0");
            Convert.toStr(test, "0");
            fastToString(test, "0");
        }

        // 1. String.valueOf
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Integer test = testData[i % testData.length];
            String result =String.valueOf(test);
        }
        long method1Time = System.nanoTime() - startTime;

        // 2. ObjectUtils.toString
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Integer test = testData[i % testData.length];
            String result = Objects.toString(test,"0");
        }
        long method2Time = System.nanoTime() - startTime;

        // 3. Convert.toStr
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Integer test = testData[i % testData.length];
            String result = Convert.toStr(test, "0");
        }
        long method3Time = System.nanoTime() - startTime;

        // 4. Convert.toStrNull
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Integer test = testData[i % testData.length];
            String result = Convert.toStr(test);
        }
        long method4Time = System.nanoTime() - startTime;

        // 5. 自定义方法
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Integer test = testData[i % testData.length];
            String result = fastToString(test, "0");
        }
        long method5Time = System.nanoTime() - startTime;

        // 6. Optional.ofNullable(i).map(String::valueOf).orElse("0")
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Integer test = testData[i % testData.length];
            String result = Optional.ofNullable(test).map(String::valueOf).orElse("0");
        }
        long method6Time = System.nanoTime() - startTime;


        System.out.printf("String.valueOf + 三元: %.2f ms%n", method1Time / 1_000_000.0);
        System.out.printf("Objects.toString: %.2f ms%n", method2Time / 1_000_000.0);
        System.out.printf("Convert.toStr: %.2f ms%n", method3Time / 1_000_000.0);
        System.out.printf("Convert.toStrNull: %.2f ms%n", method4Time / 1_000_000.0);
        System.out.printf("自定义fastToString: %.2f ms%n", method5Time / 1_000_000.0);
        System.out.printf("Optional.ofNullable(i): %.2f ms%n", method6Time / 1_000_000.0);

        // 性能比较
        double ratio1 = (double) method3Time / method1Time;
        double ratio2 = (double) method3Time / method2Time;
        double ratio3 = (double) method3Time / method4Time;
        double ratio4 = (double) method3Time / method5Time;
        double ratio5 = (double) method3Time / method6Time;

        System.out.printf("\n性能比较（以Convert.toStr为基准）:%n");
        System.out.printf("String.valueOf + 三元 快 %.2fx%n", ratio1);
        System.out.printf("Objects.toString 快 %.2fx%n", ratio2);
        System.out.printf("toNull 快 %.2fx%n", ratio3);
        System.out.printf("自定义方法 快 %.2fx%n", ratio4);
        System.out.printf("Optional.ofNullable(i) 快 %.2fx%n", ratio5);
    }
}
