package com.uban.rent.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.ResultOrderQueryBean;
import com.uban.rent.module.WXPayProviderBean;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestPaymentOrder;
import com.uban.rent.module.request.UnifieOrderBean;
import com.uban.rent.ui.activity.order.OrdersDetailActivity;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.CommonUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.IpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    public static final String KEY_CREATE_ORDER_RESULTSBEAN = "createOrderResultsBean";
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_time)
    TextView orderTime;
    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.switch_btn_one)
    CheckBox switchBtnOne;
    @Bind(R.id.switch_btn_two)
    CheckBox switchBtnTwo;
    @Bind(R.id.cancel_order_btn)
    TextView cancelOrderBtn;
    @Bind(R.id.message_remind_str)
    TextView messageRemindStr;
    @Bind(R.id.darkening_background_layout)
    LinearLayout darkeningBackgroundLayout;
    @Bind(R.id.activity_order_payment)
    RelativeLayout activityOrderPayment;
    private int payType = 1;//1微信，2支付宝
    private String orderNum;
    private int state;
    private int workdeskType;
    private RequestCreatShortRentOrderBean.ResultsBean resultsBean;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private int payStatus;
    private int paymentTime;
    private TimeCount time;
    private static final String[] CANCEL_REASON_STR = new String[]{"我要重新预定", "下错订单", "不需要预定了", "其他"};
    private LayoutInflater mInflater;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_payment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbarContentText.setText("订单支付");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initData();
        switchBtnOne.setOnCheckedChangeListener(checkboxlister1);
        switchBtnTwo.setOnCheckedChangeListener(checkboxlister2);
        switchBtnOne.setChecked(true);
    }

    private CheckBox.OnCheckedChangeListener checkboxlister1 = new CheckBox.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub
            //在这里进行你需要的逻辑
            if (switchBtnOne.isChecked()) {
                payType = 1;
            }
            if (payType == 1) {
                switchBtnTwo.setChecked(false);
                switchBtnOne.setChecked(true);
            } else {
                switchBtnOne.setChecked(false);
                switchBtnTwo.setChecked(true);
            }
        }

    };
    private CheckBox.OnCheckedChangeListener checkboxlister2 = new CheckBox.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub
            //在这里进行你需要的逻辑
            if (switchBtnTwo.isChecked()) {
                payType = 2;
            }
            if (payType == 1) {
                switchBtnTwo.setChecked(false);
                switchBtnOne.setChecked(true);
            } else {
                switchBtnOne.setChecked(false);
                switchBtnTwo.setChecked(true);
            }
        }

    };

    private void initData() {
        resultsBean = (RequestCreatShortRentOrderBean.ResultsBean) getIntent().getSerializableExtra(KEY_CREATE_ORDER_RESULTSBEAN);
        orderNum = resultsBean.getOrderNo();
        orderNumber.setText("订单编号：" + orderNum);
        orderTime.setText(resultsBean.getCreatAt());
        state = resultsBean.getState();
        if (state == 3) {
            orderState.setText("等待支付");
        }
        workdeskType = resultsBean.getWorkDeskType();
        int failureAt = resultsBean.getFailureAt();
        StartCountDown(failureAt);
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


    private void goActivity(Class<?> cls, RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
        Intent orderIntent = new Intent();
        orderIntent.setClass(mContext, cls);
        orderIntent.putExtra(OrdersDetailActivity.KEY_ORDER_NUMBER, String.valueOf(resultsBean.getOrderNo()));
        startActivity(orderIntent);
        if (null != time) {
            time.cancel();
        }
        finish();
    }

    @OnClick({R.id.cancel_order_btn, R.id.order_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_order_btn://取消订单
                showCancelPop();
                break;
            case R.id.order_create://提交订单
                submitOrder();
                break;
            default:
                break;
        }
    }

    private TextView cancel_reason_one;
    private TextView cancel_reason_two;
    private TextView cancel_reason_three;
    private TextView cancel_reason_four;
    private String cancelReason = CANCEL_REASON_STR[0];
    private PopupWindow cancelPopupWindow;

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
                cancelShortRentOrder();
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
                WXPayEntryActivity.this, cancelPopupWindow, cancelView, screeWidth,
                220, activityOrderPayment);
        CommonUtil.setPopupWindowListener(new CommonUtil.PopupWindowListener() {

            @Override
            public void myDissmiss() {
                cancelPopupWindow = null;
                darkeningBackgroundLayout.setVisibility(View.GONE);
            }

        });
    }

    private void submitOrder() {
        UnifieOrderBean unifieOrderBean = new UnifieOrderBean();
        unifieOrderBean.setBody(resultsBean.getOfficespaceBasicinfo().getSpaceCnName());
        unifieOrderBean.setOut_trade_no(String.valueOf(resultsBean.getOrderNo()));
        unifieOrderBean.setTotal_fee(String.valueOf(1));
        //unifieOrderBean.setTotal_fee(String.valueOf(resultsBean.getPayMoney()*100));
        unifieOrderBean.setTrade_type("APP");
        unifieOrderBean.setSpbill_create_ip(IpUtils.getIp(mContext));
        unifieOrderBean.setNotify_url(Constants.NOTIFY_URL);
        ServiceFactory.getProvideHttpService().getUnifiedorder(unifieOrderBean)
                .compose(this.<WXPayProviderBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<WXPayProviderBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<WXPayProviderBean, Boolean>() {
                    @Override
                    public Boolean call(WXPayProviderBean wxPayProviderBean) {
                        if (wxPayProviderBean.getStatusCode() == Constants.STATUS_CODE_ERROR) {
                            ToastUtil.makeText(mContext, wxPayProviderBean.getMsg());
                        }
                        return wxPayProviderBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<WXPayProviderBean, WXPayProviderBean.ResultsBean>() {
                    @Override
                    public WXPayProviderBean.ResultsBean call(WXPayProviderBean wXPayProviderBean) {
                        return wXPayProviderBean.getResults();
                    }
                })
                .subscribe(new Action1<WXPayProviderBean.ResultsBean>() {
                    @Override
                    public void call(WXPayProviderBean.ResultsBean resultsBean) {

                        //处理返回结果
                        WXPayOrder(resultsBean);
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

    private void WXPayOrder(WXPayProviderBean.ResultsBean resultsBean) {
        paymentTime = Integer.parseInt(resultsBean.getTimestamp());
        PayReq req = new PayReq();
        req.appId = Constants.APP_ID;
        req.partnerId = resultsBean.getMch_id();
        req.prepayId = resultsBean.getPrepay_id();
        req.nonceStr = resultsBean.getNonce_str();
        req.timeStamp = resultsBean.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = resultsBean.getSign();
        req.extData = "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    // 取消订单
    private void cancelShortRentOrder() {
        RequestPaymentOrder requestPaymentOrder = new RequestPaymentOrder();
        requestPaymentOrder.setOrderNo(orderNum);
        requestPaymentOrder.setState(state);
        requestPaymentOrder.setRefundDesc(cancelReason);
        requestPaymentOrder.setWorkDeskType(workdeskType);
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
                        if (requestCreatShortRentOrderBean.getStatusCode() == Constants.STATUS_CODE_ERROR) {
                            ToastUtil.makeText(mContext, requestCreatShortRentOrderBean.getMsg());
                        }
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
                        goActivity(OrdersDetailActivity.class, resultsBean);
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

    private void queryOrder() {
        UnifieOrderBean unifieOrderBean = new UnifieOrderBean();
        unifieOrderBean.setOrderNo(String.valueOf(resultsBean.getOrderNo()));
        ServiceFactory.getProvideHttpService().orderQuery(unifieOrderBean)
                .compose(this.<ResultOrderQueryBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<ResultOrderQueryBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .filter(new Func1<ResultOrderQueryBean, Boolean>() {
                    @Override
                    public Boolean call(ResultOrderQueryBean wxPayProviderBean) {
                        return payBooleanSucceed(wxPayProviderBean);
                    }
                })
                .map(new Func1<ResultOrderQueryBean, ResultOrderQueryBean.ResultsBean>() {
                    @Override
                    public ResultOrderQueryBean.ResultsBean call(ResultOrderQueryBean wXPayProviderBean) {
                        return wXPayProviderBean.getResults();
                    }
                })
                .subscribe(new Action1<ResultOrderQueryBean.ResultsBean>() {
                    @Override
                    public void call(ResultOrderQueryBean.ResultsBean result) {
                        //处理返回结果
                        orderState.setText("支付成功");
                        messageRemindStr.setVisibility(View.GONE);
                        if (null != time) {
                            time.cancel();
                        }
                        goActivity(OrdersDetailActivity.class, resultsBean);
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "支付失败！");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                    }
                });
    }


    private boolean payBooleanSucceed(ResultOrderQueryBean resultOrderQueryBean) {
        if (resultOrderQueryBean.getStatusCode() == Constants.STATUS_CODE_ERROR) {
            if (null != time) {
                time.cancel();
            }
            goActivity(OrdersDetailActivity.class, resultsBean);
            finish();
        }
        return resultOrderQueryBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //支付返回调用
            queryOrder();
        }
    }

    private void StartCountDown(int failureAt) {
        messageRemindStr.setVisibility(View.VISIBLE);
        time = new TimeCount(failureAt * 1000, 1000);
        time.start();// 开始计时
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {
            //倒计时完毕
            messageRemindStr.setVisibility(View.GONE);
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
        StatService.onPageEnd(mContext,"支付页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"支付页");
    }
}
