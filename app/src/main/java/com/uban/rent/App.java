package com.uban.rent;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.uban.rent.util.ImageLoadUtils;


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
    }
    public static Application getInstance() {
        return _instance;
    }
}
