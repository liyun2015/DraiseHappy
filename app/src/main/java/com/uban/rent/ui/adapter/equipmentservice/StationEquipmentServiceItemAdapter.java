package com.uban.rent.ui.adapter.equipmentservice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.util.Constants;
import com.uban.rent.util.ImageLoadUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Unan on 2016/12/5.
 */

public class StationEquipmentServiceItemAdapter extends
        RecyclerView.Adapter<StationEquipmentServiceItemAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<WorkplaceDetailBean.ResultsBean.ServiceListBean> mDatas;

    public StationEquipmentServiceItemAdapter(Context context, List<WorkplaceDetailBean.ResultsBean.ServiceListBean> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_detail_service_equipment,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(mDatas.get(i));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_service_equipment_image)
        ImageView ivServiceEquipmentImage;
        @Bind(R.id.tv_service_equipment_text)
        TextView tvServiceEquipmentText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /*  ViewHolder(View view) {
              ButterKnife.bind(this, view);
          }*/
        public void bind(WorkplaceDetailBean.ResultsBean.ServiceListBean serviceListBean) {
            ImageLoadUtils.displayImage(Constants.APP_IMG_URL_EQUIPMENT_SERVICE + serviceListBean.getFieldImg(), ivServiceEquipmentImage);
            tvServiceEquipmentText.setText(serviceListBean.getFieldName());
        }
    }

}


