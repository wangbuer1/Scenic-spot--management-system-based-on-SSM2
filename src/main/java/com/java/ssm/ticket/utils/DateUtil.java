package com.java.ssm.ticket.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



//hgy 自己在网上找来的工具类


/**
 * @ Author: Ding <br/>
 * @ Version: V1.0
 * @ Notes: 时间工具类      
 * <p>
 * Created with IDEA. Date：2019/11/22 8:46 下午
 * <a href="https://github.com/Deencode">Github Home Page</a>
 * </p>
 */
public class DateUtil {
    public static final String FMT_YM = "yyyyMM";
    public static final String FMT_Y_M = "yyyy-MM";

    public static final String FMT_YMD = "yyyyMMdd";
    public static final String FMT_Y_M_D = "yyyy-MM-dd";

    public static final String FMT_YMD_HMS = "yyyyMMddHHmmss";
    public static final String FMT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_UTC_Y_M_D_H_M_S = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FMT_UTC_Y_M_D_H_M_S_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String FMT_YMD_HMS_S = "yyyyMMddHHmmssSSS";
    public static final String FMT_Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.SSS";


    public static final String FMT_HMS = "HHmmss";
    public static final String FMT_H_M_S = "HH:mm:ss";

    public static final String FMT_HMS_S = "HHmmssSSS";
    public static final String FMT_H_M_S_S = "HH:mm:ss.SSS";

    public static final String FMT_CHINESE_Y_M_D = "yyyy年MM月dd日";
    public static final String FMT_CHINESE_Y_M_D_H_M_S = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FMT_CHINESE_Y_M_D_H_M_S_S = "yyyy年MM月dd日 HH:mm:ss.SSS";

    public static final String FMT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    private DateUtil dateUtil;

    public enum Field {
        YEAR,
        MONTH,
        WEEK,
        DAY,
        HOUR,   // 24小时制
        MINUTE,
        SECOND,
        MILLISECOND
    }

