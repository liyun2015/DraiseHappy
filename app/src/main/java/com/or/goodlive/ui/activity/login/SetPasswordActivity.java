package com.or.goodlive.ui.activity.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.request.RegisterBean;
import com.or.goodlive.module.request.RequestLogin;
import com.or.goodlive.module.request.RequestVerificationCode;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/14.
 * 设置密码
 */

public class SetPasswordActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_new_pass_word)
    EditText etNewPassWord;
    @Bind(R.id.et_affirm_new_pass_word)
    EditText etAffirmNewPassWord;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_password;
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

    @OnClick(R.id.btn_submit)
    public void onClick() {
        setPassWord();
    }

    private void setPassWord() {
        String newPassWord = etNewPassWord.getText().toString().trim();
        String newPassWordRe = etAffirmNewPassWord.getText().toString().trim();
        RequestLogin requestVerificationCode = new RequestLogin();
        requestVerificationCode.setPassword(newPassWord);
        requestVerificationCode.setRepeatPassword(newPassWordRe);
        ServiceFactory.getProvideHttpService().resetPassword(requestVerificationCode)
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
                        ToastUtil.makeText(mContext, "设置成功！");
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
}
