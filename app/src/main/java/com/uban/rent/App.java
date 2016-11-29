package com.uban.rent;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.uban.rent.util.ImageLoadUtils;
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
        _instance = this;//
        SDKInitializer.initialize(this);
        ImageLoadUtils.initImageLoader(getApplicationContext());
        UMShareAPI.get(this);
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
    }

    //分享各平台初始化配置
    {
        PlatformConfig.setWeixin("wx560d10dd4001d136", "28edcb9a7b8090ea4b2e11b234b33fcc");
        PlatformConfig.setSinaWeibo("1658710715", "ac8d11b4dfae35e5a354afc5e27e9c55");
        PlatformConfig.setQQZone("1105851078", "PkfjgLEw8sIAMAzx");
    }

    public static Application getInstance() {
        return _instance;
    }
}
