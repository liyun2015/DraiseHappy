package com.uban.rent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestCreatOrder;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.CommonUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.TimeUtils;
import com.uban.rent.ui.view.wheelview.OnWheelScrollListener;
import com.uban.rent.ui.view.wheelview.WheelView;
import com.uban.rent.ui.view.wheelview.adapter.NumericWheelAdapter;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 创建订单
 */
public class CreateOrdersActivity extends BaseActivity {
    public static final String KEY_SPACEDESK_ID = "spaceDeskId";
    public static final String KEY_SPACEDESK_NAME = "spaceDeskName";
    public static final String KEY_SPACEDESK_ADDRESS = "spaceDeskAddress";
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.numder_of_stations)
    TextView numder_of_stations;
    @Bind(R.id.darkening_background_layout)
    LinearLayout darkening_background_layout;
    @Bind(R.id.activity_create_orders)
    RelativeLayout activityCreateOrders;
    @Bind(R.id.numder_of_months)
    TextView numderOfMonths;
    @Bind(R.id.start_time)
    TextView startTime;
    @Bind(R.id.end_time)
    TextView endTime;
    @Bind(R.id.total_time)
    TextView totalTime;
    private int loctionNum = 1;
    private int monthNum = 1;
    private LayoutInflater mInflater;
    private WheelView timeWheelView;
    private WheelView month_wheelView;
    private WheelView day_wheelView;
    private boolean isStartTimePop = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_orders;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        int spaceDeskId = getIntent().getIntExtra(KEY_SPACEDESK_ID, 0);
        String spaceDeskName = getIntent().getStringExtra(KEY_SPACEDESK_NAME);
        String spaceDeskAddress = getIntent().getStringExtra(KEY_SPACEDESK_ADDRESS);
        initView();
    }

    private void initView() {
        toolbarContentText.setText("创建订单");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        numder_of_stations.setText(String.valueOf(loctionNum));
        numderOfMonths.setText(String.valueOf(monthNum));
        startTime.setText(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "MM月dd日 HH:mm"));
        endTime.setText(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "MM月dd日 HH:mm"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
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

    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }


    @OnClick({R.id.month_add_btn, R.id.reduce_btn_month, R.id.order_create, R.id.add_btn, R.id.reduce_btn, R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create://提交订单
                submitOrder();
                goActivity(OrderPaymentActivity.class);
                break;
            case R.id.add_btn://加工位
                loctionNum = loctionNum + 1;
                numder_of_stations.setText(String.valueOf(loctionNum));
                break;
            case R.id.reduce_btn://减工位
                if (loctionNum > 0) {
                    loctionNum = loctionNum - 1;
                    numder_of_stations.setText(String.valueOf(loctionNum));
                }
                break;
            case R.id.month_add_btn://加月
                monthNum = monthNum + 1;
                numderOfMonths.setText(String.valueOf(monthNum));
                break;
            case R.id.reduce_btn_month://减月
                if (monthNum > 0) {
                    monthNum = monthNum - 1;
                    numderOfMonths.setText(String.valueOf(monthNum));
                }
                break;
            case R.id.start_time_layout://开始时间
                isStartTimePop = true;
                showTimePopupWindow();
                break;
            case R.id.end_time_layout://结束时间
                isStartTimePop = false;
                showTimePopupWindow();
                break;
            default:
                break;
        }
    }

    //提交订单
    private void submitOrder() {
        String orderStart = startTime.getText().toString().trim();
        String orderEnd = endTime.getText().toString().trim();
        if (!TextUtils.isEmpty(orderStart) && !TextUtils.isEmpty(orderEnd)) {
            return;
        }
        int startTimes = (int)TimeUtils.geTimestampTimes(orderStart, "MM月dd日 HH:mm");
        int endTimes = (int)TimeUtils.geTimestampTimes(orderEnd, "MM月dd日 HH:mm");
        int rentTime = (endTimes-startTimes)/3600;
        RequestCreatOrder requestCreatOrder = new RequestCreatOrder();
        requestCreatOrder.setBeginTime(startTimes);
        requestCreatOrder.setEndTime(endTimes);
        requestCreatOrder.setCityId(12);
        requestCreatOrder.setPayMoney(23599);
        requestCreatOrder.setFailureTime(System.currentTimeMillis() / 1000);
        requestCreatOrder.setCellPhone("13693133934");
        requestCreatOrder.setRentType(1);
        requestCreatOrder.setReserved("android");
        requestCreatOrder.setRentTime(rentTime);
        requestCreatOrder.setWorkDeskType(3);
        requestCreatOrder.setWorkDeskNum(12);
        ServiceFactory.getProvideHttpService().creatShortRentOrder(requestCreatOrder)
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
                        int state = resultsBean.getState();
                        goActivity(OrderPaymentActivity.class);
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

    private PopupWindow timePopupWindow; // 显示popupwindow
    private int cruYear;

    private void showTimePopupWindow() {
        // 得到控件
        darkening_background_layout.setVisibility(View.VISIBLE);
        View timeView = mInflater.inflate(R.layout.choose_time_pop, null);
        // 确定按钮
        TextView budget_sure = (TextView) timeView.findViewById(R.id.time_sure);
        budget_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timePopupWindow.isShowing()) {
                    timePopupWindow.dismiss();
                    darkening_background_layout.setVisibility(View.GONE);
                }
                // 将取到的时间拼成出生日期
                String dateContent = "";
                if (TextUtils.isEmpty(outMonthStr)
                        || TextUtils.isEmpty(outDayStr)
                        || TextUtils.isEmpty(hourStr)) {
                    dateContent = "";
                } else {
                    dateContent = outMonthStr + "月" + outDayStr
                            + "日  " + hourStr + ":00";
                }
                if (!TextUtils.isEmpty(dateContent)) {
                    if (isStartTimePop) {
                        startTime.setText(dateContent);
                    } else {
                        endTime.setText(dateContent);
                    }
                    initTotalTime();
                }
            }
        });
        // 取消按钮
        TextView budget_cancle = (TextView) timeView
                .findViewById(R.id.time_cancle);
        budget_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timePopupWindow.isShowing()) {
                    timePopupWindow.dismiss();
                    darkening_background_layout.setVisibility(View.GONE);
                }
            }
        });
        Calendar c = Calendar.getInstance();
        cruYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        int curHour = c.get(Calendar.HOUR_OF_DAY);

        //月
        month_wheelView = (WheelView) timeView.findViewById(R.id.month_wheelView);
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this, 1, 12, "%02d");
        numericWheelAdapter.setLabel("月");
        month_wheelView.setViewAdapter(numericWheelAdapter);
        month_wheelView.setCyclic(true);
        month_wheelView.addScrollingListener(scrollListener);
        //日
        day_wheelView = (WheelView) timeView.findViewById(R.id.day_wheelView);
        initDay(cruYear, curMonth);
        day_wheelView.setCyclic(true);
        day_wheelView.addScrollingListener(scrollListener);
        // 小时控件
        timeWheelView = (WheelView) timeView.findViewById(R.id.time_wheelView);
        NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(this, 1, 23, "%02d");
        numericWheelAdapter1.setLabel("时");
        timeWheelView.setViewAdapter(numericWheelAdapter1);
        timeWheelView.setCyclic(true);
        timeWheelView.addScrollingListener(scrollListener);
        int screeWidth = getWindowManager().getDefaultDisplay().getWidth();

        timePopupWindow = CommonUtil.ShowBottomPopupWindow(
                CreateOrdersActivity.this, timePopupWindow, timeView, screeWidth,
                258, activityCreateOrders);
        CommonUtil.setPopupWindowListener(new CommonUtil.PopupWindowListener() {

            @Override
            public void myDissmiss() {
                timePopupWindow = null;
                darkening_background_layout.setVisibility(View.GONE);
            }

        });
        month_wheelView.setCurrentItem(curMonth - 1);
        day_wheelView.setCurrentItem(curDate - 1);
        timeWheelView.setCurrentItem(curHour - 1);
        outMonthStr=String.valueOf(curMonth);
        outDayStr=String.valueOf(curDate);
        hourStr=String.valueOf(curHour);
    }

    private void initTotalTime() {
        String orderStart = startTime.getText().toString().trim();
        String orderEnd = endTime.getText().toString().trim();
        if (!TextUtils.isEmpty(orderStart) && !TextUtils.isEmpty(orderEnd)) {
            long startTime = TimeUtils.geTimestampTimes(orderStart, "MM月dd日 HH:mm");
            long endTime = TimeUtils.geTimestampTimes(orderEnd, "MM月dd日 HH:mm");
            int total_time =  (int)(endTime-startTime);
            if(total_time>0){
                totalTime.setText(String.valueOf(total_time/3600));
            }else{
                ToastUtil.makeText(mContext, "开始时间不能大于结束时间！");
                totalTime.setText("0");
            }
        }
    }

    private void initDay(int year, int month) {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this, 1, TimeUtils.getDay(year, month), "%02d");
        numericWheelAdapter.setLabel("日");
        day_wheelView.setViewAdapter(numericWheelAdapter);
    }

    private String outMonthStr;// 输出 的月
    private String outDayStr;// 输出的日
    private String hourStr;// 输出的小时
    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_month = month_wheelView.getCurrentItem() + 1;//月
            initDay(cruYear, n_month);
            outDayStr = String.valueOf(day_wheelView.getCurrentItem() + 1);//日
            hourStr = String.valueOf(timeWheelView.getCurrentItem() + 1);//小时
            outMonthStr = String.valueOf(n_month);
        }
    };
}
