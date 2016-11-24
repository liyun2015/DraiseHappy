package com.uban.rent.ui.activity.components;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.BaseResultsBean;
import com.uban.rent.module.LoginInBean;
import com.uban.rent.module.VerifyMemberBean;
import com.uban.rent.module.request.RequestLogin;
import com.uban.rent.module.request.RequestSendValid;
import com.uban.rent.module.request.RequestVerifyMember;
import com.uban.rent.ui.activity.MainActivity;
import com.uban.rent.ui.activity.other.AgreementActivity;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.SPUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_login_phone_num)
    EditText etLoginPhoneNum;
    @Bind(R.id.tv_login_code_zx)
    TextView tvLoginCodeZx;
    @Bind(R.id.et_login_code_num)
    EditText etLoginCodeNum;
    @Bind(R.id.btn_login_login_submit)
    Button btnLoginLoginSubmit;
    @Bind(R.id.activity_login)
    LinearLayout activityLogin;
    @Bind(R.id.read_agreement)
    TextView readAgreement;
    private String code;
    private String phone;
    private boolean sendCode = true;
    public static final int DELAY_SECONDS = 60000;
    private TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        SPUtils.put(mContext, Constants.USER_MEMBER, 0);// 0未成为会员, 1申请中 2 已申请会员
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
        toolbarContentText.setText("登陆／注册");

        RxTextView.textChanges(etLoginPhoneNum)
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        if (charSequence.length() >= 11) {
                            setDrawableLeftView(etLoginPhoneNum, R.drawable.ic_login_phone_checked);
                        } else {
                            setDrawableLeftView(etLoginPhoneNum, R.drawable.ic_login_phone_normal);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setDrawableLeftView(etLoginPhoneNum, R.drawable.ic_login_phone_normal);
                    }
                });
        RxTextView.textChanges(etLoginCodeNum)
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        if (charSequence.length() >= 6) {
                            setDrawableLeftView(etLoginCodeNum, R.drawable.ic_login_password_checked);
                        } else {
                            setDrawableLeftView(etLoginCodeNum, R.drawable.ic_login_password_normal);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setDrawableLeftView(etLoginCodeNum, R.drawable.ic_login_password_normal);
                    }
                });
    }

    private void setDrawableLeftView(EditText editText, int resID) {
        Drawable drawable = getResources()
                .getDrawable(resID);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        editText.setCompoundDrawables(drawable, null, null, null);
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

    @OnClick({R.id.tv_login_code_zx, R.id.btn_login_login_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_code_zx:
                phone = etLoginPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(this, "手机号不能为空");
                } else {
                    if (sendCode) {
                        time = new TimeCount(DELAY_SECONDS, 1000);
                        time.start();// 开始计时
                        sendCode();
                    }
                }
                break;
            case R.id.btn_login_login_submit:
                code = etLoginCodeNum.getText().toString().trim();
                phone = etLoginPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(this, "手机号不能为空");
                } else if (TextUtils.isEmpty(code)) {
                    ToastUtil.makeText(this, "验证码不能为空");
                } else {
                    memberStatus();
                    loginApp();
                }
                break;
        }
    }


    //登录
    private void loginApp() {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setPhone(phone);
        requestLogin.setCode(code);
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

    private void memberStatus() {

        RequestVerifyMember requestVerifyMember = new RequestVerifyMember();
        requestVerifyMember.setType(1);
        ServiceFactory.getProvideHttpService().getVerifyMember(requestVerifyMember)
                .compose(this.<VerifyMemberBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<VerifyMemberBean>io_main())
                .filter(new Func1<VerifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(VerifyMemberBean verifyMemberBean) {
                        return verifyMemberBean!=null;
                    }
                })
                .filter(new Func1<VerifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(VerifyMemberBean verifyMemberBean) {
                        return verifyMemberBean!=null;
                    }
                })
                .filter(new Func1<VerifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(VerifyMemberBean verifyMemberBean) {
                        return verifyMemberBean.getResults().size()>0;
                    }
                })
                .subscribe(new Action1<VerifyMemberBean>() {
                    @Override
                    public void call(VerifyMemberBean verifyMemberBean) {
                        VerifyMemberBean.ResultsBean resultsBean = verifyMemberBean.getResults().get(0);
                        SPUtils.put(mContext, Constants.USER_MEMBER, resultsBean.getStatus());// 0未成为会员, 1申请中 2 已申请会员
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        SPUtils.put(mContext, Constants.USER_MEMBER, 0);// 0未成为会员, 1申请中 2 已申请会员
                    }
                });
    }

    //发送验证码
    private void sendCode() {
        RequestSendValid requestSendValid = new RequestSendValid();
        requestSendValid.setPhone(phone);
        ServiceFactory.getProvideHttpService().getSendValidSms(requestSendValid)
                .compose(this.<BaseResultsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<BaseResultsBean>io_main())
                .subscribe(new Action1<BaseResultsBean>() {
                    @Override
                    public void call(BaseResultsBean baseResultsBean) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.read_agreement)
    public void onClick() {
        startActivity(new Intent(mContext, AgreementActivity.class));
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            sendCode = true;
            if (!LoginActivity.this.isFinishing()) {
                tvLoginCodeZx.setText("获取验证码");
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            sendCode = false;
            if (!LoginActivity.this.isFinishing()) {
                tvLoginCodeZx.setText("重发" + millisUntilFinished / 1000 + "s");//重发60s
            }
        }
    }
}
