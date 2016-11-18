package com.uban.rent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.EllipsizeText;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.ui.view.banner.LoopViewPager;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;
import com.uban.rent.util.StringUtils;

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
    public static final String KEY_BUILD_SPACE_DETAIL = "BUILD_SPACE_DETAIL";

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
    @Bind(R.id.tv_space_name)
    TextView tvSpaceName;
    @Bind(R.id.tv_space_address)
    TextView tvSpaceAddress;
    @Bind(R.id.tv_space_location)
    TextView tvSpaceLocation;
    @Bind(R.id.tv_station_nums)
    TextView tvStationNums;
    @Bind(R.id.tv_space_desc)
    EllipsizeText tvSpaceDesc;
    @Bind(R.id.tv_space_area)
    TextView tvSpaceArea;
    @Bind(R.id.tv_space_public_rate)
    TextView tvSpacePublicRate;
    @Bind(R.id.tv_space_layers)
    TextView tvSpaceLayers;
    @Bind(R.id.tv_space_station_area)
    TextView tvSpaceStationArea;
    @Bind(R.id.tv_look_more_desc)
    TextView tvLookMoreDesc;

    private boolean isLookMoreDesc = true;
    private int officeSpaceBasicInfoId = 0;
    private double locationx = 0;
    private double locationy = 0;
    private SpaceDetailRentTypeAdapter spaceDetailRentTypeAdapter;
    private List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen ;
    private RequestGoSpaceDetail requestGoSpaceDetail;
    private int mPriceType = 0;
    private static final int KEY_ORDER_ALL = 0;
    private static final int KEY_MOBILE_OFFICE = 1;
    private static final int KEY_MEETIONGS_EVENTS = 2;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_space_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        spaceDeskTypePriceListBeen = new ArrayList<>();
        requestGoSpaceDetail = (RequestGoSpaceDetail) getIntent().getSerializableExtra(KEY_BUILD_SPACE_DETAIL);
        locationx = requestGoSpaceDetail.getLocationX();
        locationy = requestGoSpaceDetail.getLocationY();
        officeSpaceBasicInfoId = requestGoSpaceDetail.getOfficeSpaceBasicInfoId();
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
                        initViewData(resultsBean);
                        spaceDeskTypePriceListBeen.clear();
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

    private void setBannerImages(SpaceDetailBean.ResultsBean resultsBean){
        List<String> images = new ArrayList<>();
        for (SpaceDetailBean.ResultsBean.PicListBean picListBean:
                resultsBean.getPicList()) {
            images.add(String.format(Constants.APP_IMG_URL_640_420,picListBean.getImgPath()));
        }
        if (images==null&&images.size()>0){
            return;
        }
        BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(this);
        bannerPicAdapter.setData(images);
        viewpager.setAdapter(bannerPicAdapter);
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

    private void initViewData(SpaceDetailBean.ResultsBean resultsBean) {
        setBannerImages(resultsBean);//头部banner图片
        toolbarContentText.setText(resultsBean.getSpaceCnName());
        tvSpaceName.setText(resultsBean.getSpaceCnName());
        tvSpaceAddress.setText(resultsBean.getAddress());
        tvSpaceLocation.setText(resultsBean.getSpaceDistrict() + "－" + resultsBean.getSpaceBusinessArea());
        tvStationNums.setText(resultsBean.getRentNum() + "个工位在租");
        tvSpaceDesc.setText(resultsBean.getSpaceProfile());
        tvSpaceArea.setText(resultsBean.getSpaceArea() + "m²");
        tvSpacePublicRate.setText(resultsBean.getSpaceProportion() + "%");
        tvSpaceLayers.setText(resultsBean.getSpaceFloor() + "层");
        tvSpaceStationArea.setText(StringUtils.removeZero(String.valueOf(resultsBean.getPerStationArea())) + "m²/工位");
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
                mPriceType = tab.getPosition();
                spaceDetailRentTypeAdapter.setPriceType(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        lvSpaceDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(mContext, WorkplaceDetailActivity.class);
                intent.putExtra(WorkplaceDetailActivity.KEY_BUILD_WORK_PLACE_DETAIL,requestGoWorkPlaceDetailBean(i));
                intent.putExtra(KEY_BUILD_SPACE_DETAIL,requestGoSpaceDetail);
                startActivity(intent);
            }
        });
    }

    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetailBean(int position){
        SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean = spaceDeskTypePriceListBeen.get(position);
        RequestGoWorkPlaceDetail requestGoWorkPlaceDetail = new RequestGoWorkPlaceDetail();
        int price = KEY_ORDER_ALL;
        if (mPriceType == KEY_ORDER_ALL) {
            price = spaceDeskTypePriceListBean.getHourPrice();
        } else if (mPriceType == KEY_MOBILE_OFFICE) {
            price = spaceDeskTypePriceListBean.getDayPrice();
        } else if (mPriceType == KEY_MEETIONGS_EVENTS) {
            price = spaceDeskTypePriceListBean.getWorkDeskPrice();
        }
        requestGoWorkPlaceDetail.setPrice(price);
        requestGoWorkPlaceDetail.setPriceType(mPriceType);
        requestGoWorkPlaceDetail.setWorkplaceDetailId(spaceDeskTypePriceListBean.getId());
        return requestGoWorkPlaceDetail;
    }

    private void lookMoreDesc(boolean b){
        //查看更多描述
        if (isLookMoreDesc) {
            tvSpaceDesc.setEllipsize(null); // 展开
            tvSpaceDesc.setMaxLines(100);
            tvLookMoreDesc.setText("收起简介");
        } else {
            tvSpaceDesc.setEllipsize(TextUtils.TruncateAt.END); // 收缩
            tvSpaceDesc.setMaxLines(4);
            tvLookMoreDesc.setText("展开");
        }
        isLookMoreDesc = !b;
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


    @OnClick({R.id.call_phone, R.id.rl_panorama, R.id.rl_supporting, R.id.tv_look_more_desc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_phone:
                PhoneUtils.call(mContext, Constants.PHONE_NUMBER);
                break;
            case R.id.rl_panorama:
                goActivity(PanoramaActivity.class);
                break;
            case R.id.rl_supporting:
                Intent intent = new Intent();
                intent.setClass(mContext,SupportingActivity.class);
                intent.putExtra(SupportingActivity.KEY_LOCATION_X,locationx);
                intent.putExtra(SupportingActivity.KEY_LOCATION_Y,locationy);
                startActivity(intent);
                break;
            case R.id.tv_look_more_desc:
                lookMoreDesc(isLookMoreDesc);
                break;
        }
    }

    private void goActivity(Class<?> cls){
        startActivity(new Intent(mContext,cls));
    }
}
