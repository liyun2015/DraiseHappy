package com.uban.rent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private boolean isFirstLoc = false; // 是否首次定位
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
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
                // ToastUtil.makeText(mContext,tab.getPosition()+"");
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

    private void isShowBottomView(boolean b) {
        //tabHomeSelect.setAnimation(isShowListView ? AnimationUtils.loadAnimation(mContext, R.anim.dd_menu_out) : AnimationUtils.loadAnimation(mContext, R.anim.dd_menu_in));
        tabHomeSelect.setVisibility(isShowListView ? View.GONE : View.VISIBLE);
        btnCloseList.setVisibility(isShowListView?View.VISIBLE:View.GONE);
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

        switch (id){
            case R.id.nav_member:
                break;
            case R.id.nav_order:
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
                goActivity(WorkplaceDetailActivity.class);
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

    private void showMarkerList(){

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
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    private void goActivity(Class<?> cls){
        startActivity(new Intent(mContext,cls));
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
