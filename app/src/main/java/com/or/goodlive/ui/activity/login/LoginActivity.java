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
import android.widget.RelativeLayout;
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


public class LoginActivity extends BaseActivity {

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
    private String code;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
        toolbarContentText.setText("登录");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.register_text:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_login_login_submit,R.id.password_find_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login_submit:
                code = etLoginCodeNum.getText().toString().trim();
                phone = etLoginPhoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(mContext, "手机号不能为空");
                }  else if (TextUtils.isEmpty(code)) {
                    ToastUtil.makeText(this, "验证码不能为空");
                } else {
                    loginApp();
                }
                break;
            case R.id.password_find_layout:
                startActivity(new Intent(mContext, ForgotPasswordActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.right_textview, menu);
        return true;
    }

    //登录
    private void loginApp() {
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
