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
}