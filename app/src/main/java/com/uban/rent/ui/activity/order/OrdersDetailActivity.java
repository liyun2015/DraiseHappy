package com.uban.rent.ui.activity.order;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestOrderDetailBean;
import com.uban.rent.module.request.RequestPaymentOrder;
import com.uban.rent.ui.activity.detail.SpaceDetailActivity;
import com.uban.rent.ui.activity.detail.StationDetailActivity;
import com.uban.rent.ui.activity.other.RefundOrderActivity;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.CommonUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;
import com.uban.rent.util.StringUtils;
import com.uban.rent.util.TimeUtils;
import com.uban.rent.wxapi.WXPayEntryActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.uban.rent.wxapi.WXPayEntryActivity.KEY_CREATE_ORDER_RESULTSBEAN;

/**
 * 订单详情页
 */
public class OrdersDetailActivity extends BaseActivity {
    public static final String KEY_ORDER_NUMBER = "keyOrderNumber";
    public static final String KEY_ORDER_STATE = "keyOrderState";
    public static final String KEY_WORKDESKTYPE = "keyWorkdeskType";
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
    @Bind(R.id.meeting_room_name)
    TextView meetingRoomName;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.making_call)
    TextView makingCall;
    @Bind(R.id.bottom_view)
    RelativeLayout bottomView;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.cancel_order_btn)
    TextView cancelOrderBtn;
    @Bind(R.id.submit_right_btn)
    RelativeLayout submitRightBtn;
    @Bind(R.id.station)
    TextView station;
    @Bind(R.id.station_layout)
    RelativeLayout stationLayout;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.time_str_layout)
    RelativeLayout timeStrLayout;
    @Bind(R.id.start_time_layout)
    RelativeLayout startTimeLayout;
    @Bind(R.id.activity_orders_detail)
    RelativeLayout activityOrdersDetail;
    @Bind(R.id.none_meeting_room_view)
    LinearLayout noneMeetingRoomView;
    @Bind(R.id.meeting_room_making_call)
    RelativeLayout meetingRoomMakingCall;
    @Bind(R.id.meeting_room_cancel_order)
    TextView meetingRoomCancelOrder;
    @Bind(R.id.meeting_room_submit_order)
    TextView meetingRoomSubmitOrder;
    @Bind(R.id.meeting_room_view)
    LinearLayout meetingRoomView;
    @Bind(R.id.end_time_layout)
    RelativeLayout endTimeLayout;
    @Bind(R.id.darkening_background_layout)
    LinearLayout darkeningBackgroundLayout;
    @Bind(R.id.order_build_name_layout)
    RelativeLayout orderBuildNameLayout;
    private int state;//0取消,1等待确认,3等待支付,4支付成功,7退款成功,5退款中,13订单失效
    private static final Integer[] ORDER_TYPE = new Integer[]{0, 1, 3, 4, 7, 5, 13};
    private static final String[] ORDER_TYPE_STR = new String[]{"已取消", "等待确认", "等待支付", "支付成功", "退款成功", "退款中", "订单失效"};
    private static final String[] CANCEL_REASON_STR = new String[]{"我要重新预定", "下错订单", "不需要预定了", "其他"};
    private int workDeskType;
    private int priceType;
    private LayoutInflater mInflater;
    private PopupWindow cancelPopupWindow;
    private String orderNum;
    private int workdeskType;
    private RequestCreatShortRentOrderBean.ResultsBean resultDataBean;
    private TimeCount time;
    private String directorPhone2="";

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
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initData();
    }

    private void initDataView(RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
        resultDataBean = resultsBean;
        workdeskType = resultsBean.getWorkDeskType();
        orderNum = resultsBean.getOrderNo();
        orderNumber.setText("订单编号：" + orderNum);
        orderTime.setText(resultsBean.getCreatAt());
        state = resultsBean.getState();
        directorPhone2 = resultsBean.getOfficespaceBasicinfo().getDirectorPhone2();
        int failureAt = resultsBean.getFailureAt();
        if (state == ORDER_TYPE[0]) {//0取消
            orderState.setText(ORDER_TYPE_STR[0]);
            orderState.setTextColor(getResources().getColor(R.color.colorGrayHint));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
            meetingRoomView.setVisibility(View.GONE);
            noneMeetingRoomView.setVisibility(View.VISIBLE);
        } else if (state == ORDER_TYPE[1]) {//1等待确认
            orderState.setText(ORDER_TYPE_STR[1]);
            orderState.setTextColor(getResources().getColor(R.color.colorAccent));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.VISIBLE);
            bottomLine.setVisibility(View.VISIBLE);
            orderCreate.setVisibility(View.VISIBLE);
            meetingRoomView.setVisibility(View.VISIBLE);
            noneMeetingRoomView.setVisibility(View.GONE);
            makingCall.setVisibility(View.GONE);
            cancelOrderBtn.setText("取消订单");
        } else if (state == ORDER_TYPE[2]) {//3等待支付
            orderState.setText(ORDER_TYPE_STR[2]);
            orderState.setTextColor(getResources().getColor(R.color.colorAccent));
            messageRemindStr.setVisibility(View.VISIBLE);
            bottomView.setVisibility(View.VISIBLE);
            bottomLine.setVisibility(View.VISIBLE);
            orderCreate.setVisibility(View.VISIBLE);
            makingCall.setVisibility(View.GONE);
            meetingRoomView.setVisibility(View.GONE);
            noneMeetingRoomView.setVisibility(View.VISIBLE);
            cancelOrderBtn.setText("取消订单");
            StartCountDown(failureAt);
        } else if (state == ORDER_TYPE[3]) {//4支付成功
            orderState.setText(ORDER_TYPE_STR[3]);
            orderState.setTextColor(getResources().getColor(R.color.green_background));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.VISIBLE);
            bottomLine.setVisibility(View.VISIBLE);
            orderCreate.setVisibility(View.GONE);
            makingCall.setVisibility(View.VISIBLE);
            meetingRoomView.setVisibility(View.GONE);
            noneMeetingRoomView.setVisibility(View.VISIBLE);
            cancelOrderBtn.setText("申请退款");
        } else if (state == ORDER_TYPE[4]) {//7退款成功
            orderState.setText(ORDER_TYPE_STR[4]);
            orderState.setTextColor(getResources().getColor(R.color.green_background));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
            meetingRoomView.setVisibility(View.GONE);
            noneMeetingRoomView.setVisibility(View.VISIBLE);
        } else if (state == ORDER_TYPE[5]) {//9退款中
            orderState.setText(ORDER_TYPE_STR[5]);
            orderState.setTextColor(getResources().getColor(R.color.colorAccent));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
            meetingRoomView.setVisibility(View.GONE);
            noneMeetingRoomView.setVisibility(View.VISIBLE);
        } else if (state == ORDER_TYPE[6]) {//13支付失效
            orderState.setText(ORDER_TYPE_STR[6]);
            orderState.setTextColor(getResources().getColor(R.color.colorGrayHint));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
            meetingRoomView.setVisibility(View.GONE);
            noneMeetingRoomView.setVisibility(View.VISIBLE);
        }
        orderBuildName.setText(resultsBean.getOfficespaceBasicinfo().getSpaceCnName());
        orderBuildAddress.setText(resultsBean.getOfficespaceBasicinfo().getAddress());
        workDeskType = resultsBean.getWorkDeskType();

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
        if (workDeskType == 6 || workDeskType == 7) {
            meetingRoomNameLayout.setVisibility(View.VISIBLE);
            if(null!=resultsBean.getOfficespaceWorkdeskinfo()){
                meetingRoomName.setText(resultsBean.getOfficespaceWorkdeskinfo().getWorkDeskNo());
            }
            stationStr.setText("间");
            orderNumberOfStation.setText("1");
        } else {
            meetingRoomNameLayout.setVisibility(View.GONE);
            stationStr.setText("工位");
            orderNumberOfStation.setText(String.valueOf(resultsBean.getWorkDeskNum()));
        }

        priceType = resultsBean.getRentType();
        int price = resultsBean.getUnitPrice();
        if (priceType == 1) {
            priceOfType.setText("时租");
            timeStr.setText("时");
            priceOfOrder.setText(price + "元/时");
            priceSingle.setText(price + "元时");
            totalOfTime.setText(resultsBean.getRentTime() + "小时");
            startTime.setText(TimeUtils.formatTime(String.valueOf(resultsBean.getBeginTime()), "MM月dd日 HH:mm"));
            endTimeLayout.setVisibility(View.VISIBLE);
        } else if (priceType == 2) {
            priceOfType.setText("日租");
            priceOfOrder.setText(price + "元/日");
            priceSingle.setText(price + "元日");
            timeStr.setText("日");
            totalOfTime.setText(resultsBean.getRentTime() + "日");
            startTime.setText(TimeUtils.formatTime(String.valueOf(resultsBean.getBeginTime()), "MM月dd日"));
            endTimeLayout.setVisibility(View.GONE);
        } else if (priceType == 3) {
            priceOfType.setText("月租");
            timeStr.setText("月");
            priceOfOrder.setText(price + "元/月");
            priceSingle.setText(price + "元月");
            totalOfTime.setText(resultsBean.getRentTime() + "月");
            startTime.setText(TimeUtils.formatTime(String.valueOf(resultsBean.getBeginTime()), "MM月dd日"));
            endTimeLayout.setVisibility(View.GONE);
        }
        stationNum.setText(String.valueOf(resultsBean.getWorkDeskNum()));
        timeNum.setText(String.valueOf(resultsBean.getRentTime()));
        priceInTota.setText(StringUtils.removeZero(String.valueOf(resultsBean.getDealPrice()))+"元");
        endTime.setText(TimeUtils.formatTime(String.valueOf(resultsBean.getEndTime()), "MM月dd日 HH:mm"));
    }

    private void StartCountDown(int failureAt) {
        time = new TimeCount(failureAt * 1000, 1000);
        time.start();// 开始计时
    }

    private void initData() {
        String orderNum = getIntent().getStringExtra(KEY_ORDER_NUMBER);
        if (null == orderNum) {
            RequestCreatShortRentOrderBean.ResultsBean resultsBean = (RequestCreatShortRentOrderBean.ResultsBean) getIntent().getSerializableExtra(KEY_CREATE_ORDER_RESULTSBEAN);
            orderNum = resultsBean.getOrderNo();
            directorPhone2 = resultsBean.getOfficespaceBasicinfo().getDirectorPhone2();
        }
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
                if (null != time) {
                    time.cancel();
                }
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

    @OnClick({R.id.order_build_name_layout,R.id.cancel_order_btn, R.id.submit_right_btn, R.id.meeting_room_making_call, R.id.meeting_room_cancel_order, R.id.meeting_room_submit_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_order_btn:
                if (state == ORDER_TYPE[1] || state == ORDER_TYPE[2]) {//取消订单
                    StatService.onEvent(mContext, "OrderDetial_CancelOrderEvent", "pass", 1);
                    showCancelPop();
                } else if (state == ORDER_TYPE[3]) {//申请退款
                    Intent intent = new Intent();
                    intent.putExtra(KEY_ORDER_NUMBER, orderNum);
                    intent.putExtra(KEY_ORDER_STATE, state);
                    intent.putExtra(KEY_WORKDESKTYPE, workdeskType);
                    intent.setClass(mContext, RefundOrderActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.submit_right_btn:// 提交
                if (state == ORDER_TYPE[1] || state == ORDER_TYPE[2]) {//立即支付
                    StatService.onEvent(mContext, "OrderDetail_PayBtnClickEvent", "pass", 1);
                    goActivity(WXPayEntryActivity.class);
                } else if (state == ORDER_TYPE[3]) {//拨打电话
                    StatService.onEvent(mContext, "OrderDetail_ZiXunPhoneEvent", "pass", 1);
                    callPhone();
                }
                break;
            case R.id.meeting_room_making_call:
                StatService.onEvent(mContext, "OrderDetail_ZiXunPhoneEvent", "pass", 1);
                callPhone();
                break;
            case R.id.meeting_room_cancel_order:
                StatService.onEvent(mContext, "OrderDetail_ApplyRefundEvent", "pass", 1);
                showCancelPop();
                break;
            case R.id.meeting_room_submit_order:
                //goActivity(WXPayEntryActivity.class);
                ToastUtil.makeText(mContext, "管理员确认中，请耐心等待！");
                break;
            case R.id.order_build_name_layout:
                Intent intent = new Intent();
                intent.setClass(mContext, StationDetailActivity.class);
                intent.putExtra(StationDetailActivity.KEY_BUILD_WORK_PLACE_DETAIL,requestGoWorkPlaceDetailBean());
                intent.putExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL,requestGoSpaceDetailBean());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetailBean(){
        RequestGoWorkPlaceDetail requestGoWorkPlaceDetail = new RequestGoWorkPlaceDetail();
        requestGoWorkPlaceDetail.setWorkplaceDetailId(resultDataBean.getWorkDeskId());
        requestGoWorkPlaceDetail.setPriceType(resultDataBean.getRentType());
        return requestGoWorkPlaceDetail;
    }
    private RequestGoSpaceDetail requestGoSpaceDetailBean(){
        RequestGoSpaceDetail requestGoSpaceDetail = new RequestGoSpaceDetail();
        requestGoSpaceDetail.setLocationX(resultDataBean.getOfficespaceBasicinfo().getMapX());
        requestGoSpaceDetail.setLocationY(resultDataBean.getOfficespaceBasicinfo().getMapY());
        requestGoSpaceDetail.setOfficeSpaceBasicInfoId(resultDataBean.getOfficespaceBasicinfo().getOfficespaceBasicinfoId());
        return requestGoSpaceDetail;
    }

    //拨打电话
    private void callPhone() {
        RxPermissions.getInstance(mContext).request(Manifest .permission.CALL_PHONE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            PhoneUtils.call(mContext, directorPhone2);
                        } else {
                            ToastUtil.makeText(mContext, "未授权");
                        }
                    }
                });
    }

    private void goActivity(Class<?> cls) {
        if (null != time) {
            time.cancel();
        }
        Intent orderIntent = new Intent();
        orderIntent.setClass(mContext, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WXPayEntryActivity.KEY_CREATE_ORDER_RESULTSBEAN, resultDataBean);
        orderIntent.putExtras(bundle);
        startActivity(orderIntent);
        finish();
    }

    private TextView cancel_reason_one;
    private TextView cancel_reason_two;
    private TextView cancel_reason_three;
    private TextView cancel_reason_four;
    private String cancelReason = CANCEL_REASON_STR[0];

    private void showCancelPop() {
        darkeningBackgroundLayout.setVisibility(View.VISIBLE);
        View cancelView = mInflater.inflate(R.layout.choose_cancel_reason_pop, null);
        cancel_reason_one = (TextView) cancelView.findViewById(R.id.cancel_reason_one);
        cancel_reason_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReason = CANCEL_REASON_STR[0];
                cancel_reason_one.setTextColor(getResources().getColor(R.color.colorPrimary));
                cancel_reason_two.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_three.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_four.setTextColor(getResources().getColor(R.color.colorGrayHint));
            }
        });
        cancel_reason_two = (TextView) cancelView.findViewById(R.id.cancel_reason_two);
        cancel_reason_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReason = CANCEL_REASON_STR[1];
                cancel_reason_one.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_two.setTextColor(getResources().getColor(R.color.colorPrimary));
                cancel_reason_three.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_four.setTextColor(getResources().getColor(R.color.colorGrayHint));
            }
        });
        cancel_reason_three = (TextView) cancelView.findViewById(R.id.cancel_reason_three);
        cancel_reason_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReason = CANCEL_REASON_STR[2];
                cancel_reason_one.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_two.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_three.setTextColor(getResources().getColor(R.color.colorPrimary));
                cancel_reason_four.setTextColor(getResources().getColor(R.color.colorGrayHint));
            }
        });
        cancel_reason_four = (TextView) cancelView.findViewById(R.id.cancel_reason_four);
        cancel_reason_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReason = CANCEL_REASON_STR[3];
                cancel_reason_one.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_two.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_three.setTextColor(getResources().getColor(R.color.colorGrayHint));
                cancel_reason_four.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        TextView submit_right_btn = (TextView) cancelView.findViewById(R.id.submit_btn);
        submit_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelPopupWindow.isShowing()) {
                    cancelPopupWindow.dismiss();
                    darkeningBackgroundLayout.setVisibility(View.GONE);
                }
                cancelSubmit();
            }
        });
        TextView cancel_btn = (TextView) cancelView.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelPopupWindow.isShowing()) {
                    cancelPopupWindow.dismiss();
                    darkeningBackgroundLayout.setVisibility(View.GONE);
                }
            }
        });
        int screeWidth = getWindowManager().getDefaultDisplay().getWidth();
        cancelPopupWindow = CommonUtil.ShowBottomPopupWindow(
                OrdersDetailActivity.this, cancelPopupWindow, cancelView, screeWidth,
                220, activityOrdersDetail);
        CommonUtil.setPopupWindowListener(new CommonUtil.PopupWindowListener() {

            @Override
            public void myDissmiss() {
                cancelPopupWindow = null;
                darkeningBackgroundLayout.setVisibility(View.GONE);
            }

        });
    }

    private void cancelSubmit() {
        RequestPaymentOrder requestPaymentOrder = new RequestPaymentOrder();
        requestPaymentOrder.setOrderNo(orderNum);
        requestPaymentOrder.setState(state);
        requestPaymentOrder.setWorkDeskType(workdeskType);
        requestPaymentOrder.setRefundDesc(cancelReason);
        ServiceFactory.getProvideHttpService().cancelShortRentOrder(requestPaymentOrder)
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
                        initData();
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


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {
            //倒计时完毕
            messageRemindStr.setVisibility(View.GONE);
            orderState.setText(ORDER_TYPE_STR[6]);
            orderState.setTextColor(getResources().getColor(R.color.colorGrayHint));
            messageRemindStr.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
            bottomLine.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int isHourOron = (int) millisUntilFinished / (1000 * 60 * 60);
            if (isHourOron > 0) {
                int hour = (int) millisUntilFinished / (1000 * 60 * 60);
                int min = (int) (millisUntilFinished % (1000 * 60 * 60)) / 60;
                int sec = (int) (millisUntilFinished % (1000 * 60 * 60)) % 60;
                messageRemindStr.setText(hour + "时" + min + "分" + sec + "秒");
            } else {
                String countTime = (int) millisUntilFinished / (1000 * 60) + "分" + (millisUntilFinished / 1000) % 60 + "秒";
                messageRemindStr.setText(Html.fromHtml("请在 <font color='#FF5254' >" + countTime + "</font>内完成支付，晚了就没有了哟！"));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"订单详情页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"订单详情页");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            if (null != time) {
                time.cancel();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
