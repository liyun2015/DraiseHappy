package com.or.goodlive.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
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
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.BaseResultsBean;
import com.or.goodlive.module.request.RegisterBean;
import com.or.goodlive.module.request.RequestRegisterBean;
import com.or.goodlive.module.request.RequestVerificationCode;
import com.or.goodlive.ui.activity.MainActivity;
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
 * Created by Administrator on 2017/2/14.
 * 找回密码发送验证码
 */

public class ForgotPasswordActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_pone_number)
    EditText etPoneNumber;
    @Bind(R.id.get_verification_code_btn)
    TextView getVerificationCodeBtn;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.forget_password_layout)
    RelativeLayout forgetPasswordLayout;
    @Bind(R.id.btn_next_submit)
    Button btnNextSubmit;
    @Bind(R.id.activity_login)
    LinearLayout activityLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
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
        toolbarContentText.setText("找回密码");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    @OnClick({R.id.get_verification_code_btn, R.id.btn_next_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_verification_code_btn:
                getVerificationode();
                break;
            case R.id.btn_next_submit:
                confirmForgetPasswordCode();
                break;
        }
    }
    private void confirmForgetPasswordCode() {
        String iphone = etPoneNumber.getText().toString().trim();
        String verificationCode = etVerificationCode.getText().toString().trim();
        RequestRegisterBean requestRegisterBean=new RequestRegisterBean();
        requestRegisterBean.setPhone(iphone);
        requestRegisterBean.setVerify_code(verificationCode);
        ServiceFactory.getProvideHttpService().confirmForgetPasswordCode(requestRegisterBean)
                .compose(this.<BaseResultsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<BaseResultsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<BaseResultsBean, Boolean>() {
                    @Override
                    public Boolean call(BaseResultsBean registerBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(registerBean.getErrno())) {
                            ToastUtil.makeText(mContext, registerBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(registerBean.getErrno());
                    }
                })
                .map(new Func1<BaseResultsBean, BaseResultsBean.RstBean>() {
                    @Override
                    public BaseResultsBean.RstBean call(BaseResultsBean registerBean) {
                        return registerBean.getRst();
                    }
                })
                .subscribe(new Action1<BaseResultsBean.RstBean>() {
                    @Override
                    public void call(BaseResultsBean.RstBean resultsBean) {
                        startActivity(new Intent(mContext, SetPasswordActivity.class));
                        finish();
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
    private void getVerificationode() {
        String iphone = etPoneNumber.getText().toString().trim();
        RequestVerificationCode requestVerificationCode = new RequestVerificationCode();
        requestVerificationCode.setPhone(iphone);
        ServiceFactory.getProvideHttpService().forgetPassword(requestVerificationCode)
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
}
