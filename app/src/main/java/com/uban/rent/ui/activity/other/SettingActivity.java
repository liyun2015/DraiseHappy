package com.uban.rent.ui.activity.other;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.dialog.AlertDialogStyleApp;
import com.uban.rent.util.SPUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.Log;

import java.lang.ref.WeakReference;

import butterknife.Bind;

public class SettingActivity extends BaseActivity {
    private static final String[] ITEM_LIST = new String[]{"关于优办移动办公", "用户协议", "推荐给好友","退出登录"};
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_setting)
    ListView lvSetting;
    @Bind(R.id.activity_setting)
    LinearLayout activitySetting;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initSocial();
    }

    private void initSocial() {
        SHARE_MEDIA[] shareMedias = new SHARE_MEDIA[]{
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL,
        };
        mShareListener = new CustomShareListener(SettingActivity.this);
        mShareAction = new ShareAction(SettingActivity.this).setDisplayList(shareMedias)
                .setCallback(mShareListener);
    }

    private void goActivity(Class<?> cls){
        startActivity(new Intent(mContext,cls));
    }
    /**
     * 退出登录
     */
    private void siginOut() {
        AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
        alertDialogStyleApp.builder()
                .setMsg("退出登录")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtils.clear(mContext);
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(mContext, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("设置");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, ITEM_LIST);
        lvSetting.setAdapter(stringArrayAdapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        StatService.onEvent(mContext, "Setting_AboutUbanOfficeEvent", "pass", 1);
                        goActivity(AboutUsActivity.class);
                        break;
                    case 1:
                        StatService.onEvent(mContext, "Setting_UserProtocolEvent", "pass", 1);
                        goActivity(AgreementActivity.class);
                        break;
                    case 2:
                        StatService.onEvent(mContext, "Setting_ShareToFriendsEvent", "pass", 1);
                        String shareTitle = "优办移动办公";
                        String shareUrl = "http://bj.uban.com/";
                        UMImage shareImage = new UMImage(mContext,R.mipmap.ic_launcher);

                        ShareBoardConfig config = new ShareBoardConfig();
                        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
                        mShareAction.withText("优办移动办公App")
                                .withMedia(shareImage)
                                .withTitle(shareTitle)
                                .withTargetUrl(shareUrl)
                                .open(config);
                        break;
                    case 3:/*
                        StatService.onEvent(mContext, "Setting_MarkForUsEvent", "pass", 1);
                        break;
                    case 4:*/
                        StatService.onEvent(mContext, "Setting_LoginOutEvent", "pass", 1);
                        siginOut();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<SettingActivity> mActivity;

        private CustomShareListener(SettingActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtil.makeText(mActivity.get(),"收藏成功");
            } else {
                if (platform!= SHARE_MEDIA.MORE&&platform!=SHARE_MEDIA.SMS
                        &&platform!=SHARE_MEDIA.EMAIL
                        &&platform!=SHARE_MEDIA.FLICKR
                        &&platform!=SHARE_MEDIA.FOURSQUARE
                        &&platform!=SHARE_MEDIA.TUMBLR
                        &&platform!=SHARE_MEDIA.POCKET
                        &&platform!=SHARE_MEDIA.PINTEREST
                        &&platform!=SHARE_MEDIA.LINKEDIN
                        &&platform!=SHARE_MEDIA.INSTAGRAM
                        &&platform!=SHARE_MEDIA.GOOGLEPLUS
                        &&platform!=SHARE_MEDIA.YNOTE
                        &&platform!=SHARE_MEDIA.EVERNOTE){
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform!= SHARE_MEDIA.MORE&&platform!=SHARE_MEDIA.SMS
                    &&platform!=SHARE_MEDIA.EMAIL
                    &&platform!=SHARE_MEDIA.FLICKR
                    &&platform!=SHARE_MEDIA.FOURSQUARE
                    &&platform!=SHARE_MEDIA.TUMBLR
                    &&platform!=SHARE_MEDIA.POCKET
                    &&platform!=SHARE_MEDIA.PINTEREST
                    &&platform!=SHARE_MEDIA.LINKEDIN
                    &&platform!=SHARE_MEDIA.INSTAGRAM
                    &&platform!=SHARE_MEDIA.GOOGLEPLUS
                    &&platform!=SHARE_MEDIA.YNOTE
                    &&platform!=SHARE_MEDIA.EVERNOTE){
                ToastUtil.makeText(mActivity.get(),"分享失败");
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
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
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"设置页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"设置页");
    }
}
