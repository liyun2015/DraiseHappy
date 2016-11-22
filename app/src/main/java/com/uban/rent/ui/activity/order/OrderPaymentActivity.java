package com.uban.rent.ui.activity.order;

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

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestPaymentOrder;
import com.uban.rent.module.request.RequestRentOrderList;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.TimeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class OrderPaymentActivity extends BaseActivity {
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
    private int payType = 1;//1支付宝，2微信
    private String orderNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_payment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
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
        RequestCreatShortRentOrderBean.ResultsBean resultsBean = (RequestCreatShortRentOrderBean.ResultsBean) getIntent().getSerializableExtra(KEY_CREATE_ORDER_RESULTSBEAN);
        orderNum = resultsBean.getOrderNo();
        orderNumber.setText("订单标号："+orderNum);
        orderTime.setText(resultsBean.getCreatAt());
        int state = resultsBean.getState();
        if (state == 3) {
            orderState.setText("等待支付");
        }
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


    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }

    @OnClick({R.id.cancel_order_btn, R.id.order_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_order_btn://取消订单
                cancelShortRentOrder();
                break;
            case R.id.order_create://提交订单
                paymentShortRentOrder();
                break;
            default:
                break;
        }
    }

    private void cancelShortRentOrder() {
        RequestPaymentOrder requestPaymentOrder =new RequestPaymentOrder();
        requestPaymentOrder.setDealPrice(2000.00);
        requestPaymentOrder.setOrderNo(orderNum);
        requestPaymentOrder.setPaymentAtString(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis()/1000),"yyyy-MM-dd"));
        requestPaymentOrder.setPayType(payType);
        requestPaymentOrder.setPayStatus(1);
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
                        int status = resultsBean.getPayStatus();
                        goActivity(OrdersDetailActivity.class);
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

    private void paymentShortRentOrder() {
        RequestPaymentOrder requestPaymentOrder =new RequestPaymentOrder();
        requestPaymentOrder.setDealPrice(2000.00);
        requestPaymentOrder.setOrderNo(orderNum);
        requestPaymentOrder.setPaymentAtString(TimeUtils.formatTime(String.valueOf(System.currentTimeMillis()/1000),"yyyy-MM-dd"));
        requestPaymentOrder.setPayType(payType);
        requestPaymentOrder.setPayStatus(1);
        ServiceFactory.getProvideHttpService().paymentShortRentOrder(requestPaymentOrder)
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
                        int status = resultsBean.getPayStatus();
                        goActivity(OrdersDetailActivity.class);
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
}
