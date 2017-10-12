package com.or.draise_happy.api;


import com.or.draise_happy.module.BaseResultsBean;
import com.or.draise_happy.module.CategoryDataBean;
import com.or.draise_happy.module.CommentBean;
import com.or.draise_happy.module.CoverDataBean;
import com.or.draise_happy.module.DetailsBean;
import com.or.draise_happy.module.LoginInBean;
import com.or.draise_happy.module.MessResultsBean;
import com.or.draise_happy.module.SearchKeyWord;
import com.or.draise_happy.module.UserInfoBean;
import com.or.draise_happy.module.WeChatUserInfoBean;
import com.or.draise_happy.module.request.RegisterBean;
import com.or.draise_happy.module.request.RequestLogin;
import com.or.draise_happy.module.request.RequestRegisterBean;
import com.or.draise_happy.module.request.RequestSearchKeyWord;
import com.or.draise_happy.module.request.RequestVerificationCode;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
    // 修改昵称
    @POST("/user/user/update")
    Observable<LoginInBean> updateName(@Body RequestLogin requestLogin);
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
    // 一鸣
    @POST("/user/yiming/list")
    Observable<CoverDataBean> getYimingList(@QueryMap Map<String,Integer> map);
    // 一鸣类别
    @POST("/user/yiming/categoryList")
    Observable<CategoryDataBean> getYimingTitList(@QueryMap Map<String,Integer> map);
    // 现场
    @POST("/user/scene/list")
    Observable<CoverDataBean> getSceneList(@QueryMap Map<String,Integer> map);
    // 现场类别
    @POST("/user/scene/categoryList")
    Observable<CategoryDataBean> getSceneTitList(@QueryMap Map<String,Integer> map);
    // 同行
    @POST("/user/news/list")
    Observable<CoverDataBean> getNewsList(@QueryMap Map<String,Integer> map);
    // 同行类别
    @POST("/user/news/categoryList")
    Observable<CategoryDataBean> getNewsTitList(@QueryMap Map<String,Integer> map);
    // 上传头像
    @POST("/user/user/uploadHead")
    Observable<BaseResultsBean> uploadHead(@Body RequestBody body);
    // 消息列表
    @POST("/user/user/message")
    Observable<MessResultsBean> messageList(@QueryMap Map<String,String> map);
    // 检查更新
    @POST("/user/user/isCheckUpdate")
    Observable<BaseResultsBean> heckUpdate(@QueryMap Map<String,String> map);
    // 个人中心
    @POST("/user/user/personalCenter")
    Observable<UserInfoBean> personalCenter(@QueryMap Map<String,String> map);
    // 搜索
    @POST("/user/news/search")
    Observable<SearchKeyWord> getSearchKeyWords(@Body RequestSearchKeyWord requestSearchKeyWord);
    // 评论列表
    @POST("/user/NewsComment/list")
    Observable<CommentBean> getCommentList(@QueryMap Map<String,String> map);
    // 评论
    @POST("/user/NewsComment/add")
    Observable<BaseResultsBean> addComment(@Body RequestSearchKeyWord requestSearchKeyWord);
    // 点赞
    @POST("/user/NewsComment/operate")
    Observable<BaseResultsBean> addfavor(@Body RequestSearchKeyWord requestSearchKeyWord);
    //获取微信
    @GET("https://api.weixin.qq.com/sns/userinfo")
    Observable<WeChatUserInfoBean> getWeChatInfo(@Query("access_token") String access_token, @Query("openid") String openid);
    // 三方登录
    @POST("/user/user/thirdPlatformLogin")
    Observable<LoginInBean> thirdPlatformLogin(@Body RequestLogin requestLogin);
    //封面详情页
    @POST("/user/cover/info")
    Observable<DetailsBean> getCoverDetail(@QueryMap Map<String,String> map);
    //一鸣详情页
    @POST("/user/yiming/info")
    Observable<DetailsBean> getYimingDetail(@QueryMap Map<String,String> map);
    //现场详情页
    @POST("/user/video/info")
    Observable<DetailsBean> getVideoDetail(@QueryMap Map<String,String> map);
    //同行详情页
    @POST("/user/news/info")
    Observable<DetailsBean> getNewsDetail(@QueryMap Map<String,String> map);
}
