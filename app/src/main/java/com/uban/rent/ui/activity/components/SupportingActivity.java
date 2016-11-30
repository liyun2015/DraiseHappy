package com.uban.rent.ui.activity.components;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.view.overlayutil.PoiOverlay;
import com.uban.rent.util.ConvertUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SupportingActivity extends BaseActivity {
    public static final String KEY_LOCATION_X = "Location_x";
    public static final String KEY_LOCATION_Y = "Location_y";

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.activity_supporting)
    LinearLayout activitySupporting;
    @Bind(R.id.mainRg)
    RadioGroup mainRg;
    private BaiduMap mBaiduMap;
    private PoiSearch mPoiSearch = null;
    private int curCheckId;
    private Marker mMarker;
    private static final String[] KEYWORD_TYPE = new String[]{"公交", "地铁", "健身房", "银行", "酒店", "餐厅"};
    private LatLng point;
    public static final int REMOVE_MAP_LOGO = 1;//去除地图logo
    @Override
    protected int getLayoutId() {
        return R.layout.activity_supporting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        double locationX = getIntent().getDoubleExtra(KEY_LOCATION_X, 0.0);
        double locationY = getIntent().getDoubleExtra(KEY_LOCATION_Y, 0.0);
        point = new LatLng(locationY, locationX);
        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            toolbarContentText.setText("周边配套");
        }

        mMapView.showZoomControls(false);
        mMapView.removeViewAt(REMOVE_MAP_LOGO);
        mBaiduMap = mMapView.getMap();
        mMapView.showScaleControl(false);
        mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
        pathOverlay();
    }

    private void pathOverlay() {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_poi_support);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        // 绘制图层
        OverlayOptions circleOptions = new CircleOptions().center(point).radius(1500).fillColor(0x99CBCAE0);
        mBaiduMap.addOverlay(circleOptions);
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15f).target(point).build();
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(msu, 400);
    }

    private void initData() {

        mPoiSearch = PoiSearch.newInstance();
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_baidu_map_bus:
                        curCheckId = checkedId;
                        showPoiMarkerView(0);
                        break;
                    case R.id.btn_baidu_map_subway:
                        curCheckId = checkedId;
                        showPoiMarkerView(1);
                        break;
                    case R.id.btn_baidu_map_gym:
                        curCheckId = checkedId;
                        showPoiMarkerView(2);
                        break;
                    case R.id.btn_baidu_map_bank:
                        curCheckId = checkedId;
                        showPoiMarkerView(3);
                        break;
                    case R.id.btn_baidu_map_hotel:
                        curCheckId = checkedId;
                        showPoiMarkerView(4);
                        break;
                    case R.id.btn_baidu_map_restaurant:
                        curCheckId = checkedId;
                        showPoiMarkerView(5);
                        break;
                    default:
                        break;
                }
            }
        });
        mainRg.check(curCheckId);
    }

    private void showPoiMarkerView(int position) {
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword(KEYWORD_TYPE[position]).location(point).radius(1500));
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                // 获取POI检索结果
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    return;
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                    mBaiduMap.clear();
                    pathOverlay();
                    PoiOverlay overlay = new NearPoiOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(poiResult, R.drawable.ic_location_marker);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                // 获取Place详情页检索结果
                String poiName = poiDetailResult.getName();
                StringBuilder sb = new StringBuilder(poiName).append(poiDetailResult.getAddress());
                if (poiDetailResult.error == SearchResult.ERRORNO.NO_ERROR) {// 正常返回结果的时候，此处可以获得很多相关信息
                    TextView textView = new TextView(mContext);
                    textView.setText(sb.toString());
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(12f);
                    textView.setPadding(10, 0, 10, 12);
                    textView.setBackgroundResource(R.drawable.ic_marker_near_windows);
                    LatLng pt = mMarker.getPosition();
                    //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                    InfoWindow mInfoWindow = new InfoWindow(textView, pt, ConvertUtils.dp2px(mContext,-40));
                    //显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            mMarker = marker;
            return true;
        }
    };

    private class NearPoiOverlay extends PoiOverlay {

        public NearPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
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
