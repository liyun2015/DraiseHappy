package com.uban.rent.ui.activity.detail;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.baidu.mobstat.StatService;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.ui.activity.components.EquipmentServiceActivity;
import com.uban.rent.ui.activity.components.PanoramaActivity;
import com.uban.rent.ui.activity.components.SupportingActivity;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.EquipmentGridViewAdapter;
import com.uban.rent.ui.adapter.ServiceGridViewAdapter;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.ui.view.banner.LoopViewPager;
import com.uban.rent.ui.view.textview.EllipsizeText;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;
import com.uban.rent.util.StringUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.Log;

import java.lang.ref.WeakReference;
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
    private double locationX = 116.486388;
    private double locationY = 40.000828;
    private SpaceDetailRentTypeAdapter spaceDetailRentTypeAdapter;
    private List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen;
    private RequestGoSpaceDetail requestGoSpaceDetail;
    private int mPriceType = 1;
    private String mSpaceNamePinyin;
    private ArrayList<String> panoramaImages;
    private ArrayList<String> panoramaDesc;
    private ArrayList<String> equipmentsImages;
    private ArrayList<String> equipmentsnames;
    private ArrayList<String> servicesImages;
    private ArrayList<String> servicesnames;
    private CreateOrderParamaBean createOrderParamaBean;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
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
        locationX = requestGoSpaceDetail.getLocationX();
        locationY = requestGoSpaceDetail.getLocationY();
        officeSpaceBasicInfoId = requestGoSpaceDetail.getOfficeSpaceBasicInfoId();
        initSocial();
        initData();
        initView();
    }

    private void initSocial() {
        SHARE_MEDIA[] shareMedias = new SHARE_MEDIA[]{
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL, SHARE_MEDIA.YNOTE,
        };
        mShareListener = new CustomShareListener(SpaceDetailActivity.this);
        mShareAction = new ShareAction(SpaceDetailActivity.this).setDisplayList(shareMedias)
                .setCallback(mShareListener);
    }

    private void initData() {
        RequestSpaceDetail requestSpaceDetail = new RequestSpaceDetail();
        requestSpaceDetail.setOfficespaceBasicinfoId(officeSpaceBasicInfoId);
        requestSpaceDetail.setLocationX(locationX);
        requestSpaceDetail.setLocationY(locationY);
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
                        createOrderParamaBean = new CreateOrderParamaBean();
                        createOrderParamaBean.setPriceType(mPriceType);
                        createOrderParamaBean.setSpaceDeskAddress(resultsBean.getAddress());
                        createOrderParamaBean.setSpaceDeskName(resultsBean.getSpaceCnName());
                        createOrderParamaBean.setSpaceDeskId(resultsBean.getOfficespaceBasicinfoId());
                        spaceDeskTypePriceListBeen.clear();
                        spaceDeskTypePriceListBeen.addAll(resultsBean.getSpaceDeskTypePriceList());
                        spaceDetailRentTypeAdapter = new SpaceDetailRentTypeAdapter(mContext, spaceDeskTypePriceListBeen, createOrderParamaBean);
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
        if (images == null && images.size() <= 0) {
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
        for (SpaceDetailBean.ResultsBean.EquipmentListBean equipmentListBean :
                resultsBean.getEquipmentList()) {
            equipmentsImages.add(Constants.APP_IMG_URL_EQUIPMENT_SERVICE + equipmentListBean.getFieldImg());
            equipmentsnames.add(equipmentListBean.getFieldName());
        }

        for (SpaceDetailBean.ResultsBean.ServiceListBean serviceListBean :
                resultsBean.getServiceList()) {
            servicesImages.add(Constants.APP_IMG_URL_EQUIPMENT_SERVICE + serviceListBean.getFieldImg());
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
                mPriceType = tab.getPosition() + 1;
                spaceDetailRentTypeAdapter.setPriceType(tab.getPosition() + 1);
                if (mPriceType==Constants.RENT_HOUSE){
                    StatService.onEvent(mContext, "SpaceDetail_HourRentClickEvent", "pass", 1);
                }else if (mPriceType==Constants.RENT_DAY){
                    StatService.onEvent(mContext, "SpaceDetail_DayRentClickEvent", "pass", 1);
                }else if (mPriceType==Constants.RENT_MONTH){
                    StatService.onEvent(mContext, "SpaceDetail_MonthRentClickEven", "pass", 1);
                }
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
                StatService.onEvent(mContext, "SpaceDetail_StationClickEvent", "pass", 1);
                Intent intent = new Intent();
                intent.setClass(mContext, StationDetailActivity.class);
                intent.putExtra(StationDetailActivity.KEY_BUILD_WORK_PLACE_DETAIL, requestGoWorkPlaceDetailBean(i));
                intent.putExtra(KEY_BUILD_SPACE_DETAIL, requestGoSpaceDetail);
                startActivity(intent);
            }
        });
    }

    /**
     * 跳转工位详情参数
     *
     * @param position
     * @return
     */
    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetailBean(int position) {
        SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean = spaceDeskTypePriceListBeen.get(position);
        RequestGoWorkPlaceDetail requestGoWorkPlaceDetail = new RequestGoWorkPlaceDetail();
        requestGoWorkPlaceDetail.setPriceType(mPriceType);
        requestGoWorkPlaceDetail.setWorkplaceDetailId(spaceDeskTypePriceListBean.getId());
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
                String shareTitle = tvSpaceName.getText().toString();
                String shareUrl = "http://m.uban.com/"+HeaderConfig.cityShorthand()+"/yidongbangong/"+mSpaceNamePinyin+".html";
                UMImage shareImage = new UMImage(mContext,panoramaImages.get(0));

                ShareBoardConfig config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
                mShareAction.withText("空间详情分享")
                        .withMedia(shareImage)
                        .withTitle(shareTitle)
                        .withTargetUrl(shareUrl)
                        .open(config);

                break;
        }
        return super.onOptionsItemSelected(item);
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

        private WeakReference<SpaceDetailActivity> mActivity;

        private CustomShareListener(SpaceDetailActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtil.makeText(mActivity.get(),"收藏成功啦");
               // Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
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
                   // Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
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
             //   Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                ToastUtil.makeText(mActivity.get(),"分享失败啦");
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

          //  Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
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

    @OnClick({R.id.call_phone, R.id.rl_panorama, R.id.rl_supporting, R.id.tv_look_more_desc, R.id.iv_showEquipmentList, R.id.iv_showServiceList})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_phone:
                StatService.onEvent(mContext, "SpaceDetail_TelephoneBtnEvent", "pass", 1);
                callPhone();
                break;
            case R.id.rl_panorama:
                StatService.onEvent(mContext, "SpaceDetail_PanoBtnClickEvent", "pass", 1);
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
                StatService.onEvent(mContext, "SpaceDetail_RoundBtnClickEvent", "pass", 1);
                Intent intent = new Intent();
                intent.setClass(mContext, SupportingActivity.class);
                intent.putExtra(SupportingActivity.KEY_LOCATION_X, locationX);
                intent.putExtra(SupportingActivity.KEY_LOCATION_Y, locationY);
                startActivity(intent);
                break;
            case R.id.tv_look_more_desc:
                lookMoreDesc(isLookMoreDesc);
                break;
            case R.id.iv_showEquipmentList:
                Intent equipmentIntent = new Intent();
                equipmentIntent.setClass(mContext, EquipmentServiceActivity.class);
                equipmentIntent.putExtra(EquipmentServiceActivity.KEY_NAME_LIST, equipmentsnames);
                equipmentIntent.putExtra(EquipmentServiceActivity.KEY_IMAGE_LIST, equipmentsImages);
                startActivity(equipmentIntent);
                break;
            case R.id.iv_showServiceList:
                Intent serviceIntent = new Intent();
                serviceIntent.setClass(mContext, EquipmentServiceActivity.class);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_NAME_LIST, servicesnames);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_IMAGE_LIST, servicesImages);
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
