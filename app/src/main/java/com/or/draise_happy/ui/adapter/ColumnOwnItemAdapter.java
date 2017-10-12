package com.or.draise_happy.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.or.draise_happy.R;
import com.or.draise_happy.base.UBBaseAdapter;
import com.or.draise_happy.module.ColumnBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ColumnOwnItemAdapter extends UBBaseAdapter<ColumnBean, GridView> {

    private List<ColumnBean> list;
    private Context mContext;

    public ColumnOwnItemAdapter(Context context, List<ColumnBean> list) {
        super(context, list);
        this.list = list;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ColumnOwnItemAdapter.Holder holder = null;
        if (convertView == null) {
            holder = new ColumnOwnItemAdapter.Holder();
            convertView = View.inflate(mContext, R.layout.item_own_choose_view, null);
            holder.tv_subscribe_str = (TextView) convertView.findViewById(R.id.tv_subscribe_str);
            holder.subscribe_layout = (LinearLayout) convertView.findViewById(R.id.subscribe_layout);
            convertView.setTag(holder);
        } else {
            holder = (ColumnOwnItemAdapter.Holder) convertView.getTag();
        }
        if(list.get(position).isClick()){
            holder.tv_subscribe_str.setTextColor(mContext.getResources().getColor(R.color.pink));
            holder.subscribe_layout.setBackgroundResource(R.drawable.circle_line_red);
        }else{
            holder.tv_subscribe_str.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            holder.subscribe_layout.setBackgroundResource(R.drawable.circle_line_gray);
        }
        holder.tv_subscribe_str.setText(list.get(position).getValue());
        if(position>0){
            list.get(0).setClick(false);
            list.get(position).setClick(false);
        }
        return convertView;
    }

    public void changeData(List<ColumnBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private class Holder {
        private TextView tv_subscribe_str;
        private LinearLayout subscribe_layout;
    }
}

