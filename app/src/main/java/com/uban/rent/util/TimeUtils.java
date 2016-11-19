package com.uban.rent.util;

import java.text.SimpleDateFormat;

/**
 * TimeUtils 时间工具类
 * Created by dawabos on 2016/10/27.
 * Email dawabo@163.com
 */
public class TimeUtils {
    public static String formatTime(String time, String formatStr) {
        try {
            Long temeLong = Long.parseLong(time) * 1000;
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.format(temeLong);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }
}