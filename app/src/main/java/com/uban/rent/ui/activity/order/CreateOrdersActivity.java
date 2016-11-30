package com.uban.rent.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.request.RequestCreatOrder;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.wheelview.OnWheelScrollListener;
import com.uban.rent.ui.view.wheelview.WheelView;
import com.uban.rent.ui.view.wheelview.adapter.NumericWheelAdapter;
import com.uban.rent.util.CommonUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.StringUtils;
import com.uban.rent.util.TimeUtils;
import com.uban.rent.wxapi.WXPayEntryActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.uban.rent.R.id.station_time_line;

/**
 * 创建订单
 */
public class CreateOrdersActivity extends BaseActivity {
    public static final String KEY_CREATE_ORDER_PARAME_BEAN = "createOrderParamaBean";
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
    @Bind(R.id.top_view)
    RelativeLayout topView;
    @Bind(R.id.total_price)
    TextView totalPrice;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.build_office_name)
    TextView buildOfficeName;
    @Bind(R.id.build_office_address)
    TextView buildOfficeAddress;
    @Bind(R.id.station_or_office_str)
    TextView stationOrOfficeStr;
    @Bind(R.id.unit_price)
    TextView unitPrice;
    @Bind(R.id.term_of_lease_type)
    TextView termOfLeaseType;
    @Bind(R.id.meeting_room_name)
    TextView meetingRoomName;
    @Bind(R.id.meeting_room_name_layout)
    RelativeLayout meetingRoomNameLayout;
    @Bind(R.id.meeting_room_numstr)
    TextView meetingRoomNumstr;
    @Bind(R.id.meeting_room_num)
    TextView meetingRoomNum;
    @Bind(R.id.meeting_room_num_layout)
    RelativeLayout meetingRoomNumLayout;
    @Bind(R.id.station_str)
    TextView stationStr;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.reduce_btn)
    ImageView reduceBtn;
    @Bind(R.id.time_str)
    TextView timeStr;
    @Bind(R.id.month_add_btn)
    ImageView monthAddBtn;
    @Bind(R.id.reduce_btn_month)
    ImageView reduceBtnMonth;
    @Bind(R.id.right_arrows)
    ImageView rightArrows;
    @Bind(R.id.start_time_layout)
    RelativeLayout startTimeLayout;
    @Bind(R.id.right_arrows1)
    ImageView rightArrows1;
    @Bind(R.id.end_time_layout)
    RelativeLayout endTimeLayout;
    @Bind(R.id.right_textstr)
    TextView rightTextstr;
    @Bind(R.id.meeting_room_number_layout)
    LinearLayout meetingRoomNumberLayout;
    @Bind(R.id.station_number_layout)
    RelativeLayout stationNumberLayout;
    @Bind(R.id.station_time_layout)
    RelativeLayout stationTimeLayout;
    @Bind(R.id.total_time_layout)
    RelativeLayout totalTimeLayout;
    @Bind(R.id.end_time_line)
    View endTimeLine;
    @Bind(R.id.start_time_line)
    View startTimeLine;
    @Bind(station_time_line)
    View stationTimeLine;
    @Bind(R.id.tv_work_time)
    TextView tvWorkTime;
    @Bind(R.id.start_time_layout_top)
    View startTimeLayoutTop;
    private int loctionNum = 1;
    private int monthNum = 1;
    private WheelView timeWheelView;
    private WheelView month_wheelView;
    private WheelView day_wheelView;
    private boolean isStartTimePop = false;
    private int spaceDeskId;
    private int priceType;//价格类型(1时租  2日租 3月租)
    private int workDeskType;//工位类型(3 hot desk 4 独立工位 5 开放工位 6 会议室 7 活动场地)
    private double price;
    private String orderStart;
    private String orderEnd;
    private int rentTime = 1;
    private String workHoursBegin, workHoursEnd;
    private int workDeskId;
    private boolean timeIsTrue = false;
    private String isStartTimeDataDay;
    private boolean isEndTimeChoose = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_orders;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        CreateOrderParamaBean createOrderParamaBean = (CreateOrderParamaBean) getIntent().getSerializableExtra(KEY_CREATE_ORDER_PARAME_BEAN);
        spaceDeskId = createOrderParamaBean.getSpaceDeskId();
        workDeskId = createOrderParamaBean.getWorkDeskId();
        String spaceDeskName = createOrderParamaBean.getSpaceDeskName();
        String spaceDeskAddress = createOrderParamaBean.getSpaceDeskAddress();
        priceType = createOrderParamaBean.getPriceType();
        price = createOrderParamaBean.getPrice();
        workDeskType = createOrderParamaBean.getWorkDeskType();
        workHoursBegin = createOrderParamaBean.getWorkHoursBegin();
        workHoursEnd = createOrderParamaBean.getWorkHoursEnd();
        initView();
        initPriceView();//价格类型
        buildOfficeName.setText(spaceDeskName);
        buildOfficeAddress.setText(spaceDeskAddress);
        totalPrice.setText("￥ " + StringUtils.removeZero(String.valueOf(loctionNum * rentTime * price)) + "元");
        Calendar calendar = Calendar.getInstance();
        cruYear = calendar.get(Calendar.YEAR);
    }

    private void initPriceView() {

        if (priceType == 1) {
            termOfLeaseType.setText("时租");
            unitPrice.setText(StringUtils.removeZero(String.valueOf(price)) + "元/时");
            stationTimeLayout.setVisibility(View.GONE);
            endTimeLayout.setVisibility(View.VISIBLE);
            totalTimeLayout.setVisibility(View.VISIBLE);
            endTimeLine.setVisibility(View.VISIBLE);
            startTimeLine.setVisibility(View.VISIBLE);
        } else if (priceType == 2) {
            termOfLeaseType.setText("日租");
            unitPrice.setText(StringUtils.removeZero(String.valueOf(price)) + "元/日");
            stationTimeLayout.setVisibility(View.VISIBLE);
            timeStr.setText("日");
            endTimeLayout.setVisibility(View.GONE);
            totalTimeLayout.setVisibility(View.GONE);
            endTimeLine.setVisibility(View.INVISIBLE);
            startTimeLine.setVisibility(View.INVISIBLE);
            startTimeLine.setVisibility(View.INVISIBLE);
        } else if (priceType == 3) {
            termOfLeaseType.setText("月租");
            unitPrice.setText(StringUtils.removeZero(String.valueOf(price)) + "元/月");
            timeStr.setText("月");
            stationTimeLayout.setVisibility(View.VISIBLE);
            endTimeLayout.setVisibility(View.GONE);
            totalTimeLayout.setVisibility(View.GONE);
            endTimeLine.setVisibility(View.INVISIBLE);
            startTimeLine.setVisibility(View.INVISIBLE);
        }
        if (workDeskType == 6 || workDeskType == 7) {
            meetingRoomNumberLayout.setVisibility(View.VISIBLE);
            stationNumberLayout.setVisibility(View.GONE);
            startTimeLayoutTop.setVisibility(View.GONE);
            if(priceType == 2){
                stationTimeLine.setVisibility(View.GONE);
                startTimeLayoutTop.setVisibility(View.VISIBLE);
            }else{
                stationTimeLine.setVisibility(View.GONE);
            }
        } else {
            meetingRoomNumberLayout.setVisibility(View.GONE);
            stationNumberLayout.setVisibility(View.VISIBLE);
            startTimeLayoutTop.setVisibility(View.VISIBLE);
            if(priceType == 1){
                stationTimeLine.setVisibility(View.GONE);
            }else{
                stationTimeLine.setVisibility(View.VISIBLE);
            }
        }

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
        tvWorkTime.setText("营业时间： 工作日  " + workHoursBegin + "-" + workHoursEnd);
        if (workDeskType == 3) {
            stationOrOfficeStr.setText("hot desk");
        } else if (workDeskType == 4) {
            stationOrOfficeStr.setText("独立工位");
        } else if (workDeskType == 5) {
            stationOrOfficeStr.setText("开放工位");
        } else if (workDeskType == 6) {
            stationOrOfficeStr.setText("会议室");
            loctionNum = 1;
        } else if (workDeskType == 7) {
            loctionNum = 1;
            stationOrOfficeStr.setText("活动场地");
        }

        numder_of_stations.setText(String.valueOf(loctionNum));
        numderOfMonths.setText(String.valueOf(monthNum));
        if (priceType == 1) {
            Calendar calendar = Calendar.getInstance();
            int curHour = calendar.get(Calendar.HOUR_OF_DAY);
            if (workDeskType == 6 || workDeskType == 7) {
                curHour = curHour + 1;
            }
            startTime.setText(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "MM月dd日 ") + (curHour + 2) + ":00");
            endTime.setText(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "MM月dd日 ") + (curHour + 3) + ":00");
            try {
                String timeChooseStart = TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "yyyy-MM-dd ") + (curHour + 2) + ":00";
                String timeChooseEnd = TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "yyyy-MM-dd ") + (curHour + 3) + ":00";
                DateFormat fmt = new
                        SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date1 = fmt.parse(timeChooseStart);
                Date date2 = fmt.parse(timeChooseEnd);
                if (TimeUtils.isInDate(date1, workHoursBegin, workHoursEnd) && TimeUtils.isInDate(date2, workHoursBegin, workHoursEnd)) {
                    timeIsTrue = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            try {
                String timeChooseStart = TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "yyyy-MM-dd HH:mm");
                DateFormat fmt = new
                        SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = fmt.parse(timeChooseStart);
                if (TimeUtils.isInDate(date, workHoursBegin, workHoursEnd)) {
                    timeIsTrue = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startTime.setText(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis() / 1000), "MM月dd日"));
        }
        totalTime.setText(String.valueOf(rentTime));
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

    private void goActivity(Class<?> cls, RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
        Intent orderIntent = new Intent();
        orderIntent.setClass(mContext, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WXPayEntryActivity.KEY_CREATE_ORDER_RESULTSBEAN, resultsBean);
        orderIntent.putExtras(bundle);
        startActivity(orderIntent);
    }


    @OnClick({R.id.month_add_btn, R.id.reduce_btn_month, R.id.order_create, R.id.add_btn, R.id.reduce_btn, R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create://提交订单
                if (!timeIsTrue) {
                    ToastUtil.makeText(mContext, "时间不符要求，请重新选择！");
                    return;
                }
                if (priceType == 1) {
                    String orderStart = startTime.getText().toString().trim();
                    String orderEnd = endTime.getText().toString().trim();
                    long startTime = TimeUtils.geTimestampTimes(orderStart, "MM月dd日 HH:mm");
                    long endTime = TimeUtils.geTimestampTimes(orderEnd, "MM月dd日 HH:mm");
                    int total_time = (int) (endTime - startTime);
                    if (total_time > 0) {
                        submitOrder();
                    } else {
                        ToastUtil.makeText(mContext, "开始时间不能大于结束时间！");
                    }
                } else {
                    submitOrder();
                }
                break;
            case R.id.add_btn://加工位
                loctionNum = loctionNum + 1;
                numder_of_stations.setText(String.valueOf(loctionNum));
                if (priceType != 1) {
                    rentTime = monthNum;
                }
                totalPrice.setText("￥ " + StringUtils.removeZero(String.valueOf(loctionNum * rentTime * price)) + "元");
                break;
            case R.id.reduce_btn://减工位
                if (loctionNum > 0) {
                    loctionNum = loctionNum - 1;
                    numder_of_stations.setText(String.valueOf(loctionNum));
                }
                if (priceType != 1) {
                    rentTime = monthNum;
                }
                totalPrice.setText("￥ " + StringUtils.removeZero(String.valueOf(loctionNum * rentTime * price)) + "元");
                break;
            case R.id.month_add_btn://加日月
                monthNum = monthNum + 1;
                numderOfMonths.setText(String.valueOf(monthNum));
                rentTime = monthNum;
                totalPrice.setText("￥ " + StringUtils.removeZero(String.valueOf(loctionNum * rentTime * price)) + "元");
                break;
            case R.id.reduce_btn_month://减日月
                if (monthNum > 0) {
                    monthNum = monthNum - 1;
                    numderOfMonths.setText(String.valueOf(monthNum));
                }
                rentTime = monthNum;
                totalPrice.setText("￥ " + StringUtils.removeZero(String.valueOf(loctionNum * rentTime * price)) + "元");
                break;
            case R.id.start_time_layout://开始时间
                isEndTimeChoose = false;
                isStartTimePop = true;
                showTimePopupWindow();
                break;
            case R.id.end_time_layout://结束时间
                isStartTimePop = false;
                isEndTimeChoose = true;
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
        if (TextUtils.isEmpty(orderStart) && TextUtils.isEmpty(orderEnd)) {
            return;
        }
        int startTimes = 0;
        int endTimes = 0;
        if (priceType == 1) {
            startTimes = (int) TimeUtils.geTimestampTimes(cruYear + "年" + orderStart, "yyyy年MM月dd日 HH:mm");
            endTimes = (int) TimeUtils.geTimestampTimes(cruYear + "年" + orderEnd, "yyyy年MM月dd日 HH:mm");
            int rentTimes = (endTimes - startTimes) / 3600;
            rentTime = rentTimes;
        } else {
            startTimes = (int) TimeUtils.geTimestampTimes(cruYear + "年" + orderStart, "yyyy年MM月dd日");
            endTimes = 0;
        }
        RequestCreatOrder requestCreatOrder = new RequestCreatOrder();
        requestCreatOrder.setBeginTime(startTimes);
        requestCreatOrder.setEndTime(endTimes);
        requestCreatOrder.setCityId(12);
        requestCreatOrder.setDealPrice(loctionNum * rentTime * price);
        requestCreatOrder.setRentType(priceType);
        requestCreatOrder.setRentTime(rentTime);
        requestCreatOrder.setWorkDeskType(workDeskType);
        requestCreatOrder.setWorkDeskNum(loctionNum);
        requestCreatOrder.setSpaceId(spaceDeskId);
        requestCreatOrder.setUnitPrice(price);
        requestCreatOrder.setWorkdeskId(workDeskId);
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
                        //0取消,1等待确认,3等待支付,4支付成功,7退款成功,9退款中,13支付失效
                        int state = resultsBean.getState();
                        if (3 == state) {
                            goActivity(WXPayEntryActivity.class, resultsBean);
                        } else {
                            goActivity(OrdersDetailActivity.class, resultsBean);
                        }
                        finish();
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
        View timeView = getLayoutInflater().inflate(R.layout.choose_time_pop, null);
        // 确定按钮
        TextView budget_sure = (TextView) timeView.findViewById(R.id.time_sure);
        budget_sure.setOnClickListener(new View.OnClickListener() {
            public Date date;

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
                //控制营业时间：
                Calendar calendar = Calendar.getInstance();
                int cruYear = calendar.get(Calendar.YEAR);
                int curMonth = calendar.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
                int curDate = calendar.get(Calendar.DATE);
                int curMonthMore = curMonth + 1;
                int curHour = calendar.get(Calendar.HOUR_OF_DAY);
                if (priceType == 1) {
                    String timeChoose = cruYear + "-" + outMonthStr + "-" + outDayStr + " " + hourStr + ":00:00";
                    long chooseTimes = TimeUtils.geTimestampTimes(timeChoose, "yyyy-MM-dd HH:mm:ss");
                    long curTimes = System.currentTimeMillis() / 1000;
                    if (chooseTimes < curTimes || chooseTimes > (curTimes + 24 * 3600 * 30)) {
                        ToastUtil.makeText(mContext, "所选时间不符要求！");
                        timeIsTrue = false;
                        setTimeTextView(dateContent);
                        return;
                    }
                    //今天
                    try {
                        DateFormat fmt = new
                                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = fmt.parse(timeChoose);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (Integer.parseInt(outMonthStr) == curMonth && Integer.parseInt(outDayStr) == curDate) {
                        if (workDeskType == 6 || workDeskType == 7) {
                            if (TimeUtils.isInDate(date, workHoursBegin, workHoursEnd)) {
                                if (Integer.parseInt(hourStr) >= (curHour + 3)) {
                                    timeIsTrue = true;
                                } else {
                                    ToastUtil.makeText(mContext, "开始时间必须提前2小时！");
                                    timeIsTrue = false;
                                    setTimeTextView(dateContent);
                                    return;
                                }
                            } else {
                                ToastUtil.makeText(mContext, "所选时间必须在营业时间内！");
                                timeIsTrue = false;
                                setTimeTextView(dateContent);
                                return;
                            }
                        } else {
                            if (TimeUtils.isInDate(date, workHoursBegin, workHoursEnd)) {
                                if (Integer.parseInt(hourStr) >= (curHour + 2)) {
                                    timeIsTrue = true;
                                } else {
                                    ToastUtil.makeText(mContext, "开始时间必须提前1小时！");
                                    timeIsTrue = false;
                                    setTimeTextView(dateContent);
                                    return;
                                }
                            } else {
                                ToastUtil.makeText(mContext, "所选时间必须在营业时间内！");
                                timeIsTrue = false;
                                setTimeTextView(dateContent);
                                return;
                            }
                        }
                    } else {
                        if (isEndTimeChoose) {
                            if (!isStartTimeDataDay.equals(cruYear + "-" + outMonthStr + "-" + outDayStr)) {
                                ToastUtil.makeText(mContext, "所选时间必须在同一天内！");
                                setTimeTextView(dateContent);
                                return;
                            }
                        }

                        if (TimeUtils.isInDate(date, workHoursBegin, workHoursEnd)) {
                            timeIsTrue = true;
                        } else {
                            ToastUtil.makeText(mContext, "所选时间必须在营业时间内！");
                            timeIsTrue = false;
                            setTimeTextView(dateContent);
                            return;
                        }
                    }
                } else {
                    String timeContent = cruYear + "年" + outMonthStr + "月" + outDayStr + "日";
                    String currentTime = cruYear + "年" + curMonth + "月" + curDate + "日";
                    String curDateMore30 = cruYear + "年" + curMonthMore + "月" + curDate + "日";
                    long chooseTimes = TimeUtils.geTimestampTimes(timeContent, "yyyy年MM月dd日");
                    long currentTimes = TimeUtils.geTimestampTimes(currentTime, "yyyy年MM月dd日");
                    long curDateMore30s = TimeUtils.geTimestampTimes(curDateMore30, "yyyy年MM月dd日");
                    if (chooseTimes >= currentTimes && chooseTimes <= curDateMore30s) {
                        timeIsTrue = true;
                    } else {
                        ToastUtil.makeText(mContext, "开始时间必须是未来30天内！");
                        timeIsTrue = false;
                        setTimeTextView(dateContent);
                        return;
                    }
                }
                setTimeTextView(dateContent);
                initTotalTime();
                isStartTimeDataDay = cruYear + "-" + outMonthStr + "-" + outDayStr;
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
        Calendar calendar = Calendar.getInstance();
        cruYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = calendar.get(Calendar.DATE);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);

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
        timeWheelView.setCurrentItem(curHour - 1 + 2);
        outMonthStr = String.valueOf(curMonth);
        outDayStr = String.valueOf(curDate);
        hourStr = String.valueOf(curHour + 2);
        if (priceType == 1) {
            timeWheelView.setVisibility(View.VISIBLE);
        } else {
            timeWheelView.setVisibility(View.GONE);
        }
    }

    private void setTimeTextView(String dateContent) {
        if (isStartTimePop) {
            if (priceType == 1) {
                startTime.setText(dateContent);
            } else {
                startTime.setText(outMonthStr + "月" + outDayStr
                        + "日  ");
            }
        } else {
            endTime.setText(dateContent);
        }
    }

    private void initTotalTime() {
        orderStart = startTime.getText().toString().trim();
        orderEnd = endTime.getText().toString().trim();
        if (priceType == 1) {
            int startTimes = (int) TimeUtils.geTimestampTimes(orderStart, "MM月dd日 HH:mm");
            int endTimes = (int) TimeUtils.geTimestampTimes(orderEnd, "MM月dd日 HH:mm");
            rentTime = (endTimes - startTimes) / 3600;
            if (!TextUtils.isEmpty(orderStart) && !TextUtils.isEmpty(orderEnd)) {
                long startTime = TimeUtils.geTimestampTimes(orderStart, "MM月dd日 HH:mm");
                long endTime = TimeUtils.geTimestampTimes(orderEnd, "MM月dd日 HH:mm");
                int total_time = (int) (endTime - startTime);
                if (total_time > 0) {
                    totalTime.setText(String.valueOf(total_time / 3600));
                    totalPrice.setText("￥ " + StringUtils.removeZero(String.valueOf(loctionNum * rentTime * price)) + "元");
                } else {
                    ToastUtil.makeText(mContext, "开始时间不能大于结束时间！");
                    totalTime.setText("0");
                }
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
            if ((day_wheelView.getCurrentItem() + 1) < 10) {
                outDayStr = "0" + String.valueOf(day_wheelView.getCurrentItem() + 1);//日
            } else {
                outDayStr = String.valueOf(day_wheelView.getCurrentItem() + 1);//日
            }
            if ((timeWheelView.getCurrentItem() + 1) < 10) {
                hourStr = "0" + String.valueOf(timeWheelView.getCurrentItem() + 1);//小时
            } else {
                hourStr = String.valueOf(timeWheelView.getCurrentItem() + 1);//小时
            }
            outMonthStr = String.valueOf(n_month);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"创建订单页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"创建订单页");
    }
}
