package com.uban.rent.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.UBBaseAdapter;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.util.Constants;
import com.uban.rent.util.ImageLoadUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SpaceDetailRentTypeAdapter 详情页设备和服务item
 * Created by dawabos on 2016/11/16.
 * Email dawabo@163.com
 */

public class EquipmentGridViewAdapter extends UBBaseAdapter<SpaceDetailBean.ResultsBean.EquipmentListBean, GridView> {

    private GridView gridView;
    public static final int ROW_NUMBER = 1;
    public EquipmentGridViewAdapter(Context context, List<SpaceDetailBean.ResultsBean.EquipmentListBean> list, GridView view) {
        super(context, list, view);
        this.gridView = view;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final SpaceDetailBean.ResultsBean.EquipmentListBean spaceDeskTypePriceListBean = list.get(i);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_detail_service_equipment, null);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridView.getHeight()/ROW_NUMBER);
            convertView.setLayoutParams(param);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.bind(spaceDeskTypePriceListBean);
        return convertView;
    }

    public void changeData(List<SpaceDetailBean.ResultsBean.EquipmentListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.iv_service_equipment_image)
        ImageView ivServiceEquipmentImage;
        @Bind(R.id.tv_service_equipment_text)
        TextView tvServiceEquipmentText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        public void bind(SpaceDetailBean.ResultsBean.EquipmentListBean spaceDeskTypePriceListBean){
            tvServiceEquipmentText.setText(spaceDeskTypePriceListBean.getFieldName());
            ImageLoadUtils.displayImage(Constants.APP_IMG_URL_EQUIPMENT_SERVICE+spaceDeskTypePriceListBean.getFieldImg(),ivServiceEquipmentImage);
        }
    }
}
