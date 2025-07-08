package com.bx.hutool_;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/13 10:09
 */
public class DateUtil_ {
    //日期时间工具类
    public static void main(String[] args) {
        // 1.date()：获取当前的日期时间，格式：yyyy-MM-dd HH:mm:ss
        DateTime dateTime = DateUtil.date();
        System.out.println("DateUtil.date() = " +dateTime );

        // 2.now()：当前日期时间字符串
        String now = DateUtil.now();
        System.out.println("DateUtil.now() = " +now );

        // 3.format(字符串形式的格式的日期或时间对象)：将日期或时间转成想要的格式
        System.out.println("DateUtil.format(dateTime, \"YYYY/MM/dd\") = " + DateUtil.format(dateTime, "YYYY/MM/dd"));

        Date date1 = new Date();
        System.out.println("date1 = " + date1);
        // 4.formatDate()：将日期格式化为yyyy-MM-dd字符串
        System.out.println("DateUtil.formatDate(date1) = " + DateUtil.formatDate(date1));

        // 5.formatDateTime()：将日期时间格式化为yyyy-MM-dd HH:mm:ss字符串
        String formattedDate = DateUtil.formatDateTime(date1);
        System.out.println("DateUtil.formatDateTime(date1) = " + formattedDate);

        // 6.formatTime()：将时间格式化为HH:mm:ss字符串
        System.out.println("DateUtil.formatTime(date1) = " + DateUtil.formatTime(date1));

        // 7.parse(字符串)：将字符串解析为日期时间
        DateTime date = DateUtil.parse("2025-01-09T00:00:00.000+0800");
        System.out.println("字符串解析后的日期时间：" + date);

        String s = new String();
//        DateTime parse = DateUtil.parse(s);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date parse = formatter.parse(s);
            System.out.println("parse = " + parse);

            System.out.println("ObjectUtil.isNotEmpty(parse) = " + ObjectUtil.isNotEmpty(parse));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("DateUtil.year(dateTime) = " + DateUtil.year(dateTime));
        //获取月份，从0开始
        System.out.println("DateUtil.month(dateTime) = " + DateUtil.month(dateTime));
        System.out.println("DateUtil.dayOfMonth(dateTime) = " + DateUtil.dayOfMonth(dateTime));

        //时间偏移
        //昨天
        System.out.println("昨天:" + DateUtil.yesterday());
        //明天
        System.out.println("明天:"+DateUtil.tomorrow());
        //上周
        System.out.println("上周:"+DateUtil.lastWeek());
        //下周
        System.out.println("下周:"+DateUtil.nextWeek());
        //上个月
        System.out.println("上个月:"+DateUtil.lastMonth());
        //下个月
        System.out.println("下个月:"+DateUtil.nextMonth());

        //在原来时间上加上多长时间，秒 分钟 小时 天 周 月
        /**
         * DateUtil.offsetSecond()
         * DateUtil.offsetMinute()
         * DateUtil.offsetHour()
         * DateUtil.offsetDay()
         * DateUtil.offsetWeek()
         * DateUtil.offsetMonth()
         */

        //加2小时
        System.out.println("DateUtil.offsetHour(dateTime,2) = " + DateUtil.offsetHour(dateTime, 2));
        //加3天
        System.out.println("DateUtil.offsetDay(dateTime,3) = " + DateUtil.offsetDay(dateTime, 3));

        //一天的开始和结束
        System.out.println("DateUtil.beginOfDay(dateTime) = " + DateUtil.beginOfDay(dateTime));
        System.out.println("DateUtil.endOfDay(dateTime) = " + DateUtil.endOfDay(dateTime));

    }
}
