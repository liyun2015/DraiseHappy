package com.or.draise_happy.ui.activity.mycenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.or.draise_happy.R;
import com.or.draise_happy.api.config.ServiceFactory;
import com.or.draise_happy.base.BaseActivity;
import com.or.draise_happy.control.Events;
import com.or.draise_happy.control.RxBus;
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.BaseResultsBean;
import com.or.draise_happy.module.UserInfoBean;
import com.or.draise_happy.ui.activity.login.LoginActivity;
import com.or.draise_happy.ui.activity.login.UserAgreementActivity;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.ui.view.dialog.AlertDialogStyleApp;
import com.or.draise_happy.util.AppUtils;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.ImageLoadUtils;
import com.or.draise_happy.util.SPUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


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
    @Bind(R.id.message_remend_icon)
    ImageView message_remend_icon;
    public static final String HEADERURL = "headerUrl";
    public static final String USERNAME = "username";
    private String headerUrl;
    private String uname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_center;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
        regiserEvent();
    }

    private void initData() {
        headerUrl =  (String) SPUtils.get(this,Constants.AVATAR_URL,"");
        uname =  (String) SPUtils.get(this,Constants.USER_NAME,"");
        if(TextUtils.isEmpty(headerUrl)||TextUtils.isEmpty(uname)){
            getUserInfor();
        }else{
            userName.setText(uname);
            ImageLoadUtils.displayHeadIcon(headerUrl, userHeaderPic);
        }
    }

    private void getUserInfor() {
        Map<String, String> params = new HashMap<>();
        ServiceFactory.getProvideHttpService().personalCenter(params)
                .compose(this.<UserInfoBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<UserInfoBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<UserInfoBean, Boolean>() {
                    @Override
                    public Boolean call(UserInfoBean resultsBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(resultsBean.getErrno())) {
                            ToastUtil.makeText(mContext, resultsBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(resultsBean.getErrno());
                    }
                })
                .map(new Func1<UserInfoBean, UserInfoBean.RstBean>() {
                    @Override
                    public UserInfoBean.RstBean call(UserInfoBean resultsBean) {
                        return resultsBean.getRst();
                    }
                })
                .subscribe(new Action1<UserInfoBean.RstBean>() {
                    @Override
                    public void call(UserInfoBean.RstBean resultsBean) {
                        if(resultsBean!=null){
                            initDataView(resultsBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, " 数据加载失败！");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void initDataView(UserInfoBean.RstBean resultsBean) {
        uname = resultsBean.getUser().getName();
        headerUrl =resultsBean.getUser().getAvatar_url();
        userName.setText(uname);
        ImageLoadUtils.displayHeadIcon(headerUrl, userHeaderPic);
    }
    private void regiserEvent() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_UPDATA_HEADER)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        initData();
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("loginEventError",throwable.getMessage());
                    }
                })
                .create();
        RxBus.with(this)
                .setEvent(Events.EVENTS_NEW_MESSAGE)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        message_remend_icon.setVisibility(View.VISIBLE);
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                })
                .create();
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
        String newMessage =  (String) SPUtils.get(mContext, Constants.EVENTS_HAVE_NEW_MESSAGE,"");
        if(!TextUtils.isEmpty(newMessage)){
            message_remend_icon.setVisibility(View.VISIBLE);
        }else{
            message_remend_icon.setVisibility(View.GONE);
        }
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
                message_remend_icon.setVisibility(View.GONE);
                RxBus.getInstance().send(Events.EVENTS_CANSEL_MESSAGE,new Object());
                SPUtils.put(this, Constants.EVENTS_HAVE_NEW_MESSAGE, "");
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
                checkVersion();
                break;
            case R.id.clear_cache_layout://清除缓存
                clearsCache();
                break;
            case R.id.use_information_layout://修改用户资料
                Intent intent = new Intent();
                intent.setClass(mContext, ModifyUserInforActivity.class);
                intent.putExtra(HEADERURL, headerUrl);
                intent.putExtra(USERNAME,uname );
                startActivity(intent);
                break;
            case R.id.sigin_out_btn://退出登录
                siginOut();
                break;
        }

    }

    private void checkVersion() {
        Map<String, String> params = new HashMap<>();
        ServiceFactory.getProvideHttpService().heckUpdate(params)
                .compose(this.<BaseResultsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<BaseResultsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<BaseResultsBean, Boolean>() {
                    @Override
                    public Boolean call(BaseResultsBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<BaseResultsBean, BaseResultsBean.RstBean>() {
                    @Override
                    public BaseResultsBean.RstBean call(BaseResultsBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<BaseResultsBean.RstBean>() {
                    @Override
                    public void call(BaseResultsBean.RstBean resultsBean) {
                        versionUpgrade(resultsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "请求失败！");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }


    private void clearsCache() {
        AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
        alertDialogStyleApp.builder()
                .setTitle("提示：")
                .setMsg("确定清除缓存？")
                .setNegativeButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // img缓存
                        ToastUtil.makeText(mContext, "缓存已经清除");
                    }
                }).show();
    }
    private String msgStr = "已是最新版本！";
    private void versionUpgrade(BaseResultsBean.RstBean resultsBean) {
        String version = resultsBean.getAndroid_version();
        String versionNow = AppUtils.getAppVersionName(this);
        final String downloadUrl = resultsBean.getUrl();
        if(version.equals(versionNow)){
            msgStr = "已是最新版本！";
            AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
            alertDialogStyleApp.builder()
                    .setTitle("提示：")
                    .setMsg(msgStr)
                    .setNegativeButton("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }else{
            msgStr = "发现新版本，是否更新？";
            AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
            alertDialogStyleApp.builder()
                    .setTitle("提示：")
                    .setMsg(msgStr)
                    .setNegativeButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startDownload(downloadUrl);
                        }
                    }).show();
        }

    }
    private void startDownload(String downloadUrl) {
        Uri uri = Uri.parse(downloadUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        finish();
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
