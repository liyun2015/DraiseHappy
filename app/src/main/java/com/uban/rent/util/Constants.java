package com.uban.rent.util;

/**
 * Constants
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public class Constants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx560d10dd4001d136";
    //支付回调地址
    public static final String NOTIFY_URL = "http://idfa.dev.uban.com/ubanpay/notify.html";
    public static final String UBAN_TOKEN = "ubanToken";//session
    public static final String TOKEN = "token";//time
    public static final String UBAN_CITY = "ubanCity";
    public static final String NICK_NAME = "nickName";
    public static final String USER_MEMBER = "userMember";
    public static final String PHONE = "Phone";
    public static final String HEAD_IMAGE = "headImage";
    public static final String RESERVED = "reserved";
    public static final String RESERVED_PHONE = "android";
    public static final String[] CITY_ID= new String[] { "12", "13"};
    public static final String[] WORK_DESK_TYPE_NAME= new String[] {"独立空间" , "开放空间", "hot desk" , "独立工位" ,"开放工位" ,"会议室" ,"活动场地"};
    public static final int RENT_HOUSE = 1;
    public static final int RENT_DAY = 2;
    public static final int RENT_MONTH = 3;
    public static final int STATUS_CODE_SUCCESS = 0;
    public static final int SHORT_NEAR_FLAG = 1;
    public static final String PHONE_NUMBER = "400-810-6698";
    public static final String APP_IMG_URL_640_420 = "http://img1.static.uban.com/cybg/%s-w640x420";
    public static final String APP_IMG_URL_240_180 = "http://img1.static.uban.com/cybg/%s-n240x180";
    public static final String APP_IMG_PANORAMA_URL = "http://img1.static.uban.com/cybg/%s-w2048x1024N";
    public static final String APP_IMG_URL_EQUIPMENT_SERVICE = "http://img2.static.uban.com/spacelogo/";
    public static final int HOT_DESK_TYPE = 3;

    public static final int MEMBER_STATUS_NOT = 0;//未申请
    public static final int MEMBER_STATUS_APPLYING = 1;//申请中
    public static final int MEMBER_STATUS_SUCCESS = 2;//是会员


}
