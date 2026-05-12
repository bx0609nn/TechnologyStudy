package com.bx.utils;

import cn.hutool.core.date.DateUnit;

import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2026/4/30 14:07
 * @description 时间工具类
 */
public class DateUtil {
    public static final String TIME_STAMP = "yyyyMMddHHmmss";
    public static final String TIME = "yyMMdd";
    public static final String DATE_NEW = "yyyy-MM-dd'T'HH:mm:ss.SSS'+0800'";
    public static final String DATE = "yyyyMMdd";
    private static final String END_OF_DAY = " 23:59:59";
    private static final String START_TIME = "00:00:00";
    private static final String T = "T";

    /**
     * 获取结束时间字符串
     * 处理前端时间选择器选择同一天时，结束时间是第二天日期+时区小时的情况
     *
     * @param beginDate 开始日期字符串（yyyy-MM-dd）
     * @param endTime 结束时间字符串（yyyy-MM-ddTHH:mm:ss.SSS+时区）
     * @return String 结束时间字符串（yyyy-MM-dd HH:mm:ss）
     */
    public static String getEndTime(String beginDate, String endTime) {
        String endDate = endTime.split(T)[0];
        String endHour = endTime.split(T)[1];
        long dayDiff = cn.hutool.core.date.DateUtil.between(cn.hutool.core.date.DateUtil.parseDate(beginDate), cn.hutool.core.date.DateUtil.parseDate(endDate), DateUnit.DAY);
        if (dayDiff == 1 && !endHour.startsWith(START_TIME)) {
            endDate = beginDate;
        }
        return endDate + END_OF_DAY;
    }

    /**
     * 获取结束时间
     * 处理前端时间选择器选择同一天时，结束时间是第二天日期+时区小时的情况
     *
     * @param beginDate 开始日期（yyyy-MM-dd）
     * @param endTime 结束时间字符串（yyyy-MM-ddTHH:mm:ss.SSS+时区）
     * @return Date 结束时间（yyyy-MM-dd HH:mm:ss）
     */
    public static Date getEndTime(Date beginDate, String endTime) {
        String endDate = endTime.split(T)[0];
        String endHour = endTime.split(T)[1];
        long dayDiff = cn.hutool.core.date.DateUtil.between(beginDate, cn.hutool.core.date.DateUtil.parseDate(endDate), DateUnit.DAY);
        if (dayDiff == 1 && !endHour.startsWith(START_TIME)) {
            endDate = beginDate.toString().split(" ")[0];
        }
        return cn.hutool.core.date.DateUtil.parseDateTime(endDate + END_OF_DAY);
    }

}