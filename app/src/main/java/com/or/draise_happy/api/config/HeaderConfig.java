package com.or.draise_happy.api.config;

import android.text.TextUtils;

import com.or.draise_happy.App;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.SPUtils;
import com.or.draise_happy.util.encry.Des3;


/**
 * HeaderConfig
 * Created by dawabos on 2016/11/17.
 * Email dawabo@163.com
 */
public class HeaderConfig {
    public static boolean isEmptyUbanToken(){
        String ubanToken =  (String) SPUtils.get(App.getInstance(), Constants.UBAN_TOKEN,"");
        if (TextUtils.isEmpty(ubanToken))
            return true;
        else
            return false;
    }

    /**
     * 加密用户身份信息
     * @return
     */
    public static String phpsessId(){
        String phpsessId =  (String) SPUtils.get(App.getInstance(),Constants.PHPSESSID,"");
        if (TextUtils.isEmpty(phpsessId))
            return "";
        else
            return phpsessId;
    }

    /**
     * 用户登录手机号
     * @return
     */
    public static String phoneNum(){
        String phoneNum =  (String) SPUtils.get(App.getInstance(),Constants.PHONE,"");
        if (TextUtils.isEmpty(phoneNum))
            return "";
        else
            return phoneNum;
    }

    /**
     * 用户登录手机号
     * @return
     */
    public static String userHeadImage(){
        String headImage =  (String) SPUtils.get(App.getInstance(),Constants.HEAD_IMAGE,"");
        if (TextUtils.isEmpty(headImage))
            return "";
        else
           return headImage;
    }


    /**
     * 会员状态
     * @return
     */
    public static boolean isMemberStatus(){
        int memberStatus = (int) SPUtils.get(App.getInstance(),Constants.USER_MEMBER,Constants.MEMBER_STATUS_NOT);
        if (memberStatus==Constants.MEMBER_STATUS_NOT){
            return false;
        }else if (memberStatus==Constants.MEMBER_STATUS_APPLYING){
            return true;
        }
        return false;
    }

    /**
     * 昵称
     * @return
     */
    public static String nickName(){
        String nickName =  (String) SPUtils.get(App.getInstance(),Constants.NICK_NAME,"");
        if (TextUtils.isEmpty(nickName))
            return "";
        else
            return nickName;
    }


    /**
     * 加密时间戳
     * @return
     */
    public static String token(){
        String token = "";
        try {
            token = Des3.encode(Long.toString(System.currentTimeMillis()/1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 城市
     * @return
     */
    public static String ubanCity(){
        return (String)SPUtils.get(App.getInstance(), Constants.UBAN_CITY, "");
    }

    public static String cityShorthand(){
        if (ubanCity().equals(Constants.CITY_ID[0])){
            return "bj";
        }else if (ubanCity().equals(Constants.CITY_ID[1])){
            return "sh";
        }else {
            return "";
        }
    }
}
