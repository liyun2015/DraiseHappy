package com.or.goodlive.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.or.goodlive.ui.view.LoadingProgress;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public Context mContext;
    public Activity mActivity;
    private LoadingProgress loadingProgress;
    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void showLoadingView(){
        if (loadingProgress==null){
            loadingProgress = new LoadingProgress(mContext);
        }
        loadingProgress.show();
    }
    public void hideLoadingView() {
        if (loadingProgress!=null&&loadingProgress.isShowing()){
            loadingProgress.dismiss();
        }
    }
}
