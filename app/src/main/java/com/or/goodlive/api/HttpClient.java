package com.or.goodlive.api;


import com.or.goodlive.App;
import com.or.goodlive.api.config.RequestInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HttpClient
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */
public class HttpClient {

    public static final String BASE_URL = "http://weixin.xiaozhubanjia.com";
    private static HttpClient mInstance;
    private Retrofit singleton;

    public static HttpClient getIns() {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) mInstance = new HttpClient(BASE_URL);
            }
        }
        return mInstance;
    }

    public HttpClient(String BASE_URL) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(App.getInstance().getCacheDir(), "orCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(new RequestInterceptor())
                .cache(cache)
                .build();

        singleton = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public <T> T createService(Class<T> clz) {
        return singleton.create(clz);
    }

}
