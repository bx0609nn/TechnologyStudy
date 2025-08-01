package com.bx.hutool_;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author lili
 * @version 1.0
 * @date 2025/7/30 9:47
 * @description
 */
public class NumberUtilsVsConvertComparison {

    public static void main(String[] args) {
        // 测试用例
        String[] testCases = {null, "", "  ", "111", "hfsd", "123.45", "-456", "-34.76", "kldsfk", "        "};

        System.out.println("=== 行为差异对比 ===");
        behaviorComparison(testCases);

        System.out.println("\n=== 性能测试 ===");
        performanceTest();
    }

    /**
     * 行为差异对比
     */
    public static void behaviorComparison(String[] testCases) {
        System.out.printf("%-10s | %-15s | %-15s | %-10s%n",
                "Input", "NumberUtils", "Hutool Convert", "是否相同");
        System.out.println("-----------|-----------------|-----------------|----------");

        for (String test : testCases) {
            // NumberUtils处理
            int numberUtilsResult = NumberUtils.toInt(test, 0);

            // Hutool Convert处理
            int convertResult = Convert.toInt(test, 0);

            boolean isSame = numberUtilsResult == convertResult;

            System.out.printf("%-10s | %-15d | %-15d | %-10s%n",
                    test == null ? "null" : ("'" + test + "'"),
                    numberUtilsResult, convertResult, isSame);
        }
    }

    /**
     * 性能测试
     */
    public static void performanceTest() {
        String[] testData = {"123", "456", "789", "0", "-123", "999"};
        int iterations = 1_000_000;

        // 预热
        for (int i = 0; i < 10000; i++) {
            NumberUtils.toInt(testData[i % testData.length], 0);
            Convert.toInt(testData[i % testData.length], 0);
        }

        // NumberUtils性能测试
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            NumberUtils.toInt(testData[i % testData.length], 0);
        }
        long numberUtilsTime = System.nanoTime() - startTime;

        // Hutool Convert性能测试
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Convert.toInt(testData[i % testData.length], 0);
        }
        long convertTime = System.nanoTime() - startTime;

        System.out.printf("NumberUtils耗时: %.2f ms%n", numberUtilsTime / 1_000_000.0);
        System.out.printf("Hutool Convert耗时: %.2f ms%n", convertTime / 1_000_000.0);
        System.out.printf("性能比: %.2fx%n", (double) convertTime / numberUtilsTime);
    }
}