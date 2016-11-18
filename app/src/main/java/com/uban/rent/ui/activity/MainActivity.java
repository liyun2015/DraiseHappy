package com.uban.rent.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.Events;
import com.uban.rent.control.RxBus;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.control.events.SearchHomeViewEvents;
import com.uban.rent.module.HomeDatasBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.request.RequestHomeData;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.adapter.SpaceDetailRentTypeAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.util.Constants;
import com.uban.rent.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


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
    UbanListView lvMarkerList;
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
    private static final String[] TITLE_NAME = new String[]{"全部", "移动办公", "会议/活动"};
    private static final String[] TITLE_MARKER_NAME = new String[]{"时租", "日租", "月租"};
    private static final String[] TITLE_PRICE_TYPE = new String[]{"元/时 起", "元/天 起", "元/月 起"};
    private static final String KEY_BUNDLE = "Bundle";
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private Marker mMarkerBase;
    private boolean isFirstLoc = true; // 是否首次定位
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    private SpaceDetailRentTypeAdapter spaceDetailRentTypeAdapter;
    private int officeSpaceBasicInfoId;
    List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        spaceDeskTypePriceListBeen = new ArrayList<>();
        registerEvents();
        initData();
        initView();
    }

    private void registerEvents() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_SEARCH_TYPE)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        SearchHomeViewEvents searchHomeViewEvents = events.getContent();
                        keyWord = searchHomeViewEvents.getKeyWords();
                        clearOverlay();
                        initData();
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("EventError",throwable.getMessage());
                    }
                })
                .create();
    }

    private double locationX = 116.486388;
    private double locationY = 40.000828;
    private int shortRentFlag = 0;
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
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error));
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void initView() {
        saveCity(Constants.CITY_ID[0]);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        tabHomeSelect.setTabMode(TabLayout.MODE_FIXED);
        tabHomeSelect.addTab(tabHomeSelect.newTab().setText(TITLE_NAME[0]));
        tabHomeSelect.addTab(tabHomeSelect.newTab().setText(TITLE_NAME[1]));
        tabHomeSelect.addTab(tabHomeSelect.newTab().setText(TITLE_NAME[2]));

        tabHomeSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                clearOverlay();
                shortRentFlag = tab.getPosition();
                initData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tagMarkerView.setTabMode(TabLayout.MODE_FIXED);
        tagMarkerView.addTab(tagMarkerView.newTab().setText(TITLE_MARKER_NAME[0]));
        tagMarkerView.addTab(tagMarkerView.newTab().setText(TITLE_MARKER_NAME[1]));
        tagMarkerView.addTab(tagMarkerView.newTab().setText(TITLE_MARKER_NAME[2]));

        tagMarkerView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tvMarkerPriceType.setText(TITLE_PRICE_TYPE[tab.getPosition()]);
                spaceDetailRentTypeAdapter.setPriceType(tab.getPosition());
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
                SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean = spaceDeskTypePriceListBeen.get(i);
                Intent intent = new Intent();
                intent.setClass(mContext, WorkplaceDetailActivity.class);
                intent.putExtra(WorkplaceDetailActivity.KEY_ID,spaceDeskTypePriceListBean.getId());
                startActivity(intent);
            }
        });
        initMapView();
    }

    private void initMapView() {
        mMapView.showZoomControls(false);
        mMapView.removeViewAt(1);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(false);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    private void showMarkerList(HomeDatasBean.ResultsBean.DatasBean datasBean) {
        int resID = 0;
        if (shortRentFlag == 0) {
            resID = R.drawable.ic_marker_space_normal;
        } else if (shortRentFlag == 1) {
            resID = R.drawable.ic_marker_mobile_office_normal;
        } else if (shortRentFlag == 2) {
            resID = R.drawable.ic_marker_conference_activities_normal;
        }
        if (datasBean.getShortestFlag() == Constants.SHORT_NEAR_FLAG) {
            TextView textView = new TextView(this);
            textView.setText("距离最近");
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12f);
            textView.setPadding(0, 0, 0, 12);
            textView.setBackgroundResource(R.drawable.ic_marker_near_windows);
            LatLng pt = new LatLng(datasBean.getMapY(), datasBean.getMapX());
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            InfoWindow mInfoWindow = new InfoWindow(textView, pt, -120);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
        }
        LatLng latLng = new LatLng(datasBean.getMapY(), datasBean.getMapX());
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(resID);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmap);
        //在地图上添加Marker，并显示
        mMarkerBase = (Marker) mBaiduMap.addOverlay(option);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE, datasBean);
        mMarkerBase.setExtraInfo(bundle);
        mBaiduMap.setOnMarkerClickListener(markerOnclick);
    }

    BaiduMap.OnMarkerClickListener markerOnclick = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            Bundle bundle = marker.getExtraInfo();
            if (bundle == null) {
                return false;
            }

            HomeDatasBean.ResultsBean.DatasBean datasBean = (HomeDatasBean.ResultsBean.DatasBean) bundle.getSerializable(KEY_BUNDLE);
            LatLng latLng = new LatLng(datasBean.getMapY(), datasBean.getMapX());
            MapStatus mMapStatus = new MapStatus.Builder().target(latLng).build();
            MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.animateMapStatus(msu, 400);
            int resID = 0;
            if (shortRentFlag == 0) {
                resID = R.drawable.ic_marker_space_checked;
            } else if (shortRentFlag == 1) {
                resID = R.drawable.ic_marker_mobile_office_checked;
            } else if (shortRentFlag == 2) {
                resID = R.drawable.ic_marker_conference_activities_checked;
            }
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(resID);
            marker.setIcon(bitmapDescriptor);
            mMarkerBase = marker;

            ininMarkerList(datasBean);
            return true;
        }
    };

    private void ininMarkerList(HomeDatasBean.ResultsBean.DatasBean datasBean) {
        RequestSpaceDetail requestHomeMarkerList = new RequestSpaceDetail();
        requestHomeMarkerList.setOfficespaceBasicinfoId(datasBean.getOfficespaceBasicinfoId());
        requestHomeMarkerList.setLocationX(datasBean.getMapX());
        requestHomeMarkerList.setLocationY(datasBean.getMapY());
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
                        officeSpaceBasicInfoId = resultsBean.getOfficespaceBasicinfoId();
                        // ImageLoadUtils.displayHeadIcon(resultsBean.getPicList().get(0).getImgPath(),ivMarkerImages);
                        tvMarkerName.setText(resultsBean.getSpaceCnName());
                        tvMarkerLocation.setText(resultsBean.getAddress());
                        tvMarkerPrice.setText(String.valueOf(resultsBean.getMarketPrice()));
                        tvMarkerGongwei.setText(resultsBean.getRentNum() + "个工位在租");
                        spaceDeskTypePriceListBeen.clear();
                        spaceDeskTypePriceListBeen.addAll(resultsBean.getSpaceDeskTypePriceList());
                        spaceDetailRentTypeAdapter = new SpaceDetailRentTypeAdapter(mContext, spaceDeskTypePriceListBeen);
                        lvMarkerList.setAdapter(spaceDetailRentTypeAdapter);
                        isShowBottomView(true);
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
        fabCleanSearch.setVisibility(b ? View.GONE : View.VISIBLE);
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
        // as you specify ic_member parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
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
                break;
            case R.id.nav_order:
                goActivity(UserOrderActivity.class);
                break;
            case R.id.nav_message:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_setting:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fab_clean_search, R.id.fab_location, R.id.btn_close_list, R.id.select_city_bj, R.id.select_city_sh,R.id.rl_marker_space_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_clean_search:
                keyWord = "";
                initData();
                break;
            case R.id.fab_location:
                isFirstLoc = true;
                mBaiduMap.setMyLocationEnabled(true);
                mCurrentMode = LocationMode.NORMAL;
//                mCurrentMarker = BitmapDescriptorFactory
//                        .fromResource(R.drawable.ic_location_marker);
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker,
                                accuracyCircleFillColor, accuracyCircleStrokeColor));
                break;
            case R.id.btn_close_list:
                isShowBottomView(false);
                break;
            case R.id.select_city_bj:
                saveCity(Constants.CITY_ID[0]);
                break;
            case R.id.select_city_sh:
                saveCity(Constants.CITY_ID[1]);
                break;
            case R.id.rl_marker_space_detail:
                Intent intent = new Intent();
                intent.putExtra(SpaceDetailActivity.OFFICE_SPACE_BASIC_INFO_ID,officeSpaceBasicInfoId);
                intent.putExtra(SpaceDetailActivity.LOCATION_X,locationX);
                intent.putExtra(SpaceDetailActivity.LOCATION_Y,locationY);
                intent.setClass(mContext,SpaceDetailActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 选择保存城市
     *
     * @param cityId
     */
    private void saveCity(String cityId) {
        if (cityId.equals(Constants.CITY_ID[0])) {
            selectCityBj.setTextColor(Color.parseColor("#FF5254"));
            selectCitySh.setTextColor(Color.WHITE);
            SPUtils.put(mContext, Constants.UBAN_CITY, Constants.CITY_ID[0]);
        } else {
            SPUtils.put(mContext, Constants.UBAN_CITY, Constants.CITY_ID[1]);
            selectCitySh.setTextColor(Color.parseColor("#FF5254"));
            selectCityBj.setTextColor(Color.WHITE);
        }
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
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
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                locationY = location.getLatitude();
                locationX = location.getLongitude();
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
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
