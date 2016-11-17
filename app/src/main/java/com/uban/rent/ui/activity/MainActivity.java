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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.HomeDatasBean;
import com.uban.rent.module.request.RequestHomeData;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Bind(R.id.fab_switching)
    FloatingActionButton fabSwitching;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tab_home_select)
    TabLayout tabHomeSelect;
    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.home_list)
    ListView homeList;
    @Bind(R.id.fab_location)
    FloatingActionButton fabLocation;
    @Bind(R.id.btn_close_list)
    TextView btnCloseList;
    @Bind(R.id.lLayout_list_view)
    LinearLayout lLayoutListView;
    private BaiduMap mBaiduMap;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<>(Arrays.asList("nadd", "adf", "faf++", "Rubsdfdsfy", "Jsonfsdf",
            "sdfsdHTML", "nadd", "adf", "faf++", "Rubsdfdsfy", "Jsonfsdf",
            "sdfsdHTML", "nadd", "adf", "faf++", "Rubsdfdsfy", "Jsonfsdf",
            "sdfsdHTML", "nadd", "adf", "faf++", "Rubsdfdsfy", "Jsonfsdf",
            "sdfsdHTML"));
    private boolean isShowListView = true;
    private static final String[] TITLE_NAME = new String[]{"全部", "移动办公", "会议/活动"};
    private static final String KEY_BUNDLE = "Bundle";
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private Marker mMarkerBase;
    private boolean isFirstLoc = true; // 是否首次定位
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initView();
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
                        ToastUtil.makeText(mContext,getString(R.string.str_result_error));
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
        if (shortRentFlag==0){
            resID = R.drawable.ic_marker_space_normal;
        }else if (shortRentFlag==1){
            resID = R.drawable.ic_marker_mobile_office_normal;
        }else if (shortRentFlag==2){
            resID = R.drawable.ic_marker_conference_activities_normal;
        }
        if (datasBean.getShortestFlag()==Constants.SHORT_NEAR_FLAG){
            TextView textView = new TextView(this);
            textView.setText("距离最近");
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12f);
            textView.setPadding(0,0,0,12);
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

            HomeDatasBean.ResultsBean.DatasBean datasBean  = (HomeDatasBean.ResultsBean.DatasBean) bundle.getSerializable(KEY_BUNDLE);
            LatLng latLng = new LatLng(datasBean.getMapY(), datasBean.getMapX());
            MapStatus mMapStatus = new MapStatus.Builder().target(latLng).build();
            MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.animateMapStatus(msu, 400);
            int resID = 0;
            if (shortRentFlag==0){
                resID = R.drawable.ic_marker_space_checked;
            }else if (shortRentFlag==1){
                resID = R.drawable.ic_marker_mobile_office_checked;
            }else if (shortRentFlag==2){
                resID = R.drawable.ic_marker_conference_activities_checked;
            }
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(resID);
            marker.setIcon(bitmapDescriptor);
            mMarkerBase = marker;
            return true;
        }
    };
    public void clearOverlay() {
        mBaiduMap.clear();
        mMarkerBase = null;
    }
    private void isShowBottomView(boolean b) {
        //tabHomeSelect.setAnimation(isShowListView ? AnimationUtils.loadAnimation(mContext, R.anim.dd_menu_out) : AnimationUtils.loadAnimation(mContext, R.anim.dd_menu_in));
        tabHomeSelect.setVisibility(isShowListView ? View.GONE : View.VISIBLE);
        btnCloseList.setVisibility(isShowListView ? View.VISIBLE : View.GONE);
        fabLocation.setVisibility(isShowListView ? View.GONE : View.VISIBLE);
        lLayoutListView.setVisibility(isShowListView ? View.VISIBLE : View.GONE);
        isShowListView = !b;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @OnClick({R.id.fab_switching, R.id.fab_location, R.id.btn_close_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_switching:
                /*mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mDatas);
                homeList.setAdapter(mAdapter);
                isShowBottomView(isShowListView);*/
                goActivity(SpaceDetailActivity.class);
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
                isShowBottomView(isShowListView);
                break;
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
