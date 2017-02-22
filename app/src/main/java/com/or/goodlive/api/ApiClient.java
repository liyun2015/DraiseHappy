package com.or.goodlive.api;


import com.or.goodlive.module.BaseResultsBean;
import com.or.goodlive.module.CategoryDataBean;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.module.LoginInBean;
import com.or.goodlive.module.request.RegisterBean;
import com.or.goodlive.module.request.RequestLogin;
import com.or.goodlive.module.request.RequestRegisterBean;
import com.or.goodlive.module.request.RequestVerificationCode;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * ApiClient
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public interface ApiClient {
    //发送验证码
    @POST("/user/user/register")
    Observable<RegisterBean> sendVerificationCode(@Body RequestVerificationCode requestVerificationCode);
    //注册
    @POST("/user/user/confirmRegister")
    Observable<RegisterBean> register(@Body RequestRegisterBean requestRegister);
    //登录
    @POST("/user/user/login")
    Observable<LoginInBean> getLogin(@Body RequestLogin requestLogin);
    // 修改密码
    @POST("/user/user/updatePassword")
    Observable<BaseResultsBean> updatePassword(@Body RequestLogin requestLogin);
    //忘记密码三部曲
    //发送验证码
    @POST("/user/user/forgetPassword")
    Observable<RegisterBean> forgetPassword(@Body RequestVerificationCode requestVerificationCode);
    //验证验证码
    @POST("/user/user/confirmForgetPasswordCode")
    Observable<BaseResultsBean> confirmForgetPasswordCode(@Body RequestRegisterBean requestVerificationCode);
    //重置密码
    @POST("/user/user/resetPassword")
    Observable<RegisterBean> resetPassword(@Body RequestLogin requestLogin);
    // 封面
    @POST("/user/cover/list")
    Observable<CoverDataBean> getCoverList(@QueryMap Map<String,Integer> map);
    // 同行
    @POST("/user/news/list")
    Observable<CoverDataBean> getNewsList(@QueryMap Map<String,Integer> map);
    // 同行类别
    @POST("/user/news/categoryList")
    Observable<CategoryDataBean> getNewsTitList(@QueryMap Map<String,Integer> map);

}
