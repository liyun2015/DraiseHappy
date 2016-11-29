package com.uban.rent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.ui.activity.welcome.WelcomeActivity;
import com.uban.rent.util.AppUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SplashAppActivity extends BaseActivity {
    public static final String APP_FIRST_WELCOME = "appFirstWelcome";
    public static final String APP_CONTENT_WELCOME = "appContentWelcome";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_app;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        initData();
    }

    private void initData() {
        if (isFirst()){
            saveNumberTimes(AppUtils.getAppVersionName(mContext));
            goActivity(WelcomeActivity.class);
        }else {
            if (HeaderConfig.isEmptyUbanToken()){
                goActivity(LoginActivity.class);
            }else {
                goActivity(MainActivity.class);
            }
        }

    }

    public void saveNumberTimes(String versionName) {

        SharedPreferences sp = getSharedPreferences(APP_FIRST_WELCOME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(APP_CONTENT_WELCOME, versionName);
        editor.commit();

    }

    private boolean isFirst() {
        SharedPreferences sp = getSharedPreferences(APP_FIRST_WELCOME,
                Context.MODE_PRIVATE);
        String spString = sp.getString(APP_CONTENT_WELCOME, "");
        if (TextUtils.isEmpty(spString)){
            return true;
        }
        if (spString.equals(AppUtils.getAppVersionName(mContext))){
            return false;
        }
        return true;
    }


    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
        finish();
    }
}
