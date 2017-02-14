package com.or.goodlive.api;


import com.or.goodlive.module.LoginInBean;
import com.or.goodlive.module.request.RequestLogin;
import com.or.goodlive.module.request.RequestRegisterBean;
import com.or.goodlive.module.request.RequestVerificationCode;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * ApiClient
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public interface ApiClient {
    //发送验证码
    @POST("/user/user/register")
    Observable<LoginInBean> sendVerificationCode(@Body RequestVerificationCode requestVerificationCode);
    //注册
    @POST("/user/user/confirmRegister")
    Observable<LoginInBean> register(@Body RequestRegisterBean requestRegister);
    //登录
    @POST("/user/user/confirmRegister")
    Observable<LoginInBean> getLogin(@Body RequestLogin requestLogin);

}
