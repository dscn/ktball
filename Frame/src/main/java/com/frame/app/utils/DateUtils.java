package com.frame.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jy on 16/4/22.
 */
public class DateUtils {

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 返回的格式为yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    public static String formatMsgDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(new Date(date));
    }

    public static String formatDateYYYYMMDD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String formatDateYYYYMMDD(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(date));
    }

    public static String formatDateYYYYMMDDHHMM(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(date));
    }

    /**
     * 格式化时间为 19:01 格式
     *
     * @param ms
     * @return
     */
    public static String formatHHmm(long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(ms);
    }

    /**
     * 获取今日零时的毫秒值
     *
     * @return
     */
    public static long getTodayMs() {
        long ms = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(ms);
        try {
            Date date = sdf.parse(today);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getTodayMs(long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(ms);
        try {
            Date date = sdf.parse(today);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String stringTOString(String data, String dateFormat, String toDateFormat) {
        String time = getTime(data,dateFormat);
        LogUtils.e(time);
        return getStrTime(Long.parseLong(time),toDateFormat);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param user_time
     * @return
     */
    public static String getTime(String user_time, String dateFormat) {
        String time = "0";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(dateFormat);
        Date date= null;
        try {
            date = simpleDateFormat.parse(user_time);
            long timeStemp = date.getTime();
            time = timeStemp+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param cc_time
     * @return
     */
    public static String getStrTime(long cc_time,String toDateFormat) {
        SimpleDateFormat format =  new SimpleDateFormat(toDateFormat);
        return format.format(cc_time);
    }

}