    /**
     * date转日期字符串
     *
     * @param date
     * @param format
     * @return String
     */
    public static String str(Date date, String format) {
        if (null == date) {
            date = new Date();
        }
        if (StringUtils.isBlank(format)) {
            format = FMT_DEFAULT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(date);
    }

    /**
     * time转日期字符串
     *
     * @param time
     * @param format
     * @return String
     */
    public static String str(long time, String format) {
        return str(new Date(time), format);
    }

    /**
     * 字符串转日期
     *
     * @param dateStr
     * @param format
     * @return Date
     */
    public static Date date(String dateStr, String format) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = FMT_DEFAULT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 字符串日期转time
     *
     * @param dateStr
     * @param format
     * @return String
     */
    public static long time(String dateStr, String format) throws ParseException {
        Date date = date(dateStr, format);
        if (null == date) {
            return 0L;
        }
        return date.getTime();
    }

    /**
     * 获取UTC标准时间（北京时区时间-8小时）
     * Demo: 2018-11-01 09:35:00转换后为2018-11-01T01:35:00
     *
     * @param date
     */
    public static String utc(Date date) {
        if (null == date) {
            date = new Date();
        }
        SimpleDateFormat df = new SimpleDateFormat(FMT_UTC_Y_M_D_H_M_S);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    /**
     * 获取UTC标准时间（北京时区时间-8小时）
     * Demo: 2018-11-01 09:35:00转换后为2018-11-01T01:35:00
     *
     * @param dateStr
     * @param format
     */
    public static String utc(String dateStr, String format) throws ParseException {
        Date date = date(dateStr, format);
        if (null == date) {
            date = new Date();
        }
        return utc(date);
    }

    /**
     * 日期加减操作
     *
     * @param date
     * @param field
     * @param value
     * @return Date
     */
    public static Date add(Date date, Field field, int value) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int calendarField = fieldParse(field);
        calendar.set(calendarField, calendar.get(calendarField) + value);
        return calendar.getTime();
    }

    /**
     * 字符串日期加减操作
     *
     * @param dateStr
     * @param format
     * @param field
     * @param value
     * @return String
     */
    public static String add(String dateStr, String format, Field field, int value) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = FMT_DEFAULT;
        }
        Date date = date(dateStr, format);
        return str(add(date, field, value), format);
    }

    /**
     * 日期Field设置操作
     *
     * @param date
     * @param field
     * @param value
     * @return String
     */
    public static Date set(Date date, Field field, int value) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(fieldParse(field), value);
        return calendar.getTime();
    }

    /**
     * 日期Field设置操作
     *
     * @param date
     * @param fields
     * @param values
     * @return String
     */
    public static Date set(Date date, Field[] fields, int[] values) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (null != fields && fields.length > 0) {
            for (int i = 0; i < fields.length; ++i) {
                calendar.set(fieldParse(fields[i]), values[i]);
            }
        }
        return calendar.getTime();
    }

    /**
     * 日期域set操作，返回set后的日期字符串
     *
     * @param dateStr
     * @param format
     * @param field
     * @param value
     * @return String
     */
    public static String set(String dateStr, String format, Field field, int value) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = FMT_DEFAULT;
        }
        Date date = date(dateStr, format);
        return str(set(date, field, value), format);
    }

    /**
     * 日期域set操作，返回set后的日期字符串
     *
     * @param dateStr
     * @param format
     * @param fields
     * @param values
     * @return String
     */
    public static String set(String dateStr, String format, Field[] fields, int[] values) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = FMT_DEFAULT;
        }
        Date date = date(dateStr, format);
        return str(set(date, fields, values), format);
    }

    /**
     * 日期间隔差，返回指定域的时间差绝对值
     * 注意：这里是做转换成Time后的值比较，不是按域比较。如：
     * 1. 小于24小时，日、周、月、年值为0
     * 2. 大于等于24小时，小于48小时，日值为1，周、月、年值为0
     *
     * @param date1
     * @param date2
     * @param field
     * @return int
     */
    public static long between(Date date1, Date date2, Field field) {
        if (null == date1 && null == date2) {
            return 0;
        }
        if (null == date1) {
            date1 = new Date();
        }
        if (null == date2) {
            date2 = new Date();
        }
        long millSeconds = Math.abs(date1.getTime() - date2.getTime());
        switch (field) {
            case SECOND:
                return millSeconds / 1000;
            case MINUTE:
                return millSeconds / 1000 / 60;
            case HOUR:
                return millSeconds / 1000 / 3600;
            case DAY:
                return millSeconds / 1000 / 3600 / 24;
            case WEEK:
                return millSeconds / 1000 / 3600 / 24 / 7;
            case MONTH:
                return millSeconds / 1000 / 3600 / 24 / 30;
            case YEAR:
                return millSeconds / 1000 / 3600 / 24 / 365;
        }
        return millSeconds;
    }

    /**
     * 字符串日期间隔差，返回指定域的时间差绝对值
     *
     * @param dateStr1
     * @param dateStr2
     * @param field
     * @return int
     */
    public static long between(String dateStr1, String dateStr2, String format, Field field) throws ParseException {
        if (StringUtils.isBlank(dateStr1) && StringUtils.isBlank(dateStr2)) {
            return 0;
        }
        if (StringUtils.isBlank(format)) {
            format = FMT_DEFAULT;
        }
        Date date1 = date(dateStr1, format);
        Date date2 = date(dateStr2, format);
        return between(date1, date2, field);
    }

    /**
     * 日期对比
     *
     * @param date1
     * @param date2
     * @return long
     */
    public static long compare(Date date1, Date date2) {
        if (null == date1 && null == date2) {
            return 0;
        }
        if (null == date1) {
            date1 = new Date();
        }
        if (null == date2) {
            date2 = new Date();
        }
        return date1.getTime() - date2.getTime();
    }

    /**
     * 返回当前日期所在月有多少天
     *
     * @param date
     * @return int
     */
    public static int daysOfMonth(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回当前日期所在年有多少天
     *
     * @param date
     * @return int
     */
    public static int daysOfYear(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取日期所在月第一天
     *
     * @param date
     * @param time 自定义时间（默认00:00:00）
     * @return Date
     */
    public static Date firstDayOfMonth(Date date, String time) {
        if (null == date) {
            date = new Date();
        }
        if (StringUtils.isBlank(time)) {
            time = "000000";
        }
        return getDate(date, time);
    }

    /**
     * 获取日期所在月最后一天
     *
     * @param date
     * @param time 自定义时间（默认23:59:59）
     * @return Date
     */
    public static Date lastDayOfMonth(Date date, String time) {
        if (null == date) {
            date = new Date();
        }
        if (StringUtils.isBlank(time)) {
            time = "235959";
        }
        return getDate(date, time);
    }


    public static Timestamp asDateToTimestamp(){
        return new java.sql.Timestamp(new java.util.Date().getTime());
    }

    private static Date getDate(Date date, String time) {
        time = time.replaceAll("[/\\-:]", "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(2, 4)));
        calendar.set(Calendar.SECOND, Integer.parseInt(time.substring(4, 6)));
        return calendar.getTime();
    }


    /**
     * 返回 {@link java.util.Calendar} 定义的日期域
     *
     * @param field
     * @return int
     */
    private static int fieldParse(Field field) {
        switch (field) {
            case YEAR:
                return Calendar.YEAR;
            case MONTH:
                return Calendar.MONDAY;
            case WEEK:
                return Calendar.WEEK_OF_YEAR;
            case DAY:
                return Calendar.DAY_OF_MONTH;
            case HOUR:
                return Calendar.HOUR_OF_DAY;
            case MINUTE:
                return Calendar.MINUTE;
            case SECOND:
                return Calendar.SECOND;
            case MILLISECOND:
                return Calendar.MILLISECOND;
        }
        return Calendar.YEAR;
    }

    /**
     * 获取 对应日期的 对应值
     *
     * @param date
     * @param field
     * @return
     */
    public static int field(Date date, Field field) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case YEAR:
                return calendar.get(Calendar.YEAR);
            case MONTH:
                calendar.add(Calendar.MONDAY, 1);
                return calendar.get(Calendar.MONDAY);
            case WEEK:
                return calendar.get(Calendar.WEEK_OF_YEAR);
            case DAY:
                return calendar.get(Calendar.DAY_OF_MONTH);
            case HOUR:
                return calendar.get(Calendar.HOUR_OF_DAY);
            case MINUTE:
                return calendar.get(Calendar.MINUTE);
            case SECOND:
                return calendar.get(Calendar.SECOND);
            case MILLISECOND:
                return calendar.get(Calendar.MILLISECOND);
        }
        return calendar.get(Calendar.YEAR);
    }

}