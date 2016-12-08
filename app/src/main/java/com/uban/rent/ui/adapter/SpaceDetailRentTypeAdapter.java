package com.uban.rent.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.base.UBBaseAdapter;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.ui.activity.member.MemberFinalActivity;
import com.uban.rent.ui.activity.member.MemberFirstActivity;
import com.uban.rent.ui.activity.order.CreateOrdersActivity;
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
    private int mPriceType = 1;
    private CreateOrderParamaBean createOrderParamaBean;
    private RxAppCompatActivity rxAppCompatActivity;
    public SpaceDetailRentTypeAdapter(Context context, List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> list,CreateOrderParamaBean createOrderParamaBean) {
        super(context, list);
        this.createOrderParamaBean = createOrderParamaBean;
        this.rxAppCompatActivity = (RxAppCompatActivity)mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_space_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position%2==0){
            holder.itemView.setBackgroundColor(Color.WHITE);
        }else {
            holder.itemView.setBackgroundColor(Color.parseColor("#F7F7F7"));
        }
        holder.bind(mPriceType,spaceDeskTypePriceListBean);
        holder.tvCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HeaderConfig.isEmptyUbanToken()){
                     mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }else {
                    if (spaceDeskTypePriceListBean.getWorkDeskType()==Constants.HOT_DESK_TYPE){
                        StatService.onEvent(mContext, "SpaceDetail_MyMemberBtnEvent", "pass", 1);
                        BaseActivityMemberStatusGoView();
                    }else {
                        StatService.onEvent(mContext, "SpaceDetail_OrderBtnClickEvent", "pass", 1);
                        StatService.onEvent(mContext, "MainMap_OrderBtnClickEvent", "pass", 1);
                        RequestcreateOrderParamaBean(spaceDeskTypePriceListBean);
                        Intent intent = new Intent();
                        intent.setClass(mContext, CreateOrdersActivity.class);
                        intent.putExtra(CreateOrdersActivity.KEY_CREATE_ORDER_PARAME_BEAN,createOrderParamaBean);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        return convertView;
    }

    public void BaseActivityMemberStatusGoView(){
        if (HeaderConfig.isMemberStatus()){
            mContext.startActivity(new Intent(mContext, MemberFinalActivity.class));
        }else {
            mContext.startActivity(new Intent(mContext, MemberFirstActivity.class));
        }
    }
    //生成订单参数
    private CreateOrderParamaBean RequestcreateOrderParamaBean(SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean){
        createOrderParamaBean.setPriceType(mPriceType);
        if (mPriceType ==Constants.RENT_HOUSE){
            createOrderParamaBean.setPrice(spaceDeskTypePriceListBean.getHourPrice());
        }else if (mPriceType ==Constants.RENT_DAY){
            createOrderParamaBean.setPrice(spaceDeskTypePriceListBean.getDayPrice());
        }else if (mPriceType==Constants.RENT_MONTH){
            createOrderParamaBean.setPrice(spaceDeskTypePriceListBean.getWorkDeskPrice());
        }
        createOrderParamaBean.setWorkDeskType(spaceDeskTypePriceListBean.getWorkDeskType());
        createOrderParamaBean.setWorkHoursBegin(spaceDeskTypePriceListBean.getWorkHoursBegin());
        createOrderParamaBean.setWorkHoursEnd(spaceDeskTypePriceListBean.getWorkHoursEnd());
        createOrderParamaBean.setWorkDeskId(spaceDeskTypePriceListBean.getId());
        return createOrderParamaBean;
    }

    public void changeData(List<SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setPriceType(int mPriceType){
        this.mPriceType = mPriceType;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.tv_space_name)
        TextView tvSpaceName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_create_order)
        TextView tvCreateOrder;

        @Bind(R.id.rl_space_rent_item_view)
        RelativeLayout itemView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bind(int mPriceType,SpaceDetailBean.ResultsBean.SpaceDeskTypePriceListBean spaceDeskTypePriceListBean) {
            if (mPriceType==Constants.RENT_MONTH&&spaceDeskTypePriceListBean.getWorkDeskType()==Constants.CONFERENCE_ROOM_TYPE){
                itemView.setVisibility(View.GONE);
            }else if (mPriceType==Constants.RENT_MONTH&&spaceDeskTypePriceListBean.getWorkDeskType()==Constants.ACTIVE_SITE_TYPE){
                itemView.setVisibility(View.GONE);
            } else {
                itemView.setVisibility(View.VISIBLE);
                //1 独立空间  2 开放空间 3 hot desk 4 独立工位 5 开放工位 6 会议室 7 活动场地
                tvSpaceName.setText(Constants.WORK_DESK_TYPE_NAME[spaceDeskTypePriceListBean.getWorkDeskType()]);
                if ((spaceDeskTypePriceListBean.getWorkDeskType())==Constants.HOT_DESK_TYPE){
                    tvCreateOrder.setText(HeaderConfig.isMemberStatus()?"我的会员":"成为会员");
                    tvPrice.setText("会员免费");
                }else {
                    tvCreateOrder.setText("立即预定");
                    if (mPriceType == Constants.RENT_HOUSE){
                        tvPrice.setText(spaceDeskTypePriceListBean.getHourPrice()+"元/时");
                    }else if (mPriceType ==Constants.RENT_DAY){
                        tvPrice.setText(spaceDeskTypePriceListBean.getDayPrice()+"元/日");
                    }else if (mPriceType==Constants.RENT_MONTH){
                        tvPrice.setText(spaceDeskTypePriceListBean.getWorkDeskPrice()+"元/月");
                    }
                }
            }

        }
    }

}
