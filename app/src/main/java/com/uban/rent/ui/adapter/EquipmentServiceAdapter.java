package com.uban.rent.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.UBBaseAdapter;
import com.uban.rent.util.ImageLoadUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * EquipmentServiceAdapter 设备服务列表item
 * Created by dawabos on 2016/11/19.
 * Email dawabo@163.com
 */

public class EquipmentServiceAdapter extends UBBaseAdapter<String, ListView> {
    private List<String> listNames;
    private List<String> listImages;
    public EquipmentServiceAdapter(Context context, List<String> listNames,List<String> listImages) {
        super(context, listNames);
        this.listNames = listNames;
        this.listImages = listImages;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        String name = listNames.get(i);
        String image = listImages.get(i);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_equipment_service, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvServiceEquipmentText.setText(name);
        ImageLoadUtils.displayImage(image,holder.ivServiceEquipmentImage);
        return convertView;
    }

    public void changeData(List<String> list) {
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
    }
}
