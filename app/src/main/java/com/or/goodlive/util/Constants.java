package com.or.goodlive.util;

/**
 * Constants
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public class Constants {
    public static final String APP_ID = "wx560d10dd4001d136";//微信支付 APP_ID
    public static final String NOTIFY_URL = "http://idfa.third.or.com/ubanpay/notify.html";//支付回调地址
    public static final String UBAN_MEMBER_NOTIFY_URL = "http://idfa.dev.or.com/ubanpay/memPaynotify.html";//会员支付回调地址

    public static final String UBAN_TOKEN = "ubanToken";//session
    public static final String PHPSESSID = "PHPSESSID";
    public static final String UBAN_CITY = "ubanCity";

    public static final String NICK_NAME = "nickName";
    public static final String USER_MEMBER = "userMember";
    public static final String PHONE = "Phone";
    public static final String HEAD_IMAGE = "headImage";
    public static final String RESERVED = "reserved";
    public static final String RESERVED_PHONE = "android";
    public static final String LOCATION_LATITUDE = "locationLatitude";
    public static final String LOCATION_LONGITUDE = "locationLongitude";
    public static final String[] CITY_ID= new String[] { "12", "13"};

    public static final int RENT_HOUSE = 1;
    public static final int RENT_DAY = 2;
    public static final int RENT_MONTH = 3;

    public static final int SHORT_NEAR_FLAG = 1;//距离最近标识

    public static final int STATUS_CODE_SUCCESS = 0;
    public static final String RESULT_CODE_SUCCESS = "0";
    public static final int STATUS_CODE_ERROR = 1;
    public static final String PHONE_NUMBER = "010-57496664";

    public static final String APP_IMG_URL_640_420 = "http://img1.static.or.com/cybg/%s-w640x420";
    public static final String APP_IMG_URL_240_180 = "http://img1.static.or.com/cybg/%s-n240x180";
    public static final String APP_IMG_PANORAMA_URL = "http://img1.static.or.com/cybg/%s-w2048x1024N";
    public static final String APP_IMG_URL_EQUIPMENT_SERVICE = "http://img2.static.or.com/spacelogo/";
    public static final String MEMBER_EQUIPMENT_QRCODE = "http://m.or.com/bj/vipRecord/%s.html";

    public static final String[] WORK_DESK_TYPE_NAME= new String[] {"","独立空间" , "开放空间", "hot desk" , "独立工位" ,"开放工位" ,"会议室" ,"活动场地"};
    public static final int HOT_DESK_TYPE = 3;//hot desk
    public static final int CONFERENCE_ROOM_TYPE = 6;//会议室
    public static final int ACTIVE_SITE_TYPE = 7;//活动场地

    public static final int MEMBER_STATUS_APPLYING = 0;//0 是会员
    public static final int MEMBER_STATUS_NOT = 1;//1 不是会员

    public static final int APP_TYPE = 0;//0 Android 1 iOS

    public static final String APP_DOWNLOAD_URL_PAGE = "http://m.or.com/bj/toDowloadPage/";//app分享推荐好友下载页
    public static final String APP_MEMBER_URL_PAGE = "http://m.or.com/bj/dzVip";//分享会员页面
}
