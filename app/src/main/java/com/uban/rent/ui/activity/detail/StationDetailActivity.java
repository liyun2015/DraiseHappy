package com.uban.rent.ui.activity.detail;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestWorkplaceDetail;
import com.uban.rent.ui.activity.components.EquipmentServiceActivity;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.ui.activity.member.MemberFinalActivity;
import com.uban.rent.ui.activity.member.MemberFirstActivity;
import com.uban.rent.ui.activity.order.CreateOrdersActivity;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.equipmentservice.StationEquipmentServiceItemAdapter;
import com.uban.rent.ui.adapter.equipmentservice.deprecated.WorkPlaceServiceGradViewAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.banner.LoopViewPager;
import com.uban.rent.util.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * 工位详情页
 */
public class StationDetailActivity extends BaseActivity {
    public static final String KEY_BUILD_WORK_PLACE_DETAIL = "keyWorkplaceDetail";

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    LoopViewPager viewpager;
    @Bind(R.id.image_desc)
    TextView imageDesc;
    @Bind(R.id.activity_workplace)
    RelativeLayout activityWorkplace;
    @Bind(R.id.top_view)
    AppBarLayout topView;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.tv_workplace_name)
    TextView tvWorkplaceName;
    @Bind(R.id.tv_workplace_station)
    TextView tvWorkplaceStation;
    @Bind(R.id.tv_workplace_address)
    TextView tvWorkplaceAddress;
    @Bind(R.id.tv_workplace_desc)
    TextView tvWorkplaceDesc;
    @Bind(R.id.tv_workplace_time)
    TextView tvWorkplaceTime;
    @Bind(R.id.tv_workplace_notice)
    TextView tvWorkplaceNotice;
    @Bind(R.id.rl_go_space_detail)
    RelativeLayout rlGoSpaceDetail;
    @Bind(R.id.tv_price_type)
    TextView tvPriceType;
    @Bind(R.id.gridview_work_place_detail)
    GridView gridviewWorkPlaceDetail;
    @Bind(R.id.iv_show_equipment_service_list)
    ImageView ivShowEquipmentServiceList;
    private RequestGoSpaceDetail requestGoSpaceDetail;
    private int mWorkPlaceId;
    private int mPrice;
    private int mPriceType;
    private int wordDeskType;
    private static final String[] TITLE_PRICE_TYPE = new String[]{"","元/时 (时租)", "元/天 (日租)", "元/月 (月租)"};
    private ArrayList<String> equipmentServicesImages;
    private ArrayList<String> equipmentServicesnames;
    private CreateOrderParamaBean createOrderParamaBean;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_workplace_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        equipmentServicesImages = new ArrayList<>();
        equipmentServicesnames = new ArrayList<>();
        initView();
        initSocial();
        registerScheme();
    }

    private void initSocial() {

        SHARE_MEDIA[] shareMedias = new SHARE_MEDIA[]{
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL,
        };
        mShareListener = new CustomShareListener(StationDetailActivity.this);
        mShareAction = new ShareAction(StationDetailActivity.this).setDisplayList(shareMedias)
                .setCallback(mShareListener);
    }

    private void registerScheme() {
        Intent intent = getIntent();
            Uri uri = intent.getData();
            if (uri != null) {
                String query = uri.getQuery();
            /**
             *     private int spaceDetailId;
             private double locationX;
             private double locationY;
             //工位
             private int stationDetailId;
             //订单
             private int orderPriceType;
             private int orderPrice;
             */
            String stationDetailId = uri.getQueryParameter("stationDetailId");
            String spaceDetailId = uri.getQueryParameter("spaceDetailId");
            String orderPriceType = uri.getQueryParameter("orderPriceType");
            String locationX = uri.getQueryParameter("locationX");
            String locationY = uri.getQueryParameter("locationY");

            // 完整的url信息
            String url = uri.toString();
            Log.e(TAG, "url: " + uri);
            // scheme部分
            String scheme = uri.getScheme();
            Log.e(TAG, "scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            Log.e(TAG, "host: " + host);
            //port部分
            int port = uri.getPort();
            Log.e(TAG, "host: " + port);
            // 访问路劲
            String path = uri.getPath();
            Log.e(TAG, "path: " + path);

            Log.e(TAG, "query: " + query);

            RequestGoSpaceDetail requestGoSpaceDetailScheme = new RequestGoSpaceDetail();
            requestGoSpaceDetailScheme.setOfficeSpaceBasicInfoId(Integer.parseInt(spaceDetailId));
            requestGoSpaceDetailScheme.setLocationY(Double.parseDouble(locationY));
            requestGoSpaceDetailScheme.setLocationX(Double.parseDouble(locationX));
            requestGoSpaceDetail = requestGoSpaceDetailScheme;

            mWorkPlaceId = Integer.parseInt(stationDetailId);
            mPriceType = Integer.parseInt(orderPriceType);
        }else {
            requestGoSpaceDetail = (RequestGoSpaceDetail) getIntent().getSerializableExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL);
            RequestGoWorkPlaceDetail requestGoWorkPlaceDetail = (RequestGoWorkPlaceDetail) getIntent().getSerializableExtra(KEY_BUILD_WORK_PLACE_DETAIL);
            mWorkPlaceId = requestGoWorkPlaceDetail.getWorkplaceDetailId();
            mPriceType = requestGoWorkPlaceDetail.getPriceType();
        }
        initData();
    }

    private void initData() {
        RequestWorkplaceDetail requestWorkplaceDetail = new RequestWorkplaceDetail();
        requestWorkplaceDetail.setOfficespaceWorkdeskinfoId(mWorkPlaceId);
        ServiceFactory.getProvideHttpService().getOfficespaceWorkdeskInfo(requestWorkplaceDetail)
                .compose(this.<WorkplaceDetailBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<WorkplaceDetailBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<WorkplaceDetailBean, Boolean>() {
                    @Override
                    public Boolean call(WorkplaceDetailBean workplaceDetailBean) {
                        return workplaceDetailBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<WorkplaceDetailBean, WorkplaceDetailBean.ResultsBean>() {
                    @Override
                    public WorkplaceDetailBean.ResultsBean call(WorkplaceDetailBean workplaceDetailBean) {
                        return workplaceDetailBean.getResults();
                    }
                })
                .subscribe(new Action1<WorkplaceDetailBean.ResultsBean>() {
                    @Override
                    public void call(WorkplaceDetailBean.ResultsBean resultsBean) {
                        initDataView(resultsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error) + throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void setBannerImags(WorkplaceDetailBean.ResultsBean resultsBean) {
        viewpager.setBackgroundResource(R.drawable.ic_normal);
        imageDesc.setVisibility(View.GONE);
        List<String> images = new ArrayList<>();
        for (WorkplaceDetailBean.ResultsBean.PicListBean picListBean :
                resultsBean.getPicList()) {
            images.add(String.format(Constants.APP_IMG_URL_640_420, picListBean.getImgPath()));
        }
        if (images == null || images.size() <= 0) {
            return;
        }
        imageDesc.setVisibility(View.VISIBLE);
        BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(mContext);
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

    private void setaEquipmentServiceList(WorkplaceDetailBean.ResultsBean resultsBean) {
        for (WorkplaceDetailBean.ResultsBean.ServiceListBean serviceListBean :
                resultsBean.getServiceList()) {
            equipmentServicesnames.add(serviceListBean.getFieldName());
            equipmentServicesImages.add(Constants.APP_IMG_URL_EQUIPMENT_SERVICE + serviceListBean.getFieldImg());
        }
        //暂不使用
        gridviewWorkPlaceDetail.setAdapter(new WorkPlaceServiceGradViewAdapter(mContext, resultsBean.getServiceList(), gridviewWorkPlaceDetail));

        //设备和服务
        RecyclerView recycle_view_station_service_equipment = (RecyclerView) findViewById(R.id.recycle_view_station_service_equipment);
        StationEquipmentServiceItemAdapter spaceEquipmentItemAdapter = new StationEquipmentServiceItemAdapter(mContext,resultsBean.getServiceList());
        setLinearLayoutManager(recycle_view_station_service_equipment);
        recycle_view_station_service_equipment.setAdapter(spaceEquipmentItemAdapter);
    }

    private void setLinearLayoutManager(RecyclerView recyclerView){
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    private void initDataView(WorkplaceDetailBean.ResultsBean resultsBean) {
        setaEquipmentServiceList(resultsBean);
        setBannerImags(resultsBean);

        toolbarContentText.setText(resultsBean.getSpaceCnName());
        tvWorkplaceName.setText(resultsBean.getSpaceCnName());
        tvWorkplaceStation.setText(String.valueOf(resultsBean.getRentNum()));
        tvWorkplaceAddress.setText(resultsBean.getAddress());
        tvWorkplaceDesc.setText(resultsBean.getRentDesc());
        tvWorkplaceTime.setText(resultsBean.getWorkTime());
        tvWorkplaceNotice.setText(resultsBean.getBuyDesc());
        wordDeskType = resultsBean.getWorkDeskType();
        if (resultsBean.getWorkDeskType()==Constants.HOT_DESK_TYPE){
            tvPriceType.setVisibility(View.GONE);
            tvPrice.setText("会员免费");
            orderCreate.setText(HeaderConfig.isMemberStatus()?"我的会员":"成为会员");
        }else{
            tvPriceType.setVisibility(View.VISIBLE);
            tvPriceType.setText(TITLE_PRICE_TYPE[mPriceType]);
            if (mPriceType==Constants.RENT_HOUSE){
                mPrice = resultsBean.getHourPrice();
            }else if (mPriceType==Constants.RENT_DAY){
                mPrice = resultsBean.getDayPrice();
            }else if (mPriceType==Constants.RENT_MONTH){
                mPrice = resultsBean.getWorkDeskPrice();
            }
            tvPrice.setText(String.valueOf(mPrice));
        }

        createOrderParamaBean = new CreateOrderParamaBean();
        createOrderParamaBean.setSpaceDeskName(resultsBean.getSpaceCnName());
        createOrderParamaBean.setSpaceDeskAddress(resultsBean.getAddress());
        createOrderParamaBean.setSpaceDeskId(resultsBean.getSpaceId());
        createOrderParamaBean.setPriceType(mPriceType);
        createOrderParamaBean.setPrice(mPrice);
        createOrderParamaBean.setWorkDeskId(mWorkPlaceId);
        createOrderParamaBean.setWorkHoursBegin(resultsBean.getWorkHoursBegin());
        createOrderParamaBean.setWorkHoursEnd(resultsBean.getWorkHoursEnd());
        createOrderParamaBean.setWorkDeskType(resultsBean.getWorkDeskType());
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("工位详情");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify ic_home_meun_member parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                String shareTitle = "何处办公？随时随地！";
                String shareMsg  = "在线预定短租工位、会议室，让工作更轻松！下载优办移动办公！";
                String shareUrl = "http://m.uban.com/"+HeaderConfig.cityShorthand()+"/yidongbangong/gongwei-"+mWorkPlaceId+".html?orderType="+mPriceType;
                ShareBoardConfig config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
                mShareAction.withTitle(shareTitle)
                        .withText(shareMsg)
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

        private WeakReference<StationDetailActivity> mActivity;

        private CustomShareListener(StationDetailActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
               // Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
                ToastUtil.makeText(mActivity.get(),"收藏成功啦");
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
                    //Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                ToastUtil.makeText(mActivity.get(),"分享失败啦");
                if (t != null) {
                    com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

           // Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.rl_go_space_detail, R.id.iv_show_equipment_service_list,R.id.order_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_go_space_detail:
                Intent intent = new Intent();
                intent.setClass(mContext, SpaceDetailActivity.class);
                intent.putExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL, requestGoSpaceDetail);
                startActivity(intent);
                break;
            case R.id.iv_show_equipment_service_list:
                Intent serviceIntent = new Intent();
                serviceIntent.setClass(mContext, EquipmentServiceActivity.class);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_NAME_LIST, equipmentServicesnames);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_IMAGE_LIST, equipmentServicesImages);
                startActivity(serviceIntent);
                break;
            case R.id.order_create:
                if (HeaderConfig.isEmptyUbanToken()){
                    startActivity(new Intent(mContext, LoginActivity.class));
                }else{
                    if (wordDeskType==Constants.HOT_DESK_TYPE){
                        StatService.onEvent(mContext, "StationDetail_ToBeMemberEvent", "pass", 1);
                        if (HeaderConfig.isMemberStatus()){
                            startActivity(new Intent(mContext,MemberFinalActivity.class));
                        }else {
                            startActivity(new Intent(mContext,MemberFirstActivity.class));
                        }
                    }else {
                        StatService.onEvent(mContext, "StationDetail_OrderBtnEvent", "pass", 1);
                        Intent orderIntent = new Intent();
                        orderIntent.setClass(mContext,CreateOrdersActivity.class);
                        orderIntent.putExtra(CreateOrdersActivity.KEY_CREATE_ORDER_PARAME_BEAN,createOrderParamaBean);
                        startActivity(orderIntent);
                    }
                }
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"工位详情页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"工位详情页");
    }
}
