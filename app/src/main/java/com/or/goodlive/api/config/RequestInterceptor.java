package com.or.goodlive.api.config;


import com.or.goodlive.util.Constants;
import com.or.goodlive.util.NetUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * GzipRequestInterceptor
 * Created by dawabos on 2016/6/15.
 * Email dawabo@163.com
 */
public class RequestInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "public, only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "public, max-age=0";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest);
        }
        if (NetUtil.isNetworkConnected()) {
            Request compressedRequest = originalRequest.newBuilder()
                    .header("Cache-Control", CACHE_CONTROL_NETWORK)
                    .build();
            Response response = chain.proceed(compressedRequest);
            return response;
        }else{
            Response originalResponse = chain.proceed(originalRequest);
            return originalResponse.newBuilder()
                    .header("Cache-Control", CACHE_CONTROL_CACHE)
                    .removeHeader("Pragma").build();
        }

    }
}
