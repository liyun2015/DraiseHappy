package com.or.goodlive.ui.activity.components;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.or.goodlive.R;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.ui.view.overlayutil.BikingRouteOverlay;
import com.or.goodlive.ui.view.overlayutil.DrivingRouteOverlay;
import com.or.goodlive.ui.view.overlayutil.MassTransitRouteOverlay;
import com.or.goodlive.ui.view.overlayutil.OverlayManager;
import com.or.goodlive.ui.view.overlayutil.PoiOverlay;
import com.or.goodlive.ui.view.overlayutil.TransitRouteOverlay;
import com.or.goodlive.ui.view.overlayutil.WalkingRouteOverlay;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.ConvertUtils;
import com.or.goodlive.util.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;


public class SupportingActivity extends BaseActivity implements BaiduMap.OnMapClickListener,
        OnGetRoutePlanResultListener {
    public static final String KEY_LOCATION_X = "Location_x";
    public static final String KEY_LOCATION_Y = "Location_y";
    public static final String KEY_VIEW_TYPE = "view_type";//区分空间详情或工位详情进入 0，空间 1，工位
    public static final String KEY_ADDRESS = "key_address";
    public static final String KEY_NAME = "key_name";
    public static final int KEY_VALUE_SPACE = 0;
    public static final int KEY_VALUE_WORK = 1;

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.activity_supporting)
    LinearLayout activitySupporting;
    @Bind(R.id.rgMainGroup)
    RadioGroup mainRg;
    @Bind(R.id.rb_supporting_drive)
    RadioButton rbSupportingDrive;
    @Bind(R.id.rb_supporting_bus)
    RadioButton rbSupportingBus;
    @Bind(R.id.rb_supporting_walk)
    RadioButton rbSupportingWalk;
    @Bind(R.id.rg_supporting_title_view)
    RadioGroup rgSupportingTitleView;
    private BaiduMap mBaiduMap;
    private PoiSearch mPoiSearch = null;
    private int curCheckId;
    private Marker mMarker;
    private static final String[] KEYWORD_TYPE = new String[]{"地铁", "公交", "餐厅", "银行", "酒店"};
    private LatLng point;
    public static final int REMOVE_MAP_LOGO = 1;//去除地图logo
    private int viewType;
    // 搜索相关
    RoutePlanSearch mSearch = null;
    MassTransitRouteResult nowResultmass = null;
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RouteLine route = null;
    MassTransitRouteLine massroute = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    int nowSearchType = -1 ; // 当前进行的检索，供判断浏览节点时结果使用。
    private int mRouteSearchViewId = 2131558676 ;
    private String mAddress;
    private String mName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_supporting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        double locationX = getIntent().getDoubleExtra(KEY_LOCATION_X, 0.0);
        double locationY = getIntent().getDoubleExtra(KEY_LOCATION_Y, 0.0);
        viewType = getIntent().getIntExtra(KEY_VIEW_TYPE, KEY_VALUE_WORK);
        mAddress = getIntent().getStringExtra(KEY_ADDRESS);
        mName = getIntent().getStringExtra(KEY_NAME);
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
            toolbarContentText.setText(viewType == KEY_VALUE_SPACE ? "周边配套" : "");
        }

        if (viewType==KEY_VALUE_SPACE){
            rgSupportingTitleView.setVisibility(View.GONE);
            mainRg.setVisibility(View.VISIBLE);
            toolbarContentText.setText("周边配套");
        }else {
            rgSupportingTitleView.setVisibility(View.VISIBLE);
            mainRg.setVisibility(View.GONE);
            toolbarContentText.setText("");
        }

        mMapView.showZoomControls(false);
        mMapView.removeViewAt(REMOVE_MAP_LOGO);
        mBaiduMap = mMapView.getMap();
        mMapView.showScaleControl(false);
        mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
        pathOverlay();

    }
    private void routePlanSearch() {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_marker);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        mBaiduMap.addOverlay(option);
        mBaiduMap.setOnMapClickListener(this);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        View markerView = getLayoutInflater().inflate(R.layout.view_supporting_map_info_window, null);//
        TextView first = (TextView)markerView.findViewById(R.id.tv_support_info_window_first);
        TextView second = (TextView)markerView.findViewById(R.id.tv_support_info_window_second);
        first.setText(mName);
        second.setText(mAddress);
        markerView.findViewById(R.id.supporting_info_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButtonProcess(mRouteSearchViewId);
            }
        });

        InfoWindow mInfoWindow = new InfoWindow(markerView, point, ConvertUtils.dp2px(mContext, -40));
        mBaiduMap.showInfoWindow(mInfoWindow);

    }
    private void pathOverlay() {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_marker);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        if (viewType==KEY_VALUE_SPACE){
            // 绘制图层
            OverlayOptions circleOptions = new CircleOptions().center(point).radius(1500).fillColor(0x99CBCAE0);
            mBaiduMap.addOverlay(circleOptions);

        }else {
            routePlanSearch();
        }
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
                    case R.id.btn_baidu_map_subway:
                        curCheckId = checkedId;
                        showPoiMarkerView(0);
                        break;
                    case R.id.btn_baidu_map_bus:
                        curCheckId = checkedId;
                        showPoiMarkerView(1);
                        break;
                    case R.id.btn_baidu_map_restaurant:
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

                    default:
                        break;
                }
            }
        });
        mainRg.check(curCheckId);
    }

    private void showPoiMarkerView(final int position) {
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
                    overlay.setData(poiResult, getResID(position), mContext);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                // 获取Place详情页检索结果
                StringBuilder sb = new StringBuilder(poiDetailResult.getName());
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
                    InfoWindow mInfoWindow = new InfoWindow(textView, pt, ConvertUtils.dp2px(mContext, -40));
                    //显示InfoWindow
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }
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
    /**
     * View markerView = getLayoutInflater().inflate(R.layout.view_marker_icon, null);//
     * markerView.setBackgroundResource(iconId);
     * BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(markerView);
     * markerList.add(new MarkerOptions().icon(bitmap).extraInfo(bundle).position(mPoiResult.getAllPoi().get(i).location));
     *
     * @param position
     * @return
     */
    private int getResID(int position) {
        int resID = 0;
        if (position == 0) {
            resID = R.drawable.ic_supporting_marker_subway;
        } else if (position == 1) {
            resID = R.drawable.ic_supporting_marker_bus;
        } else if (position == 2) {
            resID = R.drawable.ic_supporting_marker_dining;
        } else if (position == 3) {
            resID = R.drawable.ic_supporting_marker_bank;
        } else if (position == 4) {
            resID = R.drawable.ic_supporting_marker_hotel;
        }
        return resID;
    }

    BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            mMarker = marker;
            return true;
        }
    };


    @OnClick({R.id.rb_supporting_drive, R.id.rb_supporting_bus, R.id.rb_supporting_walk, R.id.rg_supporting_title_view})
    public void onClick(View view) {
        mRouteSearchViewId = view.getId();
    }

    public void searchButtonProcess(int curCheckId) {

        // 重置浏览节点的路线数据
        route = null;
        mBaiduMap.clear();
        routePlanSearch();
        String locationY = (String) SPUtils.get(mContext, Constants.LOCATION_LATITUDE,"0.0");
        String locationX = (String) SPUtils.get(mContext, Constants.LOCATION_LONGITUDE,"0.0");
        LatLng latLng = new LatLng(Double.parseDouble(locationY),Double.parseDouble(locationX));
        PlanNode stNode = PlanNode.withLocation(latLng);
        PlanNode enNode = PlanNode.withLocation(point);
        if (curCheckId == R.id.rb_supporting_drive) {
            mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode).to(enNode));
            nowSearchType = 1;
        } else if (curCheckId == R.id.rb_supporting_bus) {
            mSearch.transitSearch((new TransitRoutePlanOption())
                    .from(stNode).city("北京").to(enNode));
            nowSearchType = 2;
        } else if (curCheckId == R.id.rb_supporting_walk) {
            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(stNode).to(enNode));
            nowSearchType = 3;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
       // mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtil.makeText(mContext,"抱歉，未找到结果");
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            if ( walkingRouteResult.getRouteLines().size() >= 1 ) {
                // 直接显示
                route = walkingRouteResult.getRouteLines().get(0);
                WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(walkingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            } else {
                ToastUtil.makeText(mContext,"抱歉，暂无数据");
                Log.d("route result", "结果数<0" );
                return;
            }

        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtil.makeText(mContext,"抱歉，未找到结果");
        }
        if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
           if ( transitRouteResult.getRouteLines().size() >= 1 ) {
                // 直接显示
                route = transitRouteResult.getRouteLines().get(0);
                TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(transitRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            } else {
               ToastUtil.makeText(mContext,"抱歉，暂无数据");
                Log.d("route result", "结果数<0" );
                return;
            }

        }

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        if (massTransitRouteResult == null || massTransitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtil.makeText(mContext,"抱歉，未找到结果");
        }
        if (massTransitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点模糊，获取建议列表
            massTransitRouteResult.getSuggestAddrInfo();
            return;
        }
        if (massTransitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nowResultmass = massTransitRouteResult;

            nodeIndex = -1;

            MyMassTransitRouteOverlay overlay = new MyMassTransitRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            massroute = nowResultmass.getRouteLines().get(0);
            overlay.setData(nowResultmass.getRouteLines().get(0));

            MassTransitRouteLine line = nowResultmass.getRouteLines().get(0);
            overlay.setData( line );
            if ( nowResultmass.getOrigin().getCityId() == nowResultmass.getDestination().getCityId() ) {
                // 同城
                overlay.setSameCity(true);
            } else {
                // 跨城
                overlay.setSameCity(false);

            }
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtil.makeText(mContext,"抱歉，未找到结果");
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;


           if ( drivingRouteResult.getRouteLines().size() >= 1 ) {
                route = drivingRouteResult.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
                routeOverlay = overlay;
               mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            } else {
               ToastUtil.makeText(mContext,"抱歉，暂无数据");
                Log.d("route result", "结果数<0" );
                return;
            }

        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtil.makeText(mContext,"抱歉，未找到结果");
        }
        if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            if ( bikingRouteResult.getRouteLines().size() >= 1 ) {
                route = bikingRouteResult.getRouteLines().get(0);
                BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaiduMap);
                routeOverlay = overlay;
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(bikingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            } else {
                Log.d("route result", "结果数<0" );
                return;
            }

        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }
    private class MyMassTransitRouteOverlay extends MassTransitRouteOverlay {
        public  MyMassTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyBikingRouteOverlay extends BikingRouteOverlay {
        public  MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
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
        if (mSearch != null) {
            mSearch.destroy();
        }
        mMapView.onDestroy();
        super.onDestroy();
    }
}
