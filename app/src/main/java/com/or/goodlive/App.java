package com.or.goodlive;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.or.goodlive.util.ImageLoadUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


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
        Config.DEBUG = false;//http://dev.umeng.com/social/android/quick-integration
    }

    //分享各平台初始化配置
    {
        PlatformConfig.setWeixin("wx560d10dd4001d136", "4b5e1cb5ff7aaa0a8df4b6523fa65a5e");
        PlatformConfig.setSinaWeibo("1658710715", "ac8d11b4dfae35e5a354afc5e27e9c55");
        PlatformConfig.setQQZone("1105851078", "PkfjgLEw8sIAMAzx");
    }

    public static Application getInstance() {
        return _instance;
    }
}
