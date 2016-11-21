package com.uban.rent.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }
    private void initData() {
        RequestCreatShortRentOrderBean.ResultsBean resultsBean = (RequestCreatShortRentOrderBean.ResultsBean) getIntent().getSerializableExtra(KEY_CREATE_ORDER_RESULTSBEAN);
        orderNumber.setText(resultsBean.getOrderNo());
        orderTime.setText(resultsBean.getCreatAt());
        int state = resultsBean.getState();
        if(state==3){
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

    @OnClick(R.id.order_create)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create://提交订单
                goActivity(OrdersDetailActivity.class);
                break;
            default:
                break;
        }
    }

    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }


}
