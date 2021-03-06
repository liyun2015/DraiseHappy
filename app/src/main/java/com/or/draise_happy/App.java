package com.or.draise_happy;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.or.draise_happy.util.ImageLoadUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;


/**
 * App
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public class App extends Application {
    private static Application _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        SDKInitializer.initialize(this);
        ImageLoadUtils.initImageLoader(getApplicationContext());
        UMShareAPI.get(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this); // 初始化 JPush
        Config.DEBUG = true;//http://dev.umeng.com/social/android/quick-integration
    }

    //分享各平台初始化配置
    {
        PlatformConfig.setWeixin("wx4975d31bb0ce551f", "c8d4dd5574ef8e0d0b8b6bcc288e0bd7");
        PlatformConfig.setSinaWeibo("1264350362", "d92ce134e0bd051b199da42ef3fd94bc");
        PlatformConfig.setQQZone("1106010252", "DntSWOZGT9Vb20jk");
    }

    public static Application getInstance() {
        return _instance;
    }
}
