package com.uban.rent.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.UBBaseAdapter;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.ui.activity.CreateOrdersActivity;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.util.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SpaceDetailRentTypeAdapter 首页底部弹出列表
 * Created by dawabos on 2016/11/16.
 * Email dawabo@163.com
 */

public class SpaceDetailRentTypeAdapter extends UBBaseAdapter<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean, UbanListView> {
    private List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> spaceDeskTypePriceListBeen;
    private int type;
    public SpaceDetailRentTypeAdapter(Context context, List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean = list.get(i);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_space_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, CreateOrdersActivity.class);
                intent.putExtra(CreateOrdersActivity.KEY_SPACEDESK_ID,spaceDeskTypePriceListBean.getId());
                intent.putExtra(CreateOrdersActivity.KEY_SPACEDESK_NAME,"name");
                intent.putExtra(CreateOrdersActivity.KEY_SPACEDESK_ADDRESS,"address");
                context.startActivity(intent);
            }
        });
        holder.bind(type,spaceDeskTypePriceListBean);
        return convertView;
    }

    public void changeData(List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setPriceType(int type){
        this.type = type;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.tv_space_name)
        TextView tvSpaceName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_create_order)
        TextView tvCreateOrder;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bind(int type,SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean) {
            //1 独立空间  2 开放空间 3 hot desk 4 独立工位 5 开放工位 6 会议室 7 活动场地
            tvSpaceName.setText(Constants.WORK_DESK_TYPE_NAME[spaceDeskTypePriceListBean.getWorkDeskType()-1]);
            if (type ==0){
                tvPrice.setText(spaceDeskTypePriceListBean.getHourPrice()+"元/时");
            }else if (type ==1){
                tvPrice.setText(spaceDeskTypePriceListBean.getDayPrice()+"元/日");
            }else if (type==2){
                tvPrice.setText(spaceDeskTypePriceListBean.getWorkDeskPrice()+"元/月");
            }
        }
    }

}
