package com.tudouni.makemoney.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by codbking on 2016/12/15.
 */

public class DateUtils {

    //获取小时
    public static int getHour(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.HOUR_OF_DAY);
    }

    //获取分钟
    public static int getMinute(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.MINUTE);
    }

    //获取周
    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取周
    public static int getWeek(int year,int moth,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,moth-1,day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取年
    public static int getYear(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.YEAR);
    }

    //获取月
    public static int getMoth(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.MONTH)+1;
    }

    //获取日
    public static int getDay(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.DATE);
    }

    public static Date getDate(int year, int moth, int day,int hour,int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth - 1, day, hour, minute);
        return calendar.getTime();
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    public static void main(String[] args) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            Date date=format.parse("2016-12-15 12");
            System.out.println(getHour(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




    public static Date strToDate(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String strToStr(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToStr(date,pattern);
    }

    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
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


    public static String formatDisplayTime_mycircle(String time, String pattern) {
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
                    SimpleDateFormat halfDf2 = new SimpleDateFormat("yyyy年MM月dd日");
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
                            display = halfDf2.format(tDate);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }


    public static String formatDisplayTime2(String time, String pattern) {

        String str = "";
        String nowTime = getStringToday();
        long now = longPattern(nowTime,pattern,"yyyyMMdd");
        long dataTime = longPattern(time,pattern,"yyyyMMdd");
        if(now-dataTime==0){
            str = "今天";
        }else if(now-dataTime==1){
            str = "昨天";
        }else{
            str = stringPattern(time,pattern,"MM月,dd");
        }
        return str;
    }


    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    public static Long longPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return null;
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象
        Date d = null ;
        try{
            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace() ;       // 打印异常信息
        }
        return Long.parseLong(sdf2.format(d));
    }

    public static String stringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return null;
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象
        Date d = null ;
        try{
            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace() ;       // 打印异常信息
        }
        return  sdf2.format(d);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern) ;
        return sdf.format(date);
    }


    /**

     * 把毫秒转化成日期

     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)

     * @param millSec(毫秒数)

     * @return

     */

    public static String msgTime(String dateFormat, Long millSec){

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        Date date= new Date(millSec);

        String time = dateToStr(date,"yyyy-MM-dd HH:mm:ss");

        return formatDisplayTime(time,"yyyy-MM-dd HH:mm:ss");

    }


}
