package com.or.goodlive.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.control.events.UserLoginEvents;
import com.or.goodlive.module.LoginInBean;
import com.or.goodlive.module.request.RequestLogin;
import com.or.goodlive.module.request.RequestRegisterBean;
import com.or.goodlive.module.request.RequestVerificationCode;
import com.or.goodlive.ui.activity.other.AgreementActivity;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2017/2/8.
 * 注册页面
 */

public class RegisterActivity extends BaseActivity {

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
    @Bind(R.id.user_name_str)
    TextView userNameStr;
    @Bind(R.id.password_str)
    TextView passwordStr;
    @Bind(R.id.phone_number_str)
    TextView phoneNumberStr;
    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.verification_code_str)
    TextView verificationCodeStr;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.get_verification_code_btn)
    Button getVerificationCodeBtn;
    @Bind(R.id.agreement_layout)
    LinearLayout agreementLayout;
    @Bind(R.id.agreement_str)
    TextView agreementStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("注册");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_login_login_submit,R.id.get_verification_code_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login_submit:
                String code = etLoginCodeNum.getText().toString().trim();
                String phone = etLoginPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(mContext, "手机号不能为空");
                } else if (phone.length() <= 10) {
                    ToastUtil.makeText(mContext, "请输入正确手机号");
                } else if (TextUtils.isEmpty(code)) {
                    ToastUtil.makeText(this, "验证码不能为空");
                } else if (code.length() <= 5) {
                    ToastUtil.makeText(mContext, "验证码不正确");
                } else {
                    loginApp();
                }
                break;
            case R.id.get_verification_code_btn:
                getVerificationode();
                break;
        }
    }

    private void getVerificationode() {
        String iphone = etPhoneNumber.getText().toString().trim();
        RequestVerificationCode requestVerificationCode = new RequestVerificationCode();
        requestVerificationCode.setPhone(iphone);
        ServiceFactory.getProvideHttpService().sendVerificationCode(requestVerificationCode)
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
                        if (loginInBean.getStatusCode() != Constants.STATUS_CODE_SUCCESS) {
                            ToastUtil.makeText(mContext, loginInBean.getMsg());
                        }
                        return loginInBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<LoginInBean, LoginInBean.ResultsBean>() {
                    @Override
                    public LoginInBean.ResultsBean call(LoginInBean loginInBean) {
                        return loginInBean.getResults();
                    }
                })
                .subscribe(new Action1<LoginInBean.ResultsBean>() {
                    @Override
                    public void call(LoginInBean.ResultsBean resultsBean) {
                        SPUtils.put(mContext, Constants.NICK_NAME, resultsBean.getNickname());
                        SPUtils.put(mContext, Constants.UBAN_TOKEN, resultsBean.getToken());
                        SPUtils.put(mContext, Constants.PHONE, resultsBean.getPhone());
                        SPUtils.put(mContext, Constants.HEAD_IMAGE, resultsBean.getHeadphoto());
                        SPUtils.put(mContext, Constants.USER_MEMBER, resultsBean.getIsVip());
                        UserLoginEvents userLoginEvents = new UserLoginEvents();
                        userLoginEvents.setLoginIn(true);
                        RxBus.getInstance().send(Events.EVENTS_USER_LOGIN, userLoginEvents);//发送events事件登录成功
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

    //注册
    private void loginApp() {
        RequestRegisterBean requestRegister = new RequestRegisterBean();
        requestRegister.setPhone("15801649867");
        requestRegister.setVerify_code("1122");
        requestRegister.setName("liyan");
        requestRegister.setPassword("123456");
        ServiceFactory.getProvideHttpService().register(requestRegister)
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
                        if (loginInBean.getStatusCode() != Constants.STATUS_CODE_SUCCESS) {
                            ToastUtil.makeText(mContext, loginInBean.getMsg());
                        }
                        return loginInBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<LoginInBean, LoginInBean.ResultsBean>() {
                    @Override
                    public LoginInBean.ResultsBean call(LoginInBean loginInBean) {
                        return loginInBean.getResults();
                    }
                })
                .subscribe(new Action1<LoginInBean.ResultsBean>() {
                    @Override
                    public void call(LoginInBean.ResultsBean resultsBean) {
                        SPUtils.put(mContext, Constants.NICK_NAME, resultsBean.getNickname());
                        SPUtils.put(mContext, Constants.UBAN_TOKEN, resultsBean.getToken());
                        SPUtils.put(mContext, Constants.PHONE, resultsBean.getPhone());
                        SPUtils.put(mContext, Constants.HEAD_IMAGE, resultsBean.getHeadphoto());
                        SPUtils.put(mContext, Constants.USER_MEMBER, resultsBean.getIsVip());
                        UserLoginEvents userLoginEvents = new UserLoginEvents();
                        userLoginEvents.setLoginIn(true);
                        RxBus.getInstance().send(Events.EVENTS_USER_LOGIN, userLoginEvents);//发送events事件登录成功
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
}
