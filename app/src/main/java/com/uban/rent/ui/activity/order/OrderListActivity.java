package com.uban.rent.ui.activity.order;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.adapter.FragmentTabAdapter;
import com.uban.rent.ui.fragment.OrderAllFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderListActivity extends BaseActivity {

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_user_order)
    TabLayout tabUserOrder;
    @Bind(R.id.activity_user_order)
    CoordinatorLayout activityUserOrder;
    @Bind(R.id.view_pager_user_order)
    ViewPager viewPagerUserOrder;
    private List<Fragment> listFragments;

    private static final String[] TITLE_NAME = new String[]{"全部", "移动办公", "会议/活动"};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_order;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        listFragments = new ArrayList<>();
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("我的订单");
        viewPagerUserOrder.setOffscreenPageLimit(3);
        for (int i = 0; i < TITLE_NAME.length; i++) {
            listFragments.add(OrderAllFragment.newInstance(i));
        }
        tabUserOrder.setTabMode(TabLayout.MODE_FIXED);
        FragmentTabAdapter fAdapter = new FragmentTabAdapter(getSupportFragmentManager(),listFragments, Arrays.asList(TITLE_NAME));
        viewPagerUserOrder.setAdapter(fAdapter);
        tabUserOrder.setupWithViewPager(viewPagerUserOrder);

        tabUserOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    StatService.onEvent(mContext, "OrderList_FilterAllEvent", "pass", 1);
                }else if (tab.getPosition()==1){
                    StatService.onEvent(mContext, "OrderList_FilterMobiOffiEvent", "pass", 1);
                }else if (tab.getPosition()==2){
                    StatService.onEvent(mContext, "OrderList_FilterMeetingEvent", "pass", 1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
