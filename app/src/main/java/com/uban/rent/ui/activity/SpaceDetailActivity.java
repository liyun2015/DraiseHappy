package com.uban.rent.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.ui.view.banner.LoopViewPager;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * 空间详情页
 */
public class SpaceDetailActivity extends BaseActivity {
    public static final String OFFICE_SPACE_BASIC_INFO_ID = "officeSpaceBasicInfoId";
    public static final String LOCATION_X = "location_x";
    public static final String LOCATION_Y = "location_y";

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
    @Bind(R.id.top_view)
    RelativeLayout topView;
    @Bind(R.id.call_phone)
    TextView callPhone;
    @Bind(R.id.rl_panorama)
    RelativeLayout rlPanorama;
    @Bind(R.id.rl_supporting)
    RelativeLayout rlSupporting;

    private int officeSpaceBasicInfoId = 0;
    private double locationx = 0;
    private double locationy = 0;
    private SpaceDetailRentTypeAdapter spaceDetailRentTypeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_space_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        officeSpaceBasicInfoId = getIntent().getIntExtra(OFFICE_SPACE_BASIC_INFO_ID, 0);
        locationx = getIntent().getDoubleExtra(LOCATION_X, 0.0);
        locationy = getIntent().getDoubleExtra(LOCATION_Y, 0.0);
        initData();
        initView();

    }

    private void initData() {
        RequestSpaceDetail requestSpaceDetail = new RequestSpaceDetail();
        requestSpaceDetail.setOfficespaceBasicinfoId(officeSpaceBasicInfoId);
        requestSpaceDetail.setLocationX(locationx);
        requestSpaceDetail.setLocationY(locationy);
        ServiceFactory.getProvideHttpService().getOfficeSpaceInfo(requestSpaceDetail)
                .compose(this.<SpaceDetailBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<SpaceDetailBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<SpaceDetailBean, Boolean>() {
                    @Override
                    public Boolean call(SpaceDetailBean spaceDetailBean) {
                        return spaceDetailBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<SpaceDetailBean, SpaceDetailBean.ResultsBean>() {
                    @Override
                    public SpaceDetailBean.ResultsBean call(SpaceDetailBean spaceDetailBean) {
                        return spaceDetailBean.getResults();
                    }
                })
                .subscribe(new Action1<SpaceDetailBean.ResultsBean>() {
                    @Override
                    public void call(SpaceDetailBean.ResultsBean resultsBean) {
                        List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen = new ArrayList<>();
                        spaceDeskTypePriceListBeen.addAll(resultsBean.getSpaceDeskTypePriceList());
                        spaceDetailRentTypeAdapter = new SpaceDetailRentTypeAdapter(mContext, spaceDeskTypePriceListBeen);
                        lvSpaceDetail.setAdapter(spaceDetailRentTypeAdapter);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "数据加载失败");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("空间详情");
        topView.setFocusable(true);
        topView.setFocusableInTouchMode(true);
        topView.requestFocus();


        tabSpaceDetail.setTabMode(TabLayout.MODE_FIXED);
        tabSpaceDetail.addTab(tabSpaceDetail.newTab().setText(TITLE_NAME[0]));
        tabSpaceDetail.addTab(tabSpaceDetail.newTab().setText(TITLE_NAME[1]));
        tabSpaceDetail.addTab(tabSpaceDetail.newTab().setText(TITLE_NAME[2]));

        tabSpaceDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                spaceDetailRentTypeAdapter.setPriceType(tab.getPosition());
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

    @OnClick({R.id.call_phone, R.id.rl_panorama, R.id.rl_supporting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_phone:
                PhoneUtils.call(mContext,Constants.PHONE_NUMBER);
                break;
            case R.id.rl_panorama:
                break;
            case R.id.rl_supporting:
                break;
        }
    }
}
