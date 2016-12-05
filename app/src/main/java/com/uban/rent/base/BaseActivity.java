package com.uban.rent.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.uban.rent.App;
import com.uban.rent.ui.activity.member.MemberFinalActivity;
import com.uban.rent.ui.activity.member.MemberFirstActivity;
import com.uban.rent.ui.activity.member.MemberStatusActivity;
import com.uban.rent.ui.view.LoadingProgress;
import com.uban.rent.util.Constants;
import com.uban.rent.util.SPUtils;

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

    public void BaseActivityMemberStatusGoView(){
        int memberStatus = (int) SPUtils.get(App.getInstance(),Constants.USER_MEMBER,0);
        if (memberStatus==Constants.MEMBER_STATUS_NOT){
            startActivity(new Intent(mContext, MemberFirstActivity.class));
        }else if (memberStatus==Constants.MEMBER_STATUS_APPLYING){//申请中
            startActivity(new Intent(mContext, MemberStatusActivity.class));
        }else if (memberStatus ==Constants.MEMBER_STATUS_SUCCESS){
            startActivity(new Intent(mContext, MemberFinalActivity.class));
        }else if (memberStatus == Constants.MEMBER_STATUS_BE_OVERDUE){
            startActivity(new Intent(mContext, MemberFirstActivity.class));
        }
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
