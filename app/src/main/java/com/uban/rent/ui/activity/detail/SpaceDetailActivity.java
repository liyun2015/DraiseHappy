package com.uban.rent.ui.activity.detail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.network.config.HeaderConfig;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.activity.components.EquipmentServiceActivity;
import com.uban.rent.ui.activity.components.PanoramaActivity;
import com.uban.rent.ui.activity.components.SupportingActivity;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.EquipmentGridViewAdapter;
import com.uban.rent.ui.adapter.ServiceGridViewAdapter;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.textview.EllipsizeText;
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
    AppBarLayout topView;
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
    @Bind(R.id.gridview_equipment_space_detail)
    GridView gridviewEquipmentSpaceDetail;
    @Bind(R.id.gridview_service_space_detail)
    GridView gridviewServiceSpaceDetail;
    @Bind(R.id.iv_showEquipmentList)
    ImageView ivShowEquipmentList;
    @Bind(R.id.iv_showServiceList)
    ImageView ivShowServiceList;

    private boolean isLookMoreDesc = true;
    private int officeSpaceBasicInfoId = 0;
    private double locationx = 0;
    private double locationy = 0;
    private SpaceDetailRentTypeAdapter spaceDetailRentTypeAdapter;
    private List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen;
    private RequestGoSpaceDetail requestGoSpaceDetail;
    private int mPriceType = 0;
    private static final int KEY_ORDER_ALL = 0;
    private static final int KEY_MOBILE_OFFICE = 1;
    private static final int KEY_MEETIONGS_EVENTS = 2;
    private String mSpaceNamePinyin;
    private ArrayList<String> panoramaImages;
    private ArrayList<String> panoramaDesc;
    private ArrayList<String> equipmentsImages;
    private ArrayList<String> equipmentsnames;
    private ArrayList<String> servicesImages;
    private ArrayList<String> servicesnames;
    private CreateOrderParamaBean createOrderParamaBean;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_space_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        panoramaImages = new ArrayList<>();
        panoramaDesc = new ArrayList<>();
        equipmentsImages = new ArrayList<>();
        equipmentsnames = new ArrayList<>();
        servicesImages = new ArrayList<>();
        servicesnames = new ArrayList<>();
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
                        createOrderParamaBean =new CreateOrderParamaBean();
                        createOrderParamaBean.setPriceType(mPriceType);
                        createOrderParamaBean.setSpaceDeskAddress(resultsBean.getAddress());
                        createOrderParamaBean.setSpaceDeskName(resultsBean.getSpaceCnName());
                        createOrderParamaBean.setSpaceDeskId(resultsBean.getOfficespaceBasicinfoId());
                        spaceDeskTypePriceListBeen.clear();
                        spaceDeskTypePriceListBeen.addAll(resultsBean.getSpaceDeskTypePriceList());
                        spaceDetailRentTypeAdapter = new SpaceDetailRentTypeAdapter(mContext, spaceDeskTypePriceListBeen,createOrderParamaBean);
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

    private void setBannerImages(SpaceDetailBean.ResultsBean resultsBean) {
        List<String> images = new ArrayList<>();
        for (SpaceDetailBean.ResultsBean.PicListBean picListBean :
                resultsBean.getPicList()) {
            images.add(String.format(Constants.APP_IMG_URL_640_420, picListBean.getImgPath()));
        }
        if (images == null && images.size() > 0) {
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

    private void setPanorama(SpaceDetailBean.ResultsBean resultsBean) {
        for (SpaceDetailBean.ResultsBean.Pic3dListBean pic3dListBean :
                resultsBean.getPic3dList()) {
            panoramaImages.add(String.format(Constants.APP_IMG_PANORAMA_URL, pic3dListBean.getImgPath()));
            panoramaDesc.add(pic3dListBean.getImgDesc());
        }
    }

    private void setaEquipmentServiceList(SpaceDetailBean.ResultsBean resultsBean) {
        for (SpaceDetailBean.ResultsBean.EquipmentListBean equipmentListBean:
             resultsBean.getEquipmentList()) {
            equipmentsImages.add(Constants.APP_IMG_URL_EQUIPMENT_SERVICE+equipmentListBean.getFieldImg());
            equipmentsnames.add(equipmentListBean.getFieldName());
        }

        for (SpaceDetailBean.ResultsBean.ServiceListBean serviceListBean:
             resultsBean.getServiceList()) {
            servicesImages.add(Constants.APP_IMG_URL_EQUIPMENT_SERVICE+serviceListBean.getFieldImg());
            servicesnames.add(serviceListBean.getFieldName());
        }
        EquipmentGridViewAdapter equipmentGridViewAdapter = new EquipmentGridViewAdapter(mContext, resultsBean.getEquipmentList(), gridviewEquipmentSpaceDetail);
        gridviewEquipmentSpaceDetail.setAdapter(equipmentGridViewAdapter);
        ServiceGridViewAdapter serviceGridViewAdapter = new ServiceGridViewAdapter(mContext, resultsBean.getServiceList(), gridviewServiceSpaceDetail);
        gridviewServiceSpaceDetail.setAdapter(serviceGridViewAdapter);
    }

    private void initViewData(SpaceDetailBean.ResultsBean resultsBean) {
        setBannerImages(resultsBean);//头部banner图片
        setPanorama(resultsBean);
        setaEquipmentServiceList(resultsBean);
        toolbarContentText.setText(resultsBean.getSpaceCnName());
        tvSpaceName.setText(resultsBean.getSpaceCnName());
        tvSpaceAddress.setText(resultsBean.getAddress());
        tvSpaceLocation.setText(resultsBean.getSpaceDistrict() + "－" + resultsBean.getSpaceBusinessArea());
        tvStationNums.setText(resultsBean.getRentNum() + "个工位在租");
        tvSpaceDesc.setText(resultsBean.getSpaceProfile());
        tvSpaceArea.setText(StringUtils.removeZero(resultsBean.getSpaceArea()) + "m²");
        tvSpacePublicRate.setText(resultsBean.getSpaceProportion() + "%");
        tvSpaceLayers.setText(resultsBean.getSpaceFloor() + "层");
        tvSpaceStationArea.setText(StringUtils.removeZero(String.valueOf(resultsBean.getPerStationArea())) + "m²/工位");
        mSpaceNamePinyin = resultsBean.getSpaceNamePinyin();
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
                intent.putExtra(WorkplaceDetailActivity.KEY_BUILD_WORK_PLACE_DETAIL, requestGoWorkPlaceDetailBean(i));
                intent.putExtra(KEY_BUILD_SPACE_DETAIL, requestGoSpaceDetail);
                startActivity(intent);
            }
        });
    }

    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetailBean(int position) {
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
        requestGoWorkPlaceDetail.setWorkplaceDetailId(officeSpaceBasicInfoId);
        return requestGoWorkPlaceDetail;
    }

    private void lookMoreDesc(boolean b) {
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
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                String msgTitle = tvSpaceName.getText().toString();
                String msgText = "http://m.uban.com/" + HeaderConfig.cityShorthand() + "/duanzu/" + mSpaceNamePinyin + ".html";
                shareMsg(mContext, "空间详情分享", msgTitle, msgText);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_detail, menu);
        return true;
    }

    private void callPhone() {
        RxPermissions.getInstance(mContext).request(Manifest.permission.CALL_PHONE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            PhoneUtils.call(mContext, Constants.PHONE_NUMBER);
                        } else {
                            ToastUtil.makeText(mContext, "未授权");
                        }
                    }
                });
    }

    @OnClick({R.id.call_phone, R.id.rl_panorama, R.id.rl_supporting, R.id.tv_look_more_desc,R.id.iv_showEquipmentList, R.id.iv_showServiceList})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_phone:
                callPhone();
                break;
            case R.id.rl_panorama:
                if (panoramaImages.size() > 0) {
                    Intent houseDetailPanorama = new Intent();
                    houseDetailPanorama.setClass(mContext, PanoramaActivity.class);
                    houseDetailPanorama.putExtra(PanoramaActivity.PANORAMA_IMAGE_URL, panoramaImages);
                    houseDetailPanorama.putExtra(PanoramaActivity.PANORAMA_IMAGE_DESC, panoramaDesc);
                    startActivity(houseDetailPanorama);
                } else {
                    ToastUtil.makeText(mContext, "全景拍摄中");
                }
                break;
            case R.id.rl_supporting:
                Intent intent = new Intent();
                intent.setClass(mContext, SupportingActivity.class);
                intent.putExtra(SupportingActivity.KEY_LOCATION_X, locationx);
                intent.putExtra(SupportingActivity.KEY_LOCATION_Y, locationy);
                startActivity(intent);
                break;
            case R.id.tv_look_more_desc:
                lookMoreDesc(isLookMoreDesc);
                break;
            case R.id.iv_showEquipmentList:
                Intent equipmentIntent = new Intent();
                equipmentIntent.setClass(mContext, EquipmentServiceActivity.class);
                equipmentIntent.putExtra(EquipmentServiceActivity.KEY_NAME_LIST,equipmentsnames);
                equipmentIntent.putExtra(EquipmentServiceActivity.KEY_IMAGE_LIST,equipmentsImages);
                startActivity(equipmentIntent);
                break;
            case R.id.iv_showServiceList:
                Intent serviceIntent = new Intent();
                serviceIntent.setClass(mContext, EquipmentServiceActivity.class);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_NAME_LIST,servicesnames);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_IMAGE_LIST,servicesImages);
                startActivity(serviceIntent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
