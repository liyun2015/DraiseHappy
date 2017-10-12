package com.or.draise_happy.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.or.draise_happy.R;
import com.or.draise_happy.api.config.ServiceFactory;
import com.or.draise_happy.base.BaseActivity;
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.request.RegisterBean;
import com.or.draise_happy.module.request.RequestRegisterBean;
import com.or.draise_happy.module.request.RequestVerificationCode;
import com.or.draise_happy.ui.activity.MainActivity;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.SPUtils;

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
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_pass_word)
    EditText etPassWord;
    @Bind(R.id.et_pone_number)
    EditText etPoneNumber;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.agreement_layout)
    RelativeLayout agreementLayout;
    @Bind(R.id.agreement_str)
    TextView agreementStr;
    @Bind(R.id.btn_login_login_submit)
    Button btnLoginLoginSubmit;
    @Bind(R.id.activity_login)
    LinearLayout activityLogin;
    @Bind(R.id.get_verification_code_btn)
    TextView getVerificationCodeBtn;
    @Bind(R.id.agree_btn)
    ImageView agree_btn;
    @Bind(R.id.agree_btn_view)
    ImageView agree_btn_view;
    public static final String REGISTER_PARMARS = "registerParmars";
    private boolean isAgreed=true;
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.right_textview_login, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.login_text:
//                startActivity(new Intent(mContext, LoginActivity.class));
//                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_login_login_submit, R.id.get_verification_code_btn,R.id.agreement_str,R.id.agree_btn,R.id.agree_btn_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login_submit:
                String phone = etPoneNumber.getText().toString().trim();
                String setVerifyCode = etVerificationCode.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String passWord = etPassWord.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    ToastUtil.makeText(mContext, "用户名不能为空");
                }else if(TextUtils.isEmpty(passWord)){
                    ToastUtil.makeText(mContext, "密码不能为空");
                } else if (TextUtils.isEmpty(phone)) {
                    ToastUtil.makeText(mContext, "手机号不能为空");
                }else if (phone.length() <= 10) {
                    ToastUtil.makeText(mContext, "请输入正确手机号");
                } else if (TextUtils.isEmpty(setVerifyCode)) {
                    ToastUtil.makeText(this, "验证码不能为空");
                } else {
                    String iphone = etPoneNumber.getText().toString().trim();
                    String setVerifyCodes = etVerificationCode.getText().toString().trim();
                    String userNames = etUserName.getText().toString().trim();
                    String passWords = etPassWord.getText().toString().trim();
                    RequestRegisterBean requestRegister = new RequestRegisterBean();
                    requestRegister.setPhone(iphone);
                    requestRegister.setVerify_code(setVerifyCodes);
                    requestRegister.setName(userNames);
                    requestRegister.setPassword(passWords);
                    if(!isAgreed){
                        ToastUtil.makeText(mContext, "请同意注册协议！");
                    }else{
                        registerApp(requestRegister);
                    }
                }
                break;
            case R.id.get_verification_code_btn:
                getVerificationode();
                break;
            case R.id.agreement_str:
                Intent intent = new Intent(mContext, UserAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.agree_btn_view:
            case R.id.agree_btn:
                if(isAgreed){
                    agree_btn.setBackgroundResource(R.drawable.unselect_box);
                    isAgreed=false;
                }else{
                    agree_btn.setBackgroundResource(R.drawable.select_box);
                    isAgreed=true;
                }
                break;
        }
    }
    //注册
    private void registerApp(RequestRegisterBean requestRegister) {
        Constants.isNoCookie=true;
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
                        SPUtils.put(mContext,Constants.PHPSESSID,resultsBean.getUser().getPHPSESSID());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
