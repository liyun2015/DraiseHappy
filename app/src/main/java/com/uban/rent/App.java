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
        PlatformConfig.setWeixin("wx8d1f24c0f7a7b6d6", "008efe6a80cab56ea8c55cb567d1b2c4");
        PlatformConfig.setSinaWeibo("1118025800", "eb17db06e744744b8ff3317989b3a13d");
        PlatformConfig.setQQZone("1104758527", "DZPVppJkhYORriIB");
    }

    public static Application getInstance() {
        return _instance;
    }
}
