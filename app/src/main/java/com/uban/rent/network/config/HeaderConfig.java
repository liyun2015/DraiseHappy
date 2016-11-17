package com.uban.rent.network.config;

import android.text.TextUtils;

import com.uban.rent.App;
import com.uban.rent.util.Constants;
import com.uban.rent.util.SPUtils;
import com.uban.rent.util.encry.Des3;


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
    public static String ubanToken(){
        String ubanToken =  (String) SPUtils.get(App.getInstance(),Constants.UBAN_TOKEN,"");
        if (TextUtils.isEmpty(ubanToken))
            return "";
        else
            return ubanToken;
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
        String city = (String)SPUtils.get(App.getInstance(), Constants.UBAN_CITY, "");
        if(Constants.CITY_STR[0].equals(city)){
            city = Constants.CITY_ID[0];
        }else if(Constants.CITY_STR[1].equals(city)){
            city = Constants.CITY_ID[1];
        }
        return city;
    }
}
