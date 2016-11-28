package com.uban.rent.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.baidu.location.b.g.C;

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
    @Bind(R.id.activity_order_payment)
    RelativeLayout activityOrderPayment;
    @Bind(R.id.switch_btn_one)
    CheckBox switchBtnOne;
    @Bind(R.id.switch_btn_two)
    CheckBox switchBtnTwo;
    @Bind(R.id.cancel_order_btn)
    TextView cancelOrderBtn;
    private int payType = 1;//1微信，2支付宝
    private String orderNum;
    private int state;
    private int workdeskType;
    private RequestCreatShortRentOrderBean.ResultsBean resultsBean;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private int payStatus;
    private int paymentTime;

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
        orderNumber.setText("订单编号："+orderNum);
        orderTime.setText(resultsBean.getCreatAt());
        state = resultsBean.getState();
        if (state == 3) {
            orderState.setText("订单状态：等待支付");
        }
        workdeskType = resultsBean.getWorkDeskType();
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


    private void goActivity(Class<?> cls,RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
        Intent orderIntent = new Intent();
        orderIntent.setClass(mContext,cls);
        orderIntent.putExtra(OrdersDetailActivity.KEY_ORDER_NUMBER,String.valueOf(resultsBean.getOrderNo()));
        startActivity(orderIntent);
    }

    @OnClick({R.id.cancel_order_btn, R.id.order_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_order_btn://取消订单
                cancelShortRentOrder();
                break;
            case R.id.order_create://提交订单
                submitOrder();
                break;
            default:
                break;
        }
    }

    private void submitOrder() {
        UnifieOrderBean unifieOrderBean =new UnifieOrderBean();
        unifieOrderBean.setBody(resultsBean.getOfficespaceBasicinfo().getSpaceCnName());
        unifieOrderBean.setOut_trade_no(String.valueOf(resultsBean.getOrderNo()));
        unifieOrderBean.setTotal_fee(String.valueOf(1));
        //unifieOrderBean.setTotal_fee(String.valueOf(resultsBean.getPayMoney()*100));
        unifieOrderBean.setTrade_type("APP");
        unifieOrderBean.setSpbill_create_ip(CommonUtil.getLoginIp(mContext));
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
                        if(wxPayProviderBean.getStatusCode() == Constants.STATUS_CODE_ERROR){
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
        req.partnerId		= resultsBean.getMch_id();
        req.prepayId		= resultsBean.getPrepay_id();
        req.nonceStr		= resultsBean.getNonce_str();
        req.timeStamp		= resultsBean.getTimestamp();
        req.packageValue	= "Sign=WXPay";
        req.sign			= resultsBean.getSign();
        req.extData			= "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    // 取消订单
    private void cancelShortRentOrder() {
        RequestPaymentOrder requestPaymentOrder =new RequestPaymentOrder();
        requestPaymentOrder.setOrderNo(orderNum);
        requestPaymentOrder.setState(state);
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
                        if(requestCreatShortRentOrderBean.getStatusCode() == Constants.STATUS_CODE_ERROR){
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
                        goActivity(OrdersDetailActivity.class,resultsBean);
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

    private void queryOrder() {
        UnifieOrderBean unifieOrderBean =new UnifieOrderBean();
        unifieOrderBean.setOrderNo(String.valueOf(resultsBean.getOrderNo()));
        ServiceFactory.getProvideHttpService().orderQuery(unifieOrderBean)
                .compose(this.<ResultOrderQueryBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<ResultOrderQueryBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
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
                    public void call(ResultOrderQueryBean.ResultsBean resultsBean) {
                        //处理返回结果
                        orderState.setText("订单状态：支付成功");
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


    private boolean payBooleanSucceed(ResultOrderQueryBean resultOrderQueryBean) {
        if (resultOrderQueryBean.getStatusCode()==Constants.STATUS_CODE_ERROR){
            goActivity(OrdersDetailActivity.class,resultsBean);
            finish();
        }
        return resultOrderQueryBean.getStatusCode()==Constants.STATUS_CODE_SUCCESS;
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
}
