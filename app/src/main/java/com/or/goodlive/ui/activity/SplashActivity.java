package com.or.goodlive.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.or.goodlive.R;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.ui.activity.login.LoginActivity;
import com.or.goodlive.ui.activity.welcome.WelcomeActivity;
import com.or.goodlive.util.AppUtils;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.SPUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;


/**
 * Created by Administrator on 2017/1/9.
 */

public class SplashActivity extends BaseActivity {
    private  final int GO_HOME = 1000;
    private  final int GO_LOGIN = 1001;
    private  final long SPLASH_DELAY_MILLIS = 500;//时间
    public static final String APP_FIRST_WELCOME = "appFirstWelcome";
    public static final String APP_CONTENT_WELCOME = "appContentWelcome";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_app;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        String mFlag = (String) SPUtils.get(this, Constants.PHPSESSID, "");
        if (TextUtils.isEmpty(mFlag)) {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goActivity(MainActivity.class);
                    break;
                case GO_LOGIN:
                    filterViewActivity();
                    break;
                default:
                    break;
            }
            finish();
        }
    };
    public void goActivity(Class<?> c) {
        Intent intent = new Intent(this, c);
        this.startActivity(intent);
    }

    private void filterViewActivity() {
        if (isFirst()) {
            saveNumberTimes(AppUtils.getAppVersionName(mContext));
            goActivity(WelcomeActivity.class);
        } else {
            goActivity(LoginActivity.class);
        }
    }
    private boolean isFirst() {
        SharedPreferences sp = getSharedPreferences(APP_FIRST_WELCOME,
                Context.MODE_PRIVATE);
        String spString = sp.getString(APP_CONTENT_WELCOME, "");
        if (TextUtils.isEmpty(spString)) {
            return true;
        }
        if (spString.equals(AppUtils.getAppVersionName(mContext))) {
            return false;
        }
        return true;
    }
    public void saveNumberTimes(String versionName) {
        SharedPreferences sp = getSharedPreferences(APP_FIRST_WELCOME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(APP_CONTENT_WELCOME, versionName);
        editor.commit();

    }
}
