package com.uban.rent.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.util.Constants;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SplashAppActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_app;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        initData();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initData() {
        if (HeaderConfig.isEmptyUbanToken()){
            goActivity(LoginActivity.class);
        }else {
            goActivity(MainActivity.class);
        }
    }
    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
        finish();
    }
}
