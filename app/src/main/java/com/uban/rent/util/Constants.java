package com.uban.rent.util;

/**
 * Constants
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public class Constants {
    public static final int SUCCESS_STATUS  = 1;
    public static final int ERROR_STATUS  = 0;
    public static final int INVALID_STATUS  = -100;
    public static final String TOKEN = "ubanToken";
    public static final String CURRENT_CITY_ID = "current_city_id";
    public static final String FIRST_LAUNCHER = "firstLauncher";
    public static final String SP_CITY_KEY = "cityKey";
    public static final String MAX_RESULTS = "10";
    public static final String PHONE_NUM = "400-810-8896";
    public static final String APP_PLATFORM  = "android_open";
    // 标记（值多个，以逗号隔开）： 1客户有需求 2客户急需用房 3我没有匹配房源 4客户已经租了 5客户是假的
    public static final String[] CUSTOMER_TAGS_STR= new String[] { "","客户有需求", "客户急需用房","我没有匹配房源","客户已经租了","客户是假的"};
    public static final String[] CITY_STR= new String[] { "北京", "上海"};
    public static final String[] CITY_ID= new String[] { "12", "13"};
}
