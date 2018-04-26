package com.tudouni.makemoney.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zp on 2017/3/17.
 */
public class TimeUtil {
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static String getDate(Date date) {
        if (isSameDay(date, System.currentTimeMillis())) {
            return "今天";
        }
        SimpleDateFormat format = new SimpleDateFormat("M月d日");
        return format.format(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return date1.equals(date2);
    }

    public static boolean isSameDay(Date date1, long date2) {
        return date1.getTime() == date2;
    }

    public static String getTime(Date createTime) {
        SimpleDateFormat format = new SimpleDateFormat("MM'月'dd'日' HH:mm");
        return format.format(createTime);
    }

    private static DateFormat buildIso8601Format() {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    public static String toIso8601(Date createTime) {
        return buildIso8601Format().format(createTime);
    }

    public static String getStartDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("M月d日");
        return format.format(date);
    }

    public static String dateToString(Date time) {
        // 获取时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(time);
        return ctime;
    }

    public static String getStartDateWithYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
        return format.format(date);
    }

    public static String getYearToMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日 HH:mm");
        return format.format(date);
    }

    public static String getYearToMinute(String timestamp) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        }

        long time_long = Long.parseLong(timestamp);
        Date date = new Date(time_long);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日 HH:mm");
        return format.format(date);
    }

    public static String getYearToMinute(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日 HH:mm");
        return format.format(date);
    }

    public static long getHourToMinute(long timestamp) {
        Date date = new Date(timestamp);
        //        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日 HH:mm");
        return date.getHours();
    }

    public static String getTimeStamp(int year, int month, int day, int hour, int minute) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
        try {
            Date date = format.parse(time);
            result = date.getTime() + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 中间连接处是点
     *
     * @param date
     * @return
     */
    public static String getStartDateWithYearByDot(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    public static String getStartDateWithYearByDot(String timestamp) {
        long time_long = Long.parseLong(timestamp);
        Date date = new Date(time_long);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    public static String getStartDateWithYearStyle2(Date date) {
        String timeString = "";
        try {
            timeString = friendlyTime(date.getTime() / 1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return timeString;

    }

    public static String getTime(Date start, Date end) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(start) + "-" + format.format(end);
    }

    public static String getTime(long start, long end) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(start) + "-" + format.format(end);
    }

    public static String getTwoTypeDate(Date date) {
        if (isSameDay(date, System.currentTimeMillis())) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(date);
        }
        SimpleDateFormat format = new SimpleDateFormat("M月d日");
        return format.format(date);
    }

    public static boolean sameDay(long timestamp, Calendar cal) {
        // 判断是否是同一天
        String curDate = dateFormater.get().format(cal.getTime());
        String paramDate = dateFormater.get().format(timestamp * 1000);
        boolean equals = curDate.equals(paramDate);
        return equals;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param timestamp 单位秒
     * @return
     */
    public static String friendlyTime(long timestamp) {
        Date date = new Date(timestamp * 1000);

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        boolean equals = sameDay(timestamp, cal);
        if (equals) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0) {//小于一小时
                int minute = (int) ((cal.getTimeInMillis() - date.getTime()) / 60000);
                if (minute == 0) {//小于一分钟
                    ftime = "刚刚";
                } else {//大于一分钟
                    ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
                }
            } else {//大于一小时
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = date.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0) {//小于一小时
                int minute = (int) ((cal.getTimeInMillis() - date.getTime()) / 60000);
                if (minute == 0) {//小于一分钟
                    ftime = "刚刚";
                } else {//大于一分钟
                    ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
                }
            } else {//大于一小时
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else {
            ftime = dateFormater.get().format(timestamp * 1000);
        }
        return ftime;
    }

    public static Date stringToDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static String millisToString(float millis) {
        return millisToString(millis, false);
    }

    public static String millisToText(long millis) {
        return millisToString(millis, true);
    }

    /**
     * 从时间(毫秒)中提取出时间(时:分) 时间格式: 时:分
     *
     * @param millisecond
     * @return
     */
    public static String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    // millins转换为mm:ss
    static String millisToString(float millis, boolean text) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);
        if (millis <= 1000 && millis >= 500) {
            millis = 1000;
        }

        millis /= 1000;
        int sec = Math.round(millis % 60);
        if (sec == 60) {
            sec = 0;
            millis++;
        }
        millis /= 60;
        //方案二：以下计算分钟，没有小时
        int min = (int) millis;
        millis = 0;
        int hours = Math.round((int) millis);
        //方案一：以下计算分钟加小时
        //        int min = Math.round((int) millis % 60);
        //        if (min == 60) {
        //            min = 0;
        //            millis++;
        //        }
        //        millis /= 60;
        //        int hours = Math.round((int) millis);
        //        millis = Math.round((int) millis);

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        format.applyPattern("00");
        if (text) {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + "h" + format.format(min) + "min";
            else if (min > 0)
                time = (negative ? "-" : "") + min + "min";
            else
                time = (negative ? "-" : "") + sec + "s";
        } else {
            if (millis > 0)// 有时
                time = (negative ? "-" : "") + format.format(hours) + ":" + format.format(min) + ":" + format.format(sec);
            else if (min > 0)// 无时有分
                time = (negative ? "-" : "") + format.format(min) + ":" + format.format(sec);
            else
                // 无时无分有秒
                time = (negative ? "-" : "") + format.format(min) + ":" + format.format(sec);
        }
        return time;
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        if (data == null)
            return null;
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * long类型转换为String类型
     *
     * @param currentTime
     * @param formatType  要转换的string类型的时间格式
     * @return String类型
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType) {
        String strTime = null;
        try {
            Date date = longToDate(currentTime, formatType); // long类型转成Date类型
            strTime = dateToString(date, formatType); // date类型转成String
        } catch (Exception e) {
            Log.e("JJTimeUtil", e.getMessage());
        } finally {

        }
        return strTime;
    }

    /**
     * string类型转换为date类型，strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param strTime
     * @param formatType 要转换的格式yyyy-MM-dd HH:mm:ss或者yyyy年MM月dd日
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime
     * @param formatType  要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime, String formatType) throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * string类型转换为long类型 注意：strTime的时间格式和formatType的时间格式必须相同
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String strTime, String formatType) {
        try {
            Date date = stringToDate(strTime, formatType); // String类型转成date类型
            if (date == null) {
                return 0;
            } else {
                long currentTime = dateToLong(date); // date类型转成long类型
                return currentTime;
            }
        } catch (Exception e) {
            Log.e("======>", "e:" + e.getMessage());
            return 0;
        }
    }

    /**
     * date类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String getStartWithMinute(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);

        return hms;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {
        if (!TextUtils.isEmpty(day)) {

            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);
            Calendar cal = Calendar.getInstance();
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(day);
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == 0) {
                    return true;
                }
            }
        }
        //如果不是今天的话，App初次启动时间更改、广告的启动次数清零
        //        String nowTime = TimeUtil.longToString(System.currentTimeMillis(), "yyyy-MM-dd");
        //        DictionaryUtils.getInstance().putString(Constants.APP_TADY_FIRST_START_TIME, nowTime);
        //        DictionaryUtils.getInstance().putInt(Constants.START_PAGE_AD_SHOW_COUNT, 0);
        return false;
    }


    /**
     * @return
     */
    public static String stringForTimeByQUote(String startTime, String endTime) {
        String[] srarts = startTime.split(":");
        String[] ends = endTime.split(":");
        int seconds = 0;
        int minutes = 0;
        seconds = Integer.parseInt(ends[2]) - Integer.parseInt(srarts[2]);
        minutes = Integer.parseInt(ends[1]) - Integer.parseInt(srarts[1]) + (Integer.parseInt(ends[0]) - Integer.parseInt(srarts[0])) * 60;
        if (seconds < 0) {
            minutes--;
            seconds = seconds + 60;
        }
        return minutes + "'" + seconds + "\"";
    }


    /**
     * 格式化时间（输出类似于 刚刚, 4分钟前, 一小时前, 昨天这样的时间）
     *
     * @param time    需要格式化的时间 如"2014-07-14 19:01:45"
     * @param pattern 输入参数time的时间格式 如:"yyyy-MM-dd HH:mm:ss"
     *                <p/>如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     */
    public static String formatDisplayTime(String time, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);

                Date today = new Date();
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy");
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime());
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    SimpleDateFormat halfDf = new SimpleDateFormat("MM月dd日");
                    long dTime = today.getTime() - tDate.getTime();
                    if (tDate.before(thisYear)) {
                        display = new SimpleDateFormat("yyyy年MM月dd日").format(tDate);
                    } else {
                        if (dTime < tMin) {
                            display = "刚刚";
                        } else if (dTime < tHour) {
                            display = (int) Math.ceil(dTime / tMin) + "分钟前";
                        } else if (dTime < tDay && tDate.after(yesterday)) {
                            display = (int) Math.ceil(dTime / tHour) + "小时前";
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                            display = "昨天" + new SimpleDateFormat("HH:mm").format(tDate);
                        } else {
                            display = halfDf.format(tDate);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }
}
