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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.or.goodlive.util.SPUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;


/**
 * Created by Administrator on 2017/2/7.
 */

public class MainActivity extends OldBaseActivity {

    @Bind(R.id.tl_main)
    TabLayout tabLayout;
    @Bind(R.id.vp_main)
    ViewPager viewPager;
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private List<Fragment> fragments;


    public int[] tabIcons = {R.drawable.selector_main_tab_cover, R.drawable.selector_main_tab_yaming, R.drawable.selector_main_tab_locale, R.drawable.selector_main_tab_peer};
    public String[] titles = {"封面", "一鸣", "现场", "同行"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
        registerPermissions();
        regiserEvent();
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
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.search_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("益直播");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_right_image, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                //搜索
                RxBus.getInstance().send(Events.EVENTS_DISMISS_POP,new Object());
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.right_icon:
                RxBus.getInstance().send(Events.EVENTS_DISMISS_POP,new Object());
                startActivity(new Intent(this, MyCenterActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
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
