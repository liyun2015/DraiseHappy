package com.or.goodlive.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.or.goodlive.App;
import com.or.goodlive.R;
import com.or.goodlive.base.OldBaseActivity;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.ui.activity.mycenter.MyCenterActivity;
import com.or.goodlive.ui.adapter.FragmentTabAdapter;
import com.or.goodlive.ui.fragment.CoverFragment;
import com.or.goodlive.ui.fragment.LocaleFragment;
import com.or.goodlive.ui.fragment.PeerFragment;
import com.or.goodlive.ui.fragment.YaMingFragment;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.SPUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.functions.Action1;


/**
 * Created by Administrator on 2017/2/7.
 */

public class MainActivity extends OldBaseActivity {

    @Bind(R.id.tl_main)
    TabLayout tabLayout;
    @Bind(R.id.vp_main)
    ViewPager viewPager;
    @Bind(R.id.search_icon)
    ImageView search_icon;
    @Bind(R.id.my_center_str)
    ImageView my_center_str;
    @Bind(R.id.my_center)
    ImageView my_center;
    @Bind(R.id.message_remend_icon)
    ImageView message_remend_icon;
    private List<Fragment> fragments;


    public int[] tabIcons = {R.drawable.selector_main_tab_cover, R.drawable.selector_main_tab_yaming, R.drawable.selector_main_tab_locale, R.drawable.selector_main_tab_peer};
    public String[] titles = {"封面", "一鸣", "现场", "同行"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setAlias();
        initView();
        initData();
        registerPermissions();
        regiserEvent();
    }
    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }
    private void setAlias() {
        String mPhone = (String) SPUtils.get(mContext, Constants.USER_ID, "");
        if (!TextUtils.isEmpty(mPhone)) {
            JPushInterface.setAlias(getApplicationContext(), mPhone, mAliasCallback);
        }
    }

    TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int i, String s, Set<String> set) {
            switch (i) {
                case 0:
//                    ToastUtil.makeText(mContext,"success push");
                    break;
                case 6002:
//                    ToastUtil.makeText(mContext,"timeout");
                    break;
            }
        }
    };
    private void initView() {
        String newMessage =  (String) SPUtils.get(mContext, Constants.EVENTS_HAVE_NEW_MESSAGE,"");
        if(!TextUtils.isEmpty(newMessage)){
            message_remend_icon.setVisibility(View.VISIBLE);
        }else{
            message_remend_icon.setVisibility(View.GONE);
        }
    }

    private void registerPermissions() {
        //Android 6.0 Permissions
        RxPermissions.getInstance(mContext).request(
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {

                        } else {

                        }
                    }
                });
    }
    private void regiserEvent() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_USER_LOGIN_OUT)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        if(!isFinishing()){
                            finish();
                        }
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
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
        RxBus.with(this)
                .setEvent(Events.EVENTS_CANSEL_MESSAGE)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        message_remend_icon.setVisibility(View.GONE);
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                })
                .create();
    }


    @OnClick({R.id.search_icon,R.id.my_center,R.id.my_center_str})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_icon:
                //搜索
                RxBus.getInstance().send(Events.EVENTS_DISMISS_POP,new Object());
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.my_center:
            case R.id.my_center_str:
                //个人中心
                message_remend_icon.setVisibility(View.GONE);
                RxBus.getInstance().send(Events.EVENTS_DISMISS_POP,new Object());
                startActivity(new Intent(this, MyCenterActivity.class));
                break;

        }
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(CoverFragment.newInstance());
        fragments.add(YaMingFragment.newInstance());
        fragments.add(LocaleFragment.newInstance());
        fragments.add(PeerFragment.newInstance());

        List<String> list_titles = new ArrayList<>();
        for (String titile : titles) {
            list_titles.add(titile);
        }
        FragmentTabAdapter adapter = new FragmentTabAdapter(getSupportFragmentManager(), fragments, list_titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager, true);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
        tabLayout.getTabAt(3).setCustomView(getTabView(3));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_main_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tv_main_tab);
        txt_title.setText(titles[position]);
        ImageView img_title = (ImageView) view.findViewById(R.id.iv_main_tab);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
