package com.example.jingbin.cloudreader.utils;

import android.text.TextUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    private static long timeMilliseconds;

    /**
     * 如果在1分钟之内发布的显示"刚刚" 如果在1个小时之内发布的显示"XX分钟之前" 如果在1天之内发布的显示"XX小时之前"
     * 如果在今年的1天之外的只显示“月-日”，例如“05-03” 如果不是今年的显示“年-月-日”，例如“2014-03-11”
     */
    public static String getTranslateTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
        if (date != null) {
            timeMilliseconds = date.getTime();
        }

        long timeDifferent = currentMilliseconds - timeMilliseconds;


        if (timeDifferent < 60000) {// 一分钟之内

            return "刚刚";
        }
        if (timeDifferent < 3600000) {// 一小时之内
            long longMinute = timeDifferent / 60000;
            int minute = (int) (longMinute % 100);
            return minute + "分钟之前";
        }
        long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
        if (timeDifferent < l) {// 小于一天
            long longHour = timeDifferent / 3600000;
            int hour = (int) (longHour % 100);
            return hour + "小时之前";
        }
        if (timeDifferent >= l) {
            String currYear = currDate.substring(0, 4);
            String year = time.substring(0, 4);
            if (!year.equals(currYear)) {
                return time.substring(0, 10);
            }
            return time.substring(5, 10);
        }
        return time;
    }

    /**
     * 获取当前日期
     */
    public static String getData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取当前时间是否大于12：30
     */
    public static boolean isRightTime() {
        // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        int hour = t.hour; // 0-23
        int minute = t.minute;
        return hour > 12 || (hour == 12 && minute >= 30);
    }

    /**
     * 得到上一天的时间
     */
    public static ArrayList<String> getLastTime(String year, String month, String day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));//月份是从0开始的，所以11表示12月

        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int inDay = ca.get(Calendar.DATE);
        ca.set(Calendar.DATE, inDay - 1);

        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(ca.get(Calendar.YEAR)));
        list.add(String.valueOf(ca.get(Calendar.MONTH) + 1));
        list.add(String.valueOf(ca.get(Calendar.DATE)));
        return list;
    }

    public static String formatDataTime(long timeMilliseconds) {
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = sDateFormat.format(timeMilliseconds);
            return date;
        } catch (Exception e) {
            return getData();
        }
    }

    public static String getTime(long timeMilliseconds) {
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(timeMilliseconds);
            return date;
        } catch (Exception e) {
            return getData();
        }
    }


    public static Date getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        try {
            return sDateFormat.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 获取当前月份的第几天
     */
    public static String getDay() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd");
        String date = sDateFormat.format(new Date());
        return date;
    }

}
