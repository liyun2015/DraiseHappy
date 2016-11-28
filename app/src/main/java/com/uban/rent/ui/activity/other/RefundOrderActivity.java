package com.uban.rent.ui.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestPaymentOrder;
import com.uban.rent.ui.activity.order.OrdersDetailActivity;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.wxapi.WXPayEntryActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 申请退款页面
 */
public class RefundOrderActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bottom_submit_btn)
    TextView bottomSubmitBtn;
    @Bind(R.id.refund_cause_group)
    RadioGroup refundCauseGroup;
    private String refundReason;
    private String orderNum;
    private int state;
    private int workdeskType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_order;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbarContentText.setText("申请退款");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        refundCauseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //获取变更后的选中项的ID
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton radioButton = (RadioButton)RefundOrderActivity.this.findViewById(radioButtonId);
                refundReason = String.valueOf(radioButton.getText());
            }
        });
        orderNum = getIntent().getStringExtra(OrdersDetailActivity.KEY_ORDER_NUMBER);
        state = getIntent().getIntExtra(OrdersDetailActivity.KEY_ORDER_STATE,0);
        workdeskType = getIntent().getIntExtra(OrdersDetailActivity.KEY_WORKDESKTYPE,0);
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

    @OnClick(R.id.bottom_submit_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_submit_btn://提交
                refundOrder();
                break;
            default:
                break;
        }
    }

    private void refundOrder() {
        RequestPaymentOrder requestPaymentOrder =new RequestPaymentOrder();
        requestPaymentOrder.setOrderNo(orderNum);
        requestPaymentOrder.setState(state);
        requestPaymentOrder.setWorkDeskType(workdeskType);
        requestPaymentOrder.setRefundDesc(refundReason);
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
                        //处理返回结果==退款成功
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

    private void goActivity(Class<?> cls, RequestCreatShortRentOrderBean.ResultsBean resultsBean) {
        Intent orderIntent = new Intent();
        orderIntent.setClass(mContext, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WXPayEntryActivity.KEY_CREATE_ORDER_RESULTSBEAN, resultsBean);
        orderIntent.putExtras(bundle);
        startActivity(orderIntent);
    }
}
