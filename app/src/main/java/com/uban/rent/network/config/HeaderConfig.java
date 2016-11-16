package com.uban.rent.network.config;

import android.text.TextUtils;

import com.uban.rent.App;
import com.uban.rent.util.Constants;
import com.uban.rent.util.SPUtils;


/**
 * Created by Unan on 2016/10/26.
 */

public class HeaderConfig {
    public static boolean isEmptyToken(){
        String ubanToken =  (String) SPUtils.get(App.getInstance(), Constants.TOKEN,"");
        if (TextUtils.isEmpty(ubanToken))
            return true;
        else
            return false;
    }
    public static String token(){
        String ubanToken =  (String) SPUtils.get(App.getInstance(),Constants.TOKEN,"");
        if (TextUtils.isEmpty(ubanToken))
            return "";
        else
            return ubanToken;
    }
    public static String ubanCity(){
        String city = (String)SPUtils.get(App.getInstance(), Constants.SP_CITY_KEY, "");
        if(Constants.CITY_STR[0].equals(city)){
            city = Constants.CITY_ID[0];
        }else if(Constants.CITY_STR[1].equals(city)){
            city = Constants.CITY_ID[1];
        }
        return city;
    }
}
