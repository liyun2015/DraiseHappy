package com.or.goodlive.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.base.UBBaseAdapter;
import com.or.goodlive.module.ColumnBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ColumnItemAdapter extends UBBaseAdapter<ColumnBean, GridView> {

    private List<ColumnBean> list;
    private Context mContext;

    public ColumnItemAdapter(Context context, List<ColumnBean> list) {
        super(context, list);
        this.list = list;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(mContext, R.layout.item_choose_view, null);
            holder.tv_subscribe_str = (TextView) convertView.findViewById(R.id.tv_subscribe_str);
            holder.subscribe_layout = (LinearLayout) convertView.findViewById(R.id.subscribe_layout);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
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

