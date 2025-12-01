package com.bx.hutool_;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

        Date date = DateUtil.parse("2025-01-09T00:00:00.000+0800", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        System.out.println("date = " + date);

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
        Date time = DateUtil.parse("2025-01-09T00:00:00.000+0800");
        System.out.println("字符串解析后的日期时间：" + time);

//        String s =new String();
//        DateTime parse = DateUtil.parse(s);//java.lang.IllegalArgumentException: Date String must be not blank !
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
////            Date parse = formatter.parse(s);//Unparseable date: ""，" "
////            System.out.println("parse = " + parse);
////            System.out.println("ObjectUtil.isNotEmpty(parse) = " + ObjectUtil.isNotEmpty(parse));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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


        //时间条件查询
        //1.正常的时间选择器
        String beginDate = "2025-01-09T00:00:00.000+0800";
        String endDate = "2025-01-09T00:00:00.000+0800";
        if (StrUtil.isNotBlank(beginDate) && StrUtil.isNotBlank(endDate)) {
            beginDate = beginDate.split("T")[0];
            endDate = endDate.split("T")[0] + " 23:59:59";
//            builder.and(qInvtXmlShaller.appTime.stringValue().between(beginDate, endDate));
        }

        //2.选择同一天会跨到第二天的时间选择器
        String beginTime = "2025-01-09T00:00:00.000+0800";
        String endTime = "2025-01-09T00:00:00.000+0800";
        if (StrUtil.isNotBlank(beginTime) && StrUtil.isNotBlank(endTime)) {
            beginTime = beginTime.split("T")[0];
            endDate = endTime.split("T")[0];
            String endHour = endTime.split("T")[1];
            //由于前端有种时间选择器选择同一天时，结束时间是第二天的日期+所处时区小时，所以判断是选择同一天还是相隔2天
            long dayDiff = DateUtil.between(DateUtil.parseDate(beginTime), DateUtil.parseDate(endDate), DateUnit.DAY);
            if (dayDiff == 1 && !endHour.startsWith("00:00:00")) {
                //如果结束日期是第二天，并且结束时间不是00:00:00，则说明选择了同一天，结束日期改为开始日期
                endDate = beginTime;
            }
            endTime = endDate + " 23:59:59";
//            builder.and(qInvtXmlShaller.appTime.stringValue().between(beginTime, endTime));
        }
    }
}
