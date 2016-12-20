package com.uban.rent.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mobstat.StatService;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.App;
import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.Events;
import com.uban.rent.control.RxBus;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.control.events.SearchHomeViewEvents;
import com.uban.rent.control.events.UserLoginEvents;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.HomeDatasBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestHomeData;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.ui.activity.components.SearchActivity;
import com.uban.rent.ui.activity.detail.SpaceDetailActivity;
import com.uban.rent.ui.activity.detail.StationDetailActivity;
import com.uban.rent.ui.activity.member.MemberFinalActivity;
import com.uban.rent.ui.activity.member.MemberFirstActivity;
import com.uban.rent.ui.activity.order.OrderListActivity;
import com.uban.rent.ui.activity.other.SettingActivity;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.ConvertUtils;
import com.uban.rent.util.ImageLoadUtils;
import com.uban.rent.util.SPUtils;
import com.uban.rent.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.baidu.mapapi.map.MapStatusUpdateFactory.newMapStatus;
import static com.uban.rent.R.id.user_name;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_main)
    LinearLayout contentMain;
    @Bind(R.id.fab_clean_search)
    FloatingActionButton fabCleanSearch;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tab_home_select)
    TabLayout tabHomeSelect;
    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.lv_marker_list)
    ListView lvMarkerList;
    @Bind(R.id.fab_location)
    FloatingActionButton fabLocation;
    @Bind(R.id.btn_close_list)
    TextView btnCloseList;
    @Bind(R.id.lLayout_list_view)
    LinearLayout lLayoutListView;
    @Bind(R.id.select_city_bj)
    TextView selectCityBj;
    @Bind(R.id.select_city_sh)
    TextView selectCitySh;
    @Bind(R.id.iv_marker_images)
    ImageView ivMarkerImages;
    @Bind(R.id.tv_marker_name)
    TextView tvMarkerName;
    @Bind(R.id.tv_marker_location)
    TextView tvMarkerLocation;
    @Bind(R.id.tv_marker_gongwei)
    TextView tvMarkerGongwei;
    @Bind(R.id.tv_marker_price)
    TextView tvMarkerPrice;
    @Bind(R.id.tv_marker_price_type)
    TextView tvMarkerPriceType;
    @Bind(R.id.tag_marker_view)
    TabLayout tagMarkerView;
    @Bind(R.id.rl_marker_space_detail)
    RelativeLayout rlMarkerSpaceDetail;
    private BaiduMap mBaiduMap;
    private static final String[] TITLE_NAME = new String[]{"全部", "工位", "会议/活动"};
    private static final String[] TITLE_MARKER_NAME = new String[]{"时租", "日租", "月租"};
    private static final String[] TITLE_PRICE_TYPE = new String[]{"元/时 起", "元/天 起", "元/月 起"};
    private static final int KEY_ORDER_ALL = 0;
    private static final int KEY_MOBILE_OFFICE = 1;
    private static final int KEY_MEETIONGS_EVENTS = 2;
    private static final String KEY_BUNDLE = "Bundle";
    private LocationClient mLocClient;
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private Marker mMarkerBase;
    private boolean isFirstLoc = true; // 是否首次定位
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    public static final String COLOR_ACCENT = "#FF5254";

    private SpaceDetailRentTypeAdapter spaceDetailRentTypeAdapter;
    private int officeSpaceBasicInfoId;
    private List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen;
    private int mPriceType = 1;
    private CreateOrderParamaBean createOrderParamaBean;
    private boolean mSearchFlag = false;
    private boolean isShowSnackbar = false;
    private String mCityCode = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        spaceDeskTypePriceListBeen = new ArrayList<>();
        initView();
        initMapView();
        registerPermissions();
        registerEvents();
    }

    private void registerPermissions() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showSnackbar(getString(R.string.str_location_off_service));///str_location_off_service));
        }

        //Android 6.0 Permissions
        RxPermissions.getInstance(mContext).request(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            initMapViewLocation();
                       }else {
                            showSnackbar(getString(R.string.str_location_where_unknow));
                        }
                    }
                });
    }

    private void registerEvents() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_SEARCH_TYPE)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        mSearchFlag = true;
                        SearchHomeViewEvents searchHomeViewEvents = events.getContent();
                        keyWord = searchHomeViewEvents.getKeyWords();
                        clearOverlay();
                        initData();
                        fabCleanSearch.setVisibility(View.VISIBLE);
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("searchEventError",throwable.getMessage());
                    }
                })
                .create();

        RxBus.with(this)
                .setEvent(Events.EVENTS_USER_LOGIN)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        UserLoginEvents userLoginEvents = events.getContent();
                        boolean isLogin = userLoginEvents.isLoginIn();
                        initHeadView(isLogin);
                        if (spaceDetailRentTypeAdapter!=null){
                            spaceDetailRentTypeAdapter.setPriceType(mPriceType);//更新会员状态
                        }
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("loginEventError",throwable.getMessage());
                    }
                })
                .create();
    }

    private double locationX = 116.486388;
    private double locationY = 40.000828;
    private int shortRentFlag = KEY_MOBILE_OFFICE;
    private String keyWord = "";

    private void initData() {
        RequestHomeData requestHomeData = new RequestHomeData();
        requestHomeData.setKeyword(keyWord);
        requestHomeData.setLocationX(locationX);
        requestHomeData.setLocationY(locationY);
        requestHomeData.setShortRentFlag(shortRentFlag);
        ServiceFactory.getProvideHttpService().getFindShortRentOfficeSpaces(requestHomeData)
                .compose(this.<HomeDatasBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<HomeDatasBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<HomeDatasBean, Boolean>() {
                    @Override
                    public Boolean call(HomeDatasBean homeDatasBean) {
                        return homeDatasBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<HomeDatasBean, HomeDatasBean.ResultsBean>() {
                    @Override
                    public HomeDatasBean.ResultsBean call(HomeDatasBean homeDatasBean) {
                        return homeDatasBean.getResults();
                    }
                })
                .filter(new Func1<HomeDatasBean.ResultsBean, Boolean>() {
                    @Override
                    public Boolean call(HomeDatasBean.ResultsBean resultsBean) {
                        return resultsBean.getTotals() > 0;
                    }
                })
                .flatMap(new Func1<HomeDatasBean.ResultsBean, Observable<HomeDatasBean.ResultsBean.DatasBean>>() {
                    @Override
                    public Observable<HomeDatasBean.ResultsBean.DatasBean> call(HomeDatasBean.ResultsBean resultsBean) {
                        return Observable.from(resultsBean.getDatas());
                    }
                })
                .subscribe(new Action1<HomeDatasBean.ResultsBean.DatasBean>() {
                    @Override
                    public void call(HomeDatasBean.ResultsBean.DatasBean datasBean) {
                        showMarkerList(datasBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error)+throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    /**
     * 加载头像view
     */
    private void initHeadView(boolean isLogin) {
        View view = navigationView.getHeaderView(0);
        ImageView userHeadImage = (ImageView) view.findViewById(R.id.user_head_image);
        TextView userName = (TextView) view.findViewById(user_name);
        if (isLogin){
            if (TextUtils.isEmpty(HeaderConfig.userHeadImage())){
                userHeadImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_login_head_image));
            }else {
                ImageLoadUtils.displayImage(HeaderConfig.userHeadImage(),userHeadImage);
            }

            userName.setText(HeaderConfig.nickName());
            userHeadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            userHeadImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_login_head_image));
            userName.setText("登录｜注册");
            userHeadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goActivity(LoginActivity.class);
                }
            });
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goActivity(LoginActivity.class);
                }
            });
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        fabCleanSearch.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_home_title_logo);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbarContentText.setCompoundDrawables(null,null,drawable,null);
        toolbarContentText.setText("");

        initHeadView(!HeaderConfig.isEmptyUbanToken());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        tabHomeSelect.setTabMode(TabLayout.MODE_FIXED);
        tabHomeSelect.addTab(tabHomeSelect.newTab().setText(TITLE_NAME[KEY_ORDER_ALL]),false);
        tabHomeSelect.addTab(tabHomeSelect.newTab().setText(TITLE_NAME[KEY_MOBILE_OFFICE]),true);
        tabHomeSelect.addTab(tabHomeSelect.newTab().setText(TITLE_NAME[KEY_MEETIONGS_EVENTS]),false);
        tabHomeSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                clearOverlay();
                shortRentFlag = tab.getPosition();
                if (TextUtils.isEmpty(mCityCode)){//Android 4.4 版本获取不到城市ID,默认城市切换为北京
                    saveCity(Constants.CITY_ID[0]);
                }
                initData();
                if (shortRentFlag==KEY_ORDER_ALL){
                    StatService.onEvent(mContext, "MainMap_ButtonAllEvent", "pass", 1);
                }else if (shortRentFlag==KEY_MOBILE_OFFICE){
                    StatService.onEvent(mContext, "MainMap_ButtonMobileOfficEvent", "pass", 1);
                }else if (shortRentFlag==KEY_MEETIONGS_EVENTS){
                    StatService.onEvent(mContext, "MainMap_ButtonMeetingEvent", "pass", 1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tagMarkerView.setTabMode(TabLayout.MODE_FIXED);
        tagMarkerView.addTab(tagMarkerView.newTab().setText(TITLE_MARKER_NAME[KEY_ORDER_ALL]));
        tagMarkerView.addTab(tagMarkerView.newTab().setText(TITLE_MARKER_NAME[KEY_MOBILE_OFFICE]));
        tagMarkerView.addTab(tagMarkerView.newTab().setText(TITLE_MARKER_NAME[KEY_MEETIONGS_EVENTS]));

        tagMarkerView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPriceType = tab.getPosition()+1;
                spaceDetailRentTypeAdapter.setPriceType(mPriceType);
                if (mPriceType==Constants.RENT_HOUSE){
                    StatService.onEvent(mContext, "MainMap_HourRentClickEvent", "pass", 1);
                }else if (mPriceType==Constants.RENT_DAY){
                    StatService.onEvent(mContext, "MainMap_DayRentClickEvent", "pass", 1);
                }else if (mPriceType==Constants.RENT_MONTH){
                    StatService.onEvent(mContext, "MainMap_MonthRentClickEvent", "pass", 1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        lvMarkerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StatService.onEvent(mContext, "MainMap_StationClickEvent", "pass", 1);
                Intent intent = new Intent();
                intent.setClass(mContext, StationDetailActivity.class);
                intent.putExtra(StationDetailActivity.KEY_BUILD_WORK_PLACE_DETAIL,requestGoWorkPlaceDetailBean(i));
                intent.putExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL,requestGoSpaceDetailBean());
                startActivity(intent);
            }
        });

    }



    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetailBean(int position){
        SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean = spaceDeskTypePriceListBeen.get(position);
        RequestGoWorkPlaceDetail requestGoWorkPlaceDetail = new RequestGoWorkPlaceDetail();
        requestGoWorkPlaceDetail.setWorkplaceDetailId(spaceDeskTypePriceListBean.getId());
        requestGoWorkPlaceDetail.setPriceType(mPriceType);
        return requestGoWorkPlaceDetail;
    }
    private RequestGoSpaceDetail requestGoSpaceDetailBean(){
        RequestGoSpaceDetail requestGoSpaceDetail = new RequestGoSpaceDetail();
        requestGoSpaceDetail.setLocationX(locationX);
        requestGoSpaceDetail.setLocationY(locationY);
        requestGoSpaceDetail.setOfficeSpaceBasicInfoId(officeSpaceBasicInfoId);
        return requestGoSpaceDetail;
    }
    private void initMapView() {
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);
        mMapView.removeViewAt(1);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(false);// 开启定位图层
        mLocClient = new LocationClient(this);// 定位初始化
    }

    private void initMapViewLocation(){
        mLocClient.registerLocationListener(bdLocationListener);//定位监听
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mapLocationView();
    }

    /**
     * 设置地图中心点
     * @param latLng
     */
    private void setMapStatus(LatLng latLng){
        MapStatus mMapStatus = new MapStatus.Builder().target(latLng).build();
        MapStatusUpdate msu = newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(msu);
    }

    private void showMarkerList(HomeDatasBean.ResultsBean.DatasBean datasBean) {
        int resID = KEY_ORDER_ALL;
        if (shortRentFlag == KEY_ORDER_ALL) {
            resID = R.drawable.ic_marker_space_normal;
        } else if (shortRentFlag == KEY_MOBILE_OFFICE) {
            resID = R.drawable.ic_marker_mobile_office_normal;
        } else if (shortRentFlag == KEY_MEETIONGS_EVENTS) {
            resID = R.drawable.ic_marker_conference_activities_normal;
        }
        if (datasBean.getShortestFlag() == Constants.SHORT_NEAR_FLAG) {
            LatLng pt = new LatLng(datasBean.getMapY(), datasBean.getMapX());
            showInfoWindow("距离最近",pt);
        }
        LatLng latLng = new LatLng(datasBean.getMapY(), datasBean.getMapX());
        if (mSearchFlag){
            mSearchFlag = !mSearchFlag;
            // 设置地图中心点
            setMapStatus(latLng);
        }else {
            LatLng mNormalLatLng = new LatLng(locationY, locationX);
            setMapStatus(mNormalLatLng);
        }
        setMarkerView(resID,latLng);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE, datasBean);
        mMarkerBase.setExtraInfo(bundle);
        mBaiduMap.setOnMarkerClickListener(markerOnclick);
    }

    private void showInfoWindow(String content,LatLng pt){
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12f);
        textView.setPadding(0, 0, 0, 12);
        textView.setBackgroundResource(R.drawable.ic_marker_near_windows);
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(textView, pt, ConvertUtils.dp2px(mContext,-40));
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    /**
     * 添加标注点
     * @param resID
     * @param latLng
     */
    private void setMarkerView(int resID, LatLng latLng){
        //构建Marker图标
        View markerView = getLayoutInflater().inflate(R.layout.view_marker_icon, null);//
        markerView.setBackgroundResource(resID);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(markerView);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmapDescriptor);//.alpha(0.9f);//Marker透明度
        //在地图上添加Marker，并显示
        mMarkerBase = (Marker) mBaiduMap.addOverlay(option);
    }

    /**
     * 标记点点击后样式
     * @param resID
     * @param marker
     */
    private void setMarkerViewChecked(int resID, Marker marker){
        View markerView = getLayoutInflater().inflate(R.layout.view_marker_icon, null);//
        markerView.setBackgroundResource(resID);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(markerView);
        marker.setIcon(bitmapDescriptor);
        mMarkerBase = marker;
    }
    BaiduMap.OnMarkerClickListener markerOnclick = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            StatService.onEvent(mContext, "MainMap_AnnotationClickEvent", "pass", 1);
            Bundle bundle = marker.getExtraInfo();
            if (bundle == null) {
                return false;
            }

            //返回点击过后的marker
            int resIDed = KEY_ORDER_ALL;
            if (shortRentFlag == KEY_ORDER_ALL) {
                resIDed = R.drawable.ic_marker_space_normal;
            } else if (shortRentFlag == KEY_MOBILE_OFFICE) {
                resIDed = R.drawable.ic_marker_mobile_office_normal;
            } else if (shortRentFlag == KEY_MEETIONGS_EVENTS) {
                resIDed = R.drawable.ic_marker_conference_activities_normal;
            }
            setMarkerViewChecked(resIDed,mMarkerBase);

            HomeDatasBean.ResultsBean.DatasBean datasBean = (HomeDatasBean.ResultsBean.DatasBean) bundle.getSerializable(KEY_BUNDLE);
            LatLng latLng = new LatLng(datasBean.getMapY(), datasBean.getMapX());
            setMapStatus(latLng);
            int resID = KEY_ORDER_ALL;
            if (shortRentFlag == KEY_ORDER_ALL) {
                resID = R.drawable.ic_marker_space_checked;
            } else if (shortRentFlag == KEY_MOBILE_OFFICE) {
                resID = R.drawable.ic_marker_mobile_office_checked;
            } else if (shortRentFlag == KEY_MEETIONGS_EVENTS) {
                resID = R.drawable.ic_marker_conference_activities_checked;
            }
            setMarkerViewChecked(resID , marker);

            ininMarkerList(datasBean);
            return true;
        }
    };

    private void ininMarkerList(HomeDatasBean.ResultsBean.DatasBean datasBean) {
        RequestSpaceDetail requestHomeMarkerList = new RequestSpaceDetail();
        requestHomeMarkerList.setOfficespaceBasicinfoId(datasBean.getOfficespaceBasicinfoId());
        requestHomeMarkerList.setLocationX(locationX);
        requestHomeMarkerList.setLocationY(locationY);
        requestHomeMarkerList.setShortRentFlag(shortRentFlag);
        ServiceFactory.getProvideHttpService().getOfficeSpaceInfo(requestHomeMarkerList)
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

                        createOrderParamaBean =new CreateOrderParamaBean();
                        createOrderParamaBean.setPriceType(mPriceType);
                        createOrderParamaBean.setSpaceDeskAddress(resultsBean.getAddress());
                        createOrderParamaBean.setSpaceDeskName(resultsBean.getSpaceCnName());
                        createOrderParamaBean.setSpaceDeskId(resultsBean.getOfficespaceBasicinfoId());

                        if (resultsBean.getSpaceDeskTypePriceList().size()>0){
                            loadBottomDataView(resultsBean);//加载弹出列表数据
                        }else {
                            ToastUtil.makeText(mContext,"暂无数据");
                            isShowBottomView(false);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error)+throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void loadBottomDataView(SpaceDetailBean.ResultsBean resultsBean) {
        officeSpaceBasicInfoId = resultsBean.getOfficespaceBasicinfoId();
        if (resultsBean.getPicList()!=null&&resultsBean.getPicList().size()>0){
            String imageUrl = String.format(Constants.APP_IMG_URL_240_180,resultsBean.getPicList().get(0).getImgPath());
            ImageLoadUtils.displayImage(imageUrl,ivMarkerImages);
        }
        tvMarkerName.setText(resultsBean.getSpaceCnName());
        tvMarkerLocation.setText("距当前位置"+(resultsBean.getDistance()>=1000? StringUtils.removeZero(resultsBean.getDistance()/1000)+"km":StringUtils.removeZero(resultsBean.getDistance())+"m"));

        if (resultsBean.getFloorHourPrice()==KEY_ORDER_ALL){
            tvMarkerPrice.setText(String.valueOf(resultsBean.getFloorDayPrice()));
            tvMarkerPriceType.setText(TITLE_PRICE_TYPE[KEY_MOBILE_OFFICE]);
        }
        tvMarkerPrice.setText(String.valueOf(resultsBean.getFloorHourPrice()));

        tvMarkerGongwei.setText(resultsBean.getRentNum() + "个工位在租");
        spaceDeskTypePriceListBeen.clear();
        spaceDeskTypePriceListBeen.addAll(resultsBean.getSpaceDeskTypePriceList());
        if (spaceDetailRentTypeAdapter==null){
            spaceDetailRentTypeAdapter = new SpaceDetailRentTypeAdapter(mContext, spaceDeskTypePriceListBeen,createOrderParamaBean);
            lvMarkerList.setAdapter(spaceDetailRentTypeAdapter);
        }else {
            spaceDetailRentTypeAdapter.setPriceType(mPriceType);
        }
        isShowBottomView(true);
    }

    public void clearOverlay() {
        mBaiduMap.clear();
        mMarkerBase = null;
    }

    /**
     * 显示底部View
     *
     * @param b true显示
     */
    private void isShowBottomView(boolean b) {
        tabHomeSelect.setVisibility(b ? View.GONE : View.VISIBLE);
        btnCloseList.setVisibility(b ? View.VISIBLE : View.GONE);
        fabLocation.setVisibility(b ? View.GONE : View.VISIBLE);
        fabCleanSearch.setVisibility(b ? View.GONE : (TextUtils.isEmpty(keyWord)? View.GONE:View.VISIBLE));
        lLayoutListView.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (lLayoutListView.getVisibility() == View.VISIBLE) {
                isShowBottomView(false);
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify ic_home_meun_member parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                StatService.onEvent(mContext, "MainMap_SearchBtnClickEvent", "pass", 1);
                isShowBottomView(false);
                fabCleanSearch.setVisibility(TextUtils.isEmpty(keyWord)?View.GONE:View.VISIBLE);
                goActivity(SearchActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_member:
                StatService.onEvent(mContext, "LeftMenu_UbanMemberClickEvent", "pass", 1);
                if (HeaderConfig.isEmptyUbanToken()){
                    startActivity(new Intent(mContext, MemberFirstActivity.class));
                }else {
                    BaseActivityMemberStatusGoView();
                }
                break;
            case R.id.nav_order:
                StatService.onEvent(mContext, "LeftMenu_OrderClickEvent", "pass", 1);
                if (HeaderConfig.isEmptyUbanToken()){
                    startActivity(new Intent(mContext, LoginActivity.class));
                }else {
                    goActivity(OrderListActivity.class);
                }
                break;
            case R.id.nav_setting:
                StatService.onEvent(mContext, "LeftMenu_SettingClickEvent", "pass", 1);
                goActivity(SettingActivity.class);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void BaseActivityMemberStatusGoView(){
        int memberStatus = (int) SPUtils.get(App.getInstance(),Constants.USER_MEMBER,Constants.MEMBER_STATUS_NOT);
        if (memberStatus==Constants.MEMBER_STATUS_NOT){
            startActivity(new Intent(mContext, MemberFirstActivity.class));
        }else if (memberStatus==Constants.MEMBER_STATUS_APPLYING){
            startActivity(new Intent(mContext, MemberFinalActivity.class));
        }
    }

    @OnClick({R.id.fab_clean_search, R.id.fab_location, R.id.btn_close_list, R.id.select_city_bj, R.id.select_city_sh,R.id.rl_marker_space_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_clean_search:
                fabCleanSearch.setVisibility(View.GONE);
                keyWord = "";
                initData();
                break;
            case R.id.fab_location:
                isFirstLoc = true;
                mapLocationView();
                break;
            case R.id.btn_close_list:
                isShowBottomView(false);
                break;
            case R.id.select_city_bj:
                StatService.onEvent(mContext, "LeftMenu_CitySwitchClickEvent", "pass", 1);
                isShowBottomView(false);
                clearOverlay();
                keyWord = "";
                initSaveCityBJ();
                break;
            case R.id.select_city_sh:
                StatService.onEvent(mContext, "LeftMenu_CitySwitchClickEvent", "pass", 1);
                isShowBottomView(false);
                clearOverlay();
                keyWord = "";
                initSaveCitySH();
                break;
            case R.id.rl_marker_space_detail:
                Intent intent = new Intent();
                intent.putExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL,requestGoSpaceDetailBean());
                intent.setClass(mContext,SpaceDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initSaveCityBJ(){
        locationX = 116.486388;
        locationY = 40.000828;
        saveCity(Constants.CITY_ID[0]);
        LatLng latLngBJ = new LatLng(locationY,locationX);
        setMapStatus(latLngBJ);
        initData();
    }
    private void initSaveCitySH(){
        locationX = 121.52;
        locationY = 31.23;
        saveCity(Constants.CITY_ID[1]);
        LatLng latLngSH = new LatLng(locationY,locationX);
        setMapStatus(latLngSH);
        initData();
    }

    private void mapLocationView() {
        mBaiduMap.setMyLocationEnabled(true);
        mCurrentMode = LocationMode.NORMAL;
//                mCurrentMarker = BitmapDescriptorFactory
//                        .fromResource(R.drawable.ic_location_marker);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker,
                        accuracyCircleFillColor, accuracyCircleStrokeColor));
    }

    /**
     * 选择保存城市
     *
     * @param cityId
     */
    private void saveCity(String cityId) {
        if (cityId.equals(Constants.CITY_ID[0])) {
            selectCityBj.setTextColor(Color.parseColor(COLOR_ACCENT));
            selectCitySh.setTextColor(Color.WHITE);
            SPUtils.put(mContext, Constants.UBAN_CITY, Constants.CITY_ID[0]);
        } else {
            SPUtils.put(mContext, Constants.UBAN_CITY, Constants.CITY_ID[1]);
            selectCitySh.setTextColor(Color.parseColor(COLOR_ACCENT));
            selectCityBj.setTextColor(Color.WHITE);
        }
    }



    private BDLocationListener bdLocationListener = new BDLocationListener() {


        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            if (location.getLocType() == BDLocation.TypeServerError){
                ToastUtil.makeText(mContext,getString(R.string.str_location_service_error));
            }else if (location.getLocType() == BDLocation.TypeNetWorkException){
                showSnackbar(getString(R.string.str_location_is_network_success));
            }else if (location.getLocType() == BDLocation.TypeCriteriaException){
                showSnackbar("无法获取有效定位，" + getString(R.string.str_location_where_unknow));
            }else if (location.getLocType() == BDLocation.LOCATION_WHERE_UNKNOW){
                showSnackbar(getString(R.string.str_location_where_unknow));
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);//.zoom(18.0f)
                mBaiduMap.animateMapStatus(newMapStatus(builder.build()));
                if (TextUtils.isEmpty(location.getCityCode())){
                    return;
                }
                mCityCode = location.getCityCode();
                locationY = location.getLatitude();
                locationX = location.getLongitude();
                if (mCityCode.equals("131")){//131 北京市  289 上海市
                    initSaveCityBJ();
                } else if (mCityCode.equals("289")){
                    initSaveCitySH();
                }
                SPUtils.put(mContext,Constants.LOCATION_LATITUDE,locationY);
                SPUtils.put(mContext,Constants.LOCATION_LONGITUDE,locationX);
            }
        }
    };

    private void showSnackbar(String msg){
        if (isShowSnackbar){
            return;
        }
        Snackbar.make(mMapView, msg, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action_settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         openGPS();
                    }
               })
                .show();
        isShowSnackbar = !isShowSnackbar;
    }
    /**
     * 打开GPS设置界面
     */
    public void openGPS() {
        final Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        StatService.onPageEnd(mContext,"地图页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        StatService.onPageStart(mContext,"地图页");
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
