package com.or.goodlive.ui.activity.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.HeaderConfig;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.control.events.UserLoginEvents;
import com.or.goodlive.ui.activity.login.LoginActivity;
import com.or.goodlive.ui.activity.login.UserAgreementActivity;
import com.or.goodlive.ui.view.dialog.AlertDialogStyleApp;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/17.
 * 个人中心
 */

public class MyCenterActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.user_header_pic)
    ImageView userHeaderPic;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.message_icon)
    ImageView messageIcon;
    @Bind(R.id.message_icon_layout)
    RelativeLayout messageIconLayout;
    @Bind(R.id.about_us_icon)
    ImageView aboutUsIcon;
    @Bind(R.id.about_us_layout)
    RelativeLayout aboutUsLayout;
    @Bind(R.id.user_agreement_icon)
    ImageView userAgreementIcon;
    @Bind(R.id.user_agreement_layout)
    RelativeLayout userAgreementLayout;
    @Bind(R.id.modify_password_icon)
    ImageView modifyPasswordIcon;
    @Bind(R.id.modify_password_layout)
    RelativeLayout modifyPasswordLayout;
    @Bind(R.id.version_upgrade_icon)
    ImageView versionUpgradeIcon;
    @Bind(R.id.version_upgrade_layout)
    RelativeLayout versionUpgradeLayout;
    @Bind(R.id.clear_cache_icon)
    ImageView clearCacheIcon;
    @Bind(R.id.clear_cache_layout)
    RelativeLayout clearCacheLayout;
    @Bind(R.id.use_information_layout)
    RelativeLayout useInformationLayout;
    @Bind(R.id.sigin_out_btn)
    Button siginBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_center;
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
        toolbarContentText.setText("我的");
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

    @OnClick({R.id.sigin_out_btn,R.id.use_information_layout,R.id.message_icon_layout, R.id.about_us_layout, R.id.user_agreement_layout, R.id.modify_password_layout, R.id.version_upgrade_layout, R.id.clear_cache_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_icon_layout://我的消息
                startActivity(new Intent(this, MyMessageActivity.class));
                break;
            case R.id.about_us_layout:
                startActivity(new Intent(this, AboutWeActivity.class));
                break;
            case R.id.user_agreement_layout:
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;
            case R.id.modify_password_layout:
                startActivity(new Intent(this, ModifyPassWordActivity.class));
                break;
            case R.id.version_upgrade_layout://版本更新
                break;
            case R.id.clear_cache_layout://清除缓存
                break;
            case R.id.use_information_layout://修改用户资料
                startActivity(new Intent(this, ModifyUserInforActivity.class));
                break;
            case R.id.sigin_out_btn://退出登录
                siginOut();
                break;
        }

    }
    /**
     * 退出登录
     */
    private void siginOut() {
        AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
        alertDialogStyleApp.builder()
                .setMsg("确定退出登录？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtils.clear(mContext);
                        RxBus.getInstance().send(Events.EVENTS_USER_LOGIN_OUT, new Object());
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

}
