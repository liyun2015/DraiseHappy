package com.or.goodlive.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
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
import com.or.goodlive.module.request.RegisterBean;
import com.or.goodlive.module.request.RequestRegisterBean;
import com.or.goodlive.module.request.RequestVerificationCode;
import com.or.goodlive.ui.activity.MainActivity;
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

import static com.or.goodlive.R.id.agreement_str;


/**
 * Created by Administrator on 2017/2/8.
 * 注册页面
 */

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_pass_word)
    EditText etPassWord;
    @Bind(R.id.et_pone_number)
    EditText etPoneNumber;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.agreement_layout)
    LinearLayout agreementLayout;
    @Bind(agreement_str)
    TextView agreementStr;
    @Bind(R.id.btn_login_login_submit)
    Button btnLoginLoginSubmit;
    @Bind(R.id.activity_login)
    LinearLayout activityLogin;
    @Bind(R.id.get_verification_code_btn)
    TextView getVerificationCodeBtn;

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
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("注册");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.right_textview_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.login_text:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_login_login_submit, R.id.get_verification_code_btn,R.id.agreement_str})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login_submit:
                String phone = etPoneNumber.getText().toString().trim();
                String setVerifyCode = etVerificationCode.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String passWord = etPassWord.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(mContext, "手机号不能为空");
                } else if (phone.length() <= 10) {
                    ToastUtil.makeText(mContext, "请输入正确手机号");
                } else if (TextUtils.isEmpty(setVerifyCode)) {
                    ToastUtil.makeText(this, "验证码不能为空");
                } else if (TextUtils.isEmpty(userName)) {
                    ToastUtil.makeText(mContext, "用户名不能为空");
                } else if (TextUtils.isEmpty(passWord)) {
                    ToastUtil.makeText(mContext, "密码不能为空");
                }else {
                    registerApp();
                }
                break;
            case R.id.get_verification_code_btn:
                getVerificationode();
                break;
            case R.id.agreement_str:
                startActivity(new Intent(mContext, AgreementActivity.class));
                break;
        }
    }

    private void getVerificationode() {
        String iphone = etPoneNumber.getText().toString().trim();
        RequestVerificationCode requestVerificationCode = new RequestVerificationCode();
        requestVerificationCode.setPhone(iphone);
        ServiceFactory.getProvideHttpService().sendVerificationCode(requestVerificationCode)
                .compose(this.<RegisterBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<RegisterBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<RegisterBean, Boolean>() {
                    @Override
                    public Boolean call(RegisterBean registerBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(registerBean.getErrno())) {
                            ToastUtil.makeText(mContext, registerBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(registerBean.getErrno());
                    }
                })
                .map(new Func1<RegisterBean, RegisterBean.RstBean>() {
                    @Override
                    public RegisterBean.RstBean call(RegisterBean registerBean) {
                        return registerBean.getRst();
                    }
                })
                .subscribe(new Action1<RegisterBean.RstBean>() {
                    @Override
                    public void call(RegisterBean.RstBean resultsBean) {
                        ToastUtil.makeText(mContext, "发送成功！");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "数据请求失败，请重试~");
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
    private void registerApp() {
        String iphone = etPoneNumber.getText().toString().trim();
        String setVerifyCode = etVerificationCode.getText().toString().trim();
        String userName = etUserName.getText().toString().trim();
        String passWord = etPassWord.getText().toString().trim();
        RequestRegisterBean requestRegister = new RequestRegisterBean();
        requestRegister.setPhone(iphone);
        requestRegister.setVerify_code(setVerifyCode);
        requestRegister.setName(userName);
        requestRegister.setPassword(passWord);
        ServiceFactory.getProvideHttpService().register(requestRegister)
                .compose(this.<RegisterBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<RegisterBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<RegisterBean, Boolean>() {
                    @Override
                    public Boolean call(RegisterBean registerBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(registerBean.getErrno())) {
                            ToastUtil.makeText(mContext, registerBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(registerBean.getErrno());
                    }
                })
                .map(new Func1<RegisterBean, RegisterBean.RstBean>() {
                    @Override
                    public RegisterBean.RstBean call(RegisterBean registerBean) {
                        return registerBean.getRst();
                    }
                })
                .subscribe(new Action1<RegisterBean.RstBean>() {
                    @Override
                    public void call(RegisterBean.RstBean resultsBean) {
                        ToastUtil.makeText(mContext, "注册成功！");
                        startActivity(new Intent(mContext, MainActivity.class));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "数据请求失败，请重试~");
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
