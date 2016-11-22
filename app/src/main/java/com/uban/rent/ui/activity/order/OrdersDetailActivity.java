package com.uban.rent.ui.activity.order;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestOrderDetailBean;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.TimeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * 订单详情页
 */
public class OrdersDetailActivity extends BaseActivity {
    public static final String KEY_ORDER_NUMBER = "keyOrderNumber";
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.message_remind_str)
    TextView messageRemindStr;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_time)
    TextView orderTime;
    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.order_build_name)
    TextView orderBuildName;
    @Bind(R.id.order_build_address)
    TextView orderBuildAddress;
    @Bind(R.id.work_loction_type)
    TextView workLoctionType;
    @Bind(R.id.price_of_type)
    TextView priceOfType;
    @Bind(R.id.price_of_order)
    TextView priceOfOrder;
    @Bind(R.id.station_num)
    TextView stationNum;
    @Bind(R.id.time_num)
    TextView timeNum;
    @Bind(R.id.start_time)
    TextView startTime;
    @Bind(R.id.end_time)
    TextView endTime;
    @Bind(R.id.time_str)
    TextView timeStr;
    @Bind(R.id.order_number_of_station)
    TextView orderNumberOfStation;
    @Bind(R.id.total_of_time)
    TextView totalOfTime;
    @Bind(R.id.price_single)
    TextView priceSingle;
    @Bind(R.id.price_in_tota)
    TextView priceInTota;
    @Bind(R.id.meeting_room_name_layout)
    RelativeLayout meetingRoomNameLayout;
    @Bind(R.id.station_str)
    TextView stationStr;
    @Bind(R.id.activity_orders_detail)
    RelativeLayout activityOrdersDetail;
    @Bind(R.id.meeting_room_name)
    TextView meetingRoomName;
    private int state;//0取消,1等待确认,3等待支付,4支付成功,7退款成功,9退款中,13支付失效
    private static final Integer[] ORDER_TYPE = new Integer[]{0, 1, 3, 4, 7, 9, 13};
    private static final String[] ORDER_TYPE_STR = new String[]{"取消", "等待确认", "等待支付", "支付成功", "退款成功", "退款中", "支付失效"};
    private int workDeskType;
    private int priceType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orders_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbarContentText.setText("订单详情");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initData();
    }

    private void initDataView(RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
        orderNumber.setText("订单编号：" + resultsBean.getOrderNo());
        orderTime.setText(resultsBean.getCreatAt());
        state = resultsBean.getState();
        if (state == ORDER_TYPE[0]) {
            orderState.setText(ORDER_TYPE_STR[0]);
        } else if (state == ORDER_TYPE[1]) {
            orderState.setText(ORDER_TYPE_STR[1]);
        } else if (state == ORDER_TYPE[2]) {
            orderState.setText(ORDER_TYPE_STR[2]);
        } else if (state == ORDER_TYPE[3]) {
            orderState.setText(ORDER_TYPE_STR[3]);
        } else if (state == ORDER_TYPE[4]) {
            orderState.setText(ORDER_TYPE_STR[4]);
        } else if (state == ORDER_TYPE[5]) {
            orderState.setText(ORDER_TYPE_STR[5]);
        } else if (state == ORDER_TYPE[6]) {
            orderState.setText(ORDER_TYPE_STR[6]);
        }
        orderBuildName.setText(resultsBean.getOfficespaceBasicinfo().getSpaceCnName());
        orderBuildAddress.setText(resultsBean.getOfficespaceBasicinfo().getAddress());
        workDeskType = resultsBean.getWorkDeskType();
        if (workDeskType == 6) {
            meetingRoomNameLayout.setVisibility(View.VISIBLE);
            stationStr.setText("间");
            orderNumberOfStation.setText("1");
            meetingRoomName.setText("");
        } else {
            meetingRoomNameLayout.setVisibility(View.GONE);
            stationStr.setText("工位");
            orderNumberOfStation.setText(String.valueOf(resultsBean.getWorkDeskNum()));
        }
        if (workDeskType == 3) {
            workLoctionType.setText("hot desk");
        } else if (workDeskType == 4) {
            workLoctionType.setText("独立工位");
        } else if (workDeskType == 5) {
            workLoctionType.setText("开放工位");
        } else if (workDeskType == 6) {
            workLoctionType.setText("会议室");
        } else if (workDeskType == 7) {
            workLoctionType.setText("活动场地");
        }
        priceType = resultsBean.getRentType();
        int price = resultsBean.getUnitPrice();
        if (priceType == 1) {
            priceOfType.setText("时租");
            timeStr.setText("时");
            priceOfOrder.setText(price + "元/时");
            priceSingle.setText(price + "元时");
            totalOfTime.setText(resultsBean.getRentTime() + "小时");
        } else if (priceType == 2) {
            priceOfType.setText("日租");
            priceOfOrder.setText(price + "元/日");
            priceSingle.setText(price + "元日");
            timeStr.setText("日");
            totalOfTime.setText(resultsBean.getRentTime() + "日");
        } else if (priceType == 3) {
            priceOfType.setText("月租");
            timeStr.setText("月");
            priceOfOrder.setText(price + "元/月");
            priceSingle.setText(price + "元月");
            totalOfTime.setText(resultsBean.getRentTime() + "月");
        }
        stationNum.setText(String.valueOf(resultsBean.getWorkDeskNum()));
        timeNum.setText(String.valueOf(resultsBean.getRentTime()));
        priceInTota.setText(String.valueOf(resultsBean.getPayMoney()));
        startTime.setText(TimeUtils.formatTime(String.valueOf(resultsBean.getBeginTime()), "MM月dd日 HH:mm"));
        endTime.setText(TimeUtils.formatTime(String.valueOf(resultsBean.getEndTime()), "MM月dd日 HH:mm"));
    }

    private void initData() {
        String orderNum = getIntent().getStringExtra(KEY_ORDER_NUMBER);
        RequestOrderDetailBean requestOrderDetailBean = new RequestOrderDetailBean();
        requestOrderDetailBean.setOrderNo(orderNum);
        ServiceFactory.getProvideHttpService().getOrderDetail(requestOrderDetailBean)
                .compose(this.<RequestCreatShortRentOrderBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<RequestCreatShortRentOrderBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<RequestCreatShortRentOrderBean, Boolean>() {
                    @Override
                    public Boolean call(RequestCreatShortRentOrderBean requestCreatShortRentOrderBean) {
                        return requestCreatShortRentOrderBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<RequestCreatShortRentOrderBean, RequestCreatShortRentOrderBean.ResultsBean>() {
                    @Override
                    public RequestCreatShortRentOrderBean.ResultsBean call(RequestCreatShortRentOrderBean requestCreatShortRentOrderBean) {
                        return requestCreatShortRentOrderBean.getResults();
                    }
                })
                .subscribe(new Action1<RequestCreatShortRentOrderBean.ResultsBean>() {
                    @Override
                    public void call(RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
                        //处理返回结果
                        initDataView(resultsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error) + throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
