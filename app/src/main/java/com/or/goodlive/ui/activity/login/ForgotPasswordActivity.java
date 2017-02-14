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
import com.or.goodlive.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @OnClick({R.id.forget_password_layout, R.id.btn_next_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password_layout:
                break;
            case R.id.btn_next_submit:
                startActivity(new Intent(mContext, SetPasswordActivity.class));
                break;
        }
    }
}
