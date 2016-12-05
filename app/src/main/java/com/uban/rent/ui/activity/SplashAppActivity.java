package com.uban.rent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.VersionBean;
import com.uban.rent.module.request.RequestVersion;
import com.uban.rent.ui.activity.welcome.WelcomeActivity;
import com.uban.rent.ui.view.dialog.AlertDialogStyleApp;
import com.uban.rent.util.AppUtils;
import com.uban.rent.util.Constants;

import rx.functions.Action1;
import rx.functions.Func1;

public class SplashAppActivity extends BaseActivity {
    public static final String APP_FIRST_WELCOME = "appFirstWelcome";
    public static final String APP_CONTENT_WELCOME = "appContentWelcome";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_app;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        RequestVersion requestVersion = new RequestVersion();
        requestVersion.setAppType(Constants.APP_TYPE);
        requestVersion.setName(AppUtils.getAppVersionName(mContext));
        ServiceFactory.getProvideHttpService().getAppNewVersion(requestVersion)
                .compose(this.<VersionBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<VersionBean>io_main())
                .filter(new Func1<VersionBean, Boolean>() {
                    @Override
                    public Boolean call(VersionBean versionBean) {
                        return versionBean.getStatusCode()==Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .filter(new Func1<VersionBean, Boolean>() {
                    @Override
                    public Boolean call(VersionBean versionBean) {
                        if (versionBean.getResults()==null){
                            filterViewActivity();
                        }
                        return versionBean.getResults()!=null;
                    }
                })
                .map(new Func1<VersionBean, VersionBean.ResultsBean>() {
                    @Override
                    public VersionBean.ResultsBean call(VersionBean versionBean) {
                        return versionBean.getResults();
                    }
                })
                .subscribe(new Action1<VersionBean.ResultsBean>() {
                    @Override
                    public void call(final VersionBean.ResultsBean resultsBean) {
                        AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
                        alertDialogStyleApp.builder().setTitle("更新提示")
                                .setMsg("确定要版本更新吗")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String downloadUrl = resultsBean.getDownpath();
                                        Uri uri = Uri.parse(downloadUrl);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        filterViewActivity();
                                    }
                                })
                                .show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        filterViewActivity();
                    }
                });
    }

    private void filterViewActivity(){
        if (isFirst()){
            saveNumberTimes(AppUtils.getAppVersionName(mContext));
            goActivity(WelcomeActivity.class);
        }else {
            goActivity(MainActivity.class);
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
