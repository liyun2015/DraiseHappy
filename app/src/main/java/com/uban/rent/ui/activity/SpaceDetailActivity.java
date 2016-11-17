package com.uban.rent.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.ui.view.banner.LoopViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;


/**
 * 空间详情页
 */
public class SpaceDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    LoopViewPager viewpager;
    @Bind(R.id.image_desc)
    TextView imageDesc;
    @Bind(R.id.tab_space_detail)
    TabLayout tabSpaceDetail;
    private static final String[] TITLE_NAME = new String[]{"时租", "日租", "月租"};
    @Bind(R.id.lv_space_detail)
    UbanListView lvSpaceDetail;
    private List<String> mDatas = new ArrayList<>(Arrays.asList("nadd", "adf", "fafff", "dfdsfy", "fsdf"));

    @Override
    protected int getLayoutId() {
        return R.layout.activity_space_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();

    }

    private void initData() {
        RequestSpaceDetail requestSpaceDetail = new RequestSpaceDetail();
        requestSpaceDetail.setOfficespaceBasicinfoId(2);
        ServiceFactory.getProvideHttpService().getOfficeSpaceInfo(requestSpaceDetail)
                .compose(this.<String>bindToLifecycle())
                .compose(RxSchedulersHelper.<String>io_main())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle("");
        }
        tabSpaceDetail.setTabMode(TabLayout.MODE_FIXED);
        tabSpaceDetail.addTab(tabSpaceDetail.newTab().setText(TITLE_NAME[0]));
        tabSpaceDetail.addTab(tabSpaceDetail.newTab().setText(TITLE_NAME[1]));
        tabSpaceDetail.addTab(tabSpaceDetail.newTab().setText(TITLE_NAME[2]));
        SpaceDetailRentTypeAdapter mAdapter = new SpaceDetailRentTypeAdapter(mContext,mDatas);
        lvSpaceDetail.setAdapter(mAdapter);

        tabSpaceDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // ToastUtil.makeText(mContext,tab.getPosition()+"");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.setAdapter(new BannerPicAdapter(this));
        imageDesc.setText(1 + "/" + viewpager.getAdapter().getCount());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageDesc.setText(position + 1 + "/" + viewpager.getAdapter().getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
