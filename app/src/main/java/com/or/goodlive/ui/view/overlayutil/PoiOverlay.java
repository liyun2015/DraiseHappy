package com.or.goodlive.ui.view.overlayutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.poi.PoiResult;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.or.goodlive.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于显示poi的overly
 */
public class PoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiResult mPoiResult = null;
    private int iconId;
    private Context context;
    private RxAppCompatActivity inflate;
    /**
     * 构造函数
     * 
     * @param baiduMap
     *            该 PoiOverlay 引用的 BaiduMap 对象
     */
    public PoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    /**
     * 设置POI数据
     * 
     * @param poiResult
     *            设置POI数据
     */
    public void setData(PoiResult poiResult, int id,Context context) {
        this.mPoiResult = poiResult;
        this.iconId = id;
        this.context = context;
        this.inflate = (RxAppCompatActivity)context;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
            return null;
        }
        List<OverlayOptions> markerList = new ArrayList<OverlayOptions>();
        int markerSize = 0;
        for (int i = 0; i < mPoiResult.getAllPoi().size()
                && markerSize < MAX_POI_SIZE; i++) {
            if (mPoiResult.getAllPoi().get(i).location == null) {
                continue;
            }
            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            //BitmapDescriptor bitmap = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + markerSize + ".png");
            //BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(iconId);

            View markerView = inflate.getLayoutInflater().inflate(R.layout.view_marker_icon, null);//
            markerView.setBackgroundResource(iconId);
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(markerView);
            markerList.add(new MarkerOptions().icon(bitmap).extraInfo(bundle).position(mPoiResult.getAllPoi().get(i).location));
            
        }
        return markerList;
    }

    /**
     * 获取该 PoiOverlay 的 poi数据
     * 
     * @return
     */
    public PoiResult getPoiResult() {
        return mPoiResult;
    }

    /**
     * 覆写此方法以改变默认点击行为
     * 
     * @param i
     *            被点击的poi在
     *            {@link PoiResult#getAllPoi()} 中的索引
     * @return
     */
    public boolean onPoiClick(int i) {
//        if (mPoiResult.getAllPoi() != null
//                && mPoiResult.getAllPoi().get(i) != null) {
//            Toast.makeText(BMapManager.getInstance().getContext(),
//                    mPoiResult.getAllPoi().get(i).name, Toast.LENGTH_LONG)
//                    .show();
//        }
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }
        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker.getExtraInfo().getInt("index"));
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}
