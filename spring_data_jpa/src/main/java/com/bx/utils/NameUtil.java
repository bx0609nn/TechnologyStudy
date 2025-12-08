package com.bx.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 15:41
 * @description 用户名生成工具类
 */
public class NameUtil {
    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String PREFIX = "用户";

    /**
     * 生成随机用户名
     * 格式：用户 + 时间戳 + 6位随机数
     * 例如：用户20231203143025123456
     */
    public static String generateName() {
        String timestamp = LocalDateTime.now().format(formatter);
        int randomNum = 100000 + random.nextInt(900000);
        return PREFIX + timestamp + randomNum;
    }

    // 测试方法
    public static void main(String[] args) {
        // 生成10个示例用户名
        System.out.println("生成的随机用户名示例：");
        for (int i = 0; i < 10; i++) {
            System.out.println(generateName());
        }
    }
}
