package com.bx.hutool_;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

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
            System.out.println("beginDate = " + beginDate);
            System.out.println("endDate = " + endDate);
//            builder.and(qInvtXmlShaller.appTime.stringValue().between(beginDate, endDate));
        }

        //2.选择同一天会跨到第二天的时间选择器(字符串)
        String beginTime = "2025-01-15T00:00:00.000+0800";
        String endTime = "2025-01-16T08:00:00.000+0800";
        if (StrUtil.isNotBlank(beginTime) && StrUtil.isNotBlank(endTime)) {
            beginTime = beginTime.split("T")[0];
            endTime = getEndTimeStr(beginTime, endTime);
            System.out.println("beginTime = " + beginTime);
            System.out.println("endTime = " + endTime);
//            builder.and(qInvtXmlShaller.appTime.stringValue().between(beginTime, endTime));
        }

        //3.选择同一天会跨到第二天的时间选择器(Date)
        beginTime = "2025-12-28T00:00:00.000+0800";
        endTime = "2025-12-29T08:00:00.000+0800";
        Date begin= DateUtil.parseDate(beginTime.split("T")[0]);
        Date end = getEndTime(begin, endTime);
        System.out.println("begin = " + begin);
        System.out.println("end = " + end);
//        hql.append(" and i.accountingDate BETWEEN ? and ?");
//        linkedList.add(beginSaveDate);
//        linkedList.add(endSaveDate);
    }



    /**
     * 获取结束时间
     * 处理前端时间选择器选择同一天时，结束时间是第二天日期+时区小时的情况
     *
     * @param beginTime 开始时间（yyyy-MM-dd HH:mm:ss）
     * @param endTime 结束时间字符串（yyyy-MM-ddTHH:mm:ss.SSS+时区）
     * @return Date 结束时间（yyyy-MM-dd HH:mm:ss）
     */
    public static Date getEndTime(Date beginTime, String endTime) {
        String endDate = endTime.split("T")[0];
        String endHour = endTime.split("T")[1];
        long dayDiff = DateUtil.between(beginTime, DateUtil.parseDate(endDate), DateUnit.DAY);
        if (dayDiff == 1 && !endHour.startsWith("00:00:00")) {
            endDate = beginTime.toString().split(" ")[0];
        }
        return DateUtil.parseDateTime(endDate + " 23:59:59");
    }

    /**
     * 获取结束时间字符串
     * 处理前端时间选择器选择同一天时，结束时间是第二天日期+时区小时的情况
     *
     * @param beginDate 开始日期字符串（yyyy-MM-dd）
     * @param endTime 结束时间字符串（yyyy-MM-ddTHH:mm:ss.SSS+时区）
     * @return String 结束时间字符串（yyyy-MM-dd 23:59:59）
     */
    public static String getEndTimeStr(String beginDate, String endTime) {
        String endDate = endTime.split("T")[0];
        String endHour = endTime.split("T")[1];
        long dayDiff = DateUtil.between(DateUtil.parseDate(beginDate), DateUtil.parseDate(endDate), DateUnit.DAY);
        if (dayDiff == 1 && !endHour.startsWith("00:00:00")) {
            endDate = beginDate;
        }
        return endDate + " 23:59:59";
    }
}
