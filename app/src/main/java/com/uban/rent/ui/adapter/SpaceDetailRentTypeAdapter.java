package com.uban.rent.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.uban.rent.R;
import com.uban.rent.base.UBBaseAdapter;
import com.uban.rent.ui.view.UbanListView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * SpaceDetailRentTypeAdapter
 * Created by dawabos on 2016/11/16.
 * Email dawabo@163.com
 */

public class SpaceDetailRentTypeAdapter extends UBBaseAdapter<String, UbanListView> {
    public SpaceDetailRentTypeAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        String str  = list.get(i);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_space_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind(str);
        return convertView;
    }

    public void changeData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        private void bind(String str){
        }
    }
}
