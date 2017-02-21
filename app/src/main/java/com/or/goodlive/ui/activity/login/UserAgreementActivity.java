package com.or.goodlive.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.request.RegisterBean;
import com.or.goodlive.module.request.RequestRegisterBean;
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
 * Created by Administrator on 2017/2/18.
 */

public class UserAgreementActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_agree_submit)
    Button btnAgreeSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_agreement;
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
        toolbarContentText.setText("用户协议");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_agree_submit)
    public void onClick() {
        registerApp();
    }
    //注册
    private void registerApp() {
        Constants.isNoCookie=true;
        RequestRegisterBean requestRegister=(RequestRegisterBean)getIntent().getSerializableExtra(RegisterActivity.REGISTER_PARMARS);
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
}