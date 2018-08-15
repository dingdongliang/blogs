package net.htjs.blog.util;

import net.htjs.blog.constant.DateEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * blog/net.htjs.blog.util
 *
 * @Description : JDK1.8日期操作类：LocalDate、LocalTime、LocalDateTime
 * @Author : dingdongliang
 * @Date : 2018/4/27 9:05
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 获取yyyy-MM-dd日期格式
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:06
     */
    public static String getDate() {
        return LocalDate.now().toString();
    }

    /**
     * 得到给定格式化日期格式
     *
     * @param pattern 格式化日期
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:07
     */
    public static String getPatternDate(String pattern) {
        return formatDate(LocalDate.now(), pattern);
    }

    /**
     * 获取第一个格式的日期， 默认格式为yyyy-MM-dd
     *
     * @param localDate 日期对象
     * @param pattern   格式字符串，可为空，可为多个，但只用第一个
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:51
     */
    public static String formatDate(LocalDate localDate, Object... pattern) {

        DateTimeFormatter dateTimeFormatter;
        if (pattern != null && pattern.length > 0) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern[0].toString());

        } else {
            dateTimeFormatter = DateTimeFormatter.ofPattern(DateEnum.DATE.getPattern());

        }
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 得到日期时间字符串，转换格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param localDate 本地时间
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:46
     */
    public static String formatDateTime(LocalDate localDate) {
        return formatDate(localDate, DateEnum.DATE_TIME.getPattern());
    }

    /**
     * 得到当前时间字符 格式（HH:mm:ss)
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:12
     */
    public static String getTime() {
        return LocalTime.now().withNano(0).toString();
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss)
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:12
     */
    public static String getDateTime() {
        return LocalDateTime.now().withNano(0).toString();
    }

    /**
     * 得到当前年份字符 格式（yyyy)
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:12
     */
    public static String getYear() {
        return Integer.toString(LocalDate.now().getYear());
    }

    /**
     * 得到当前月份字符 格式（MM)
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:12
     */
    public static String getMonth() {
        return Integer.toString(LocalDate.now().getMonthValue());
    }

    /**
     * 得到当天字符 格式（dd)
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:13
     */
    public static String getDay() {
        return Integer.toString(LocalDate.now().getDayOfMonth());
    }

    /**
     * 得到当前星期字符 格式（E）星期几
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/27 9:13
     */
    public static String getWeek() {
        return LocalDate.now().getDayOfWeek().name();
    }
}
