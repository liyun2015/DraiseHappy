package com.uban.rent.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.UBBaseAdapter;
import com.uban.rent.module.OrderListBean;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.util.Constants;
import com.uban.rent.util.StringUtils;
import com.uban.rent.util.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SpaceDetailRentTypeAdapter 订单列表
 * Created by dawabos on 2016/11/16.
 * Email dawabo@163.com
 */

public class OrdersListAdapter extends UBBaseAdapter<OrderListBean.ResultsBean.DatasBean, UbanListView> {
    public OrdersListAdapter(Context context, List<OrderListBean.ResultsBean.DatasBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        OrderListBean.ResultsBean.DatasBean resultsBean = list.get(i);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_user_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind(resultsBean);
        return convertView;
    }

    public void changeData(List<OrderListBean.ResultsBean.DatasBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.order_number)
        TextView orderNumber;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.order_building_name)
        TextView orderBuildingName;
        @Bind(R.id.order_time)
        TextView orderTime;
        @Bind(R.id.order_price)
        TextView orderPrice;
        @Bind(R.id.order_station_type)
        TextView orderStationType;
        @Bind(R.id.order_create_time)
        TextView orderCreateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        private void bind(OrderListBean.ResultsBean.DatasBean resultsBean) {
            orderNumber.setText("订单编号："+resultsBean.getOrderNo());
            orderStatus.setText("订单状态："+orderStatus(resultsBean.getState()));
            orderBuildingName.setText(resultsBean.getSpaceName());
            //    android:text="11月7日 14:00 至 11月7日 18:00"
            String beginTime = TimeUtils.formatTime(String.valueOf(resultsBean.getBeginTime()), "MM月dd日 HH:mm");
            String endTiem = TimeUtils.formatTime(String.valueOf(resultsBean.getEndTime()), "MM月dd日 HH:mm");
            //2016-11-11 20:32:21
            String createTiem = TimeUtils.formatTime(String.valueOf(resultsBean.getCreateAt()/1000),"yyyy-MM-dd HH:mm:ss");
            if(resultsBean.getRentType()==Constants.RENT_DAY||resultsBean.getRentType()==Constants.RENT_MONTH){
                orderTime.setText(beginTime);
            }else {
                orderTime.setText(beginTime+"至"+endTiem);
            }
            orderCreateTime.setText(createTiem);
            orderPrice.setText(StringUtils.removeZero(resultsBean.getDealPrice() ));
            orderStationType.setText(Constants.WORK_DESK_TYPE_NAME[resultsBean.getWorkDeskType()]);
        }

        private String orderStatus(int status){
            if (status==0){
                return "取消";
            }else if (status==1){
                return "等待确认";
            }
            else if (status==3){
                return "等待支付";
            }
            else if (status==4){
                return "支付成功";
            }
            else if (status==7){
                return "退款成功";
            }
            else if (status==5){
                return "退款中";
            }
            else if (status==13){
                return "订单失效";
            }
            return "";
        }
    }
}
