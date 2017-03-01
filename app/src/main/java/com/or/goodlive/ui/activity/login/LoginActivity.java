package com.or.goodlive.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.OldBaseActivity;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.LoginInBean;
import com.or.goodlive.module.SearchKeyWord;
import com.or.goodlive.module.WeChatUserInfoBean;
import com.or.goodlive.module.request.RequestLogin;
import com.or.goodlive.module.request.RequestSearchKeyWord;
import com.or.goodlive.ui.activity.MainActivity;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.SPUtils;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


public class LoginActivity extends OldBaseActivity {

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_login_phone_num)
    EditText etLoginPhoneNum;
    @Bind(R.id.et_login_code_num)
    EditText etLoginCodeNum;
    @Bind(R.id.btn_login_login_submit)
    Button btnLoginLoginSubmit;
    @Bind(R.id.activity_login)
    LinearLayout activityLogin;
    @Bind(R.id.password_find_layout)
    RelativeLayout passwordFindLayout;
    @Bind(R.id.wechat_layout)
    LinearLayout wechatLayout;
    @Bind(R.id.qq_layout)
    LinearLayout qqLayout;
    @Bind(R.id.weibo_layout)
    LinearLayout weiboLayout;
    private String code;
    private String phone;
    private UMShareAPI mShareAPI;
    private Tencent mTencent;
    private String nickName,avatar_url;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mShareAPI = UMShareAPI.get(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("登录");

        mTencent = Tencent.createInstance("1105997898", LoginActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
//            case android.R.id.home:
//                finish();
//                break;
            case R.id.register_text:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_login_login_submit, R.id.password_find_layout,R.id.wechat_layout, R.id.qq_layout, R.id.weibo_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login_submit:
                code = etLoginCodeNum.getText().toString().trim();
                phone = etLoginPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(mContext, "手机号不能为空");
                } else if (TextUtils.isEmpty(code)) {
                    ToastUtil.makeText(this, "验证码不能为空");
                } else {
                    loginApp();
                }
                break;
            case R.id.password_find_layout:
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));
                break;
            case R.id.wechat_layout:
                //微信登陆
                if (mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)){
                    AuthLogin(SHARE_MEDIA.WEIXIN);
                }else {
                    showToast("请安装微信客户端！");
                }
                break;
            case R.id.qq_layout:
                if (mShareAPI.isInstall(this, SHARE_MEDIA.QQ)){
                    AuthLogin(SHARE_MEDIA.QQ);
                }else {
                    showToast("请安装qq客户端！");
                }
                break;
            case R.id.weibo_layout:
                AuthLogin(SHARE_MEDIA.SINA);
                break;
        }
    }



    private void AuthLogin( SHARE_MEDIA platform) {
        mShareAPI.doOauthVerify(this, platform, umAuthListener);
    }

    private String openid;
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            StringBuilder sb = new StringBuilder();
            Set<String> keys = data.keySet();
            for (String key : keys) {
                sb.append(key + "=" + data.get(key).toString() + "\r\n");
            }
            String access_token = data.get("access_token");
            openid = data.get("openid");
            String expires = data.get("expires_in");
            mTencent.setOpenId(openid);
            mTencent.setAccessToken(access_token, expires);
            if(platform==SHARE_MEDIA.WEIXIN){
                getWeChatInfo(access_token,openid);
            }else if(platform==SHARE_MEDIA.QQ){
                getUserInfo();
            }else{
                getWeiboInfo(SHARE_MEDIA.SINA);
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("授权失败!");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("取消授权!");
        }
    };

    public void getWeiboInfo(SHARE_MEDIA platform)

    {

        mShareAPI.getPlatformInfo(this, platform, new UMAuthListener() {

            @Override
            public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {

            }

            @Override

            public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {

                //转换为set

                Set<String> keySet = arg2.keySet();

                //遍历循环，得到里面的key值----用户名，头像....

                for (String string : keySet) {

                }

                //得到key值得话，可以直接的到value

                String name = arg2.get("screen_name");

                String url = arg2.get("profile_image_url");
            }

            @Override

            public void onCancel(SHARE_MEDIA arg0, int arg1) {

            }

        });

    }


    /**
     * 获取用户信息
     *
     * @param platform
     */
    private UserInfo userInfo;
    private IUiListener userInfoListener;
    private void getUserInfo() {
        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    if(ret == 100030){
                        //权限不够，需要增量授权
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(LoginActivity.this, "all", new IUiListener() {

                                    @Override
                                    public void onError(UiError arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onComplete(Object arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub

                                    }
                                });
                            }
                        };

                        LoginActivity.this.runOnUiThread(r);
                    }else{
                        nickName = jo.getString("nickname");
                        avatar_url = jo.getString("figureurl_qq_2");
                        qqPlatformLogin();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }


            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };
        userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);
    }

    private void qqPlatformLogin() {
        Constants.isNoCookie = true;
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setThird_platform_type("qq");
        requestLogin.setAvatar_url(avatar_url);
        requestLogin.setNick(nickName);
        requestLogin.setOpenid(openid);
        ServiceFactory.getProvideHttpService().thirdPlatformLogin(requestLogin)
                .compose(this.<LoginInBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<LoginInBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<LoginInBean, Boolean>() {
                    @Override
                    public Boolean call(LoginInBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<LoginInBean, LoginInBean.RstBean>() {
                    @Override
                    public LoginInBean.RstBean call(LoginInBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<LoginInBean.RstBean>() {
                    @Override
                    public void call(LoginInBean.RstBean resultsBean) {
                        SPUtils.put(mContext, Constants.PHPSESSID, resultsBean.getPHPSESSID());
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "登录失败");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }


    /**
     * 微信授权
     * @param access_token
     * @param openid
     */
    private void getWeChatInfo(String access_token,String openid){
        ServiceFactory.getProvideHttpService().getWeChatInfo(access_token,openid)
                .compose(RxSchedulersHelper.<WeChatUserInfoBean>io_main())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //showLoadingView();
                    }
                })
                .subscribe(new Action1<WeChatUserInfoBean>() {
                    @Override
                    public void call(WeChatUserInfoBean weChatUserInfoBean) {

                        weChatLogin(weChatUserInfoBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }
    //微信登录
    private void weChatLogin(WeChatUserInfoBean weChatUserInfoBean) {
        Constants.isNoCookie = true;
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setThird_platform_type("weixin");
        requestLogin.setAvatar_url(weChatUserInfoBean.getHeadimgurl());
        requestLogin.setNick(weChatUserInfoBean.getNickname());
        requestLogin.setOpenid(weChatUserInfoBean.getOpenid());
        ServiceFactory.getProvideHttpService().thirdPlatformLogin(requestLogin)
                .compose(this.<LoginInBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<LoginInBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<LoginInBean, Boolean>() {
                    @Override
                    public Boolean call(LoginInBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<LoginInBean, LoginInBean.RstBean>() {
                    @Override
                    public LoginInBean.RstBean call(LoginInBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<LoginInBean.RstBean>() {
                    @Override
                    public void call(LoginInBean.RstBean resultsBean) {
                        SPUtils.put(mContext, Constants.PHPSESSID, resultsBean.getPHPSESSID());
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "登录失败");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.right_textview, menu);
        return true;
    }
    public void showToast(final String toastStr){
        ToastUtil.makeText(LoginActivity.this, toastStr);
    }
    //登录
    private void loginApp() {
        Constants.isNoCookie = true;
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setPhone(phone);
        requestLogin.setPassword(code);
        ServiceFactory.getProvideHttpService().getLogin(requestLogin)
                .compose(this.<LoginInBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<LoginInBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<LoginInBean, Boolean>() {
                    @Override
                    public Boolean call(LoginInBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<LoginInBean, LoginInBean.RstBean>() {
                    @Override
                    public LoginInBean.RstBean call(LoginInBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<LoginInBean.RstBean>() {
                    @Override
                    public void call(LoginInBean.RstBean resultsBean) {
                        SPUtils.put(mContext, Constants.PHPSESSID, resultsBean.getUser().getPHPSESSID());
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "登录失败");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
