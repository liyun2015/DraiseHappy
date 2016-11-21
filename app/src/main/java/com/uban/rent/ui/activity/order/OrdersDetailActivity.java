package com.uban.rent.ui.activity.order;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestOrderDetailBean;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.uban.rent.ui.activity.order.OrderPaymentActivity.KEY_CREATE_ORDER_RESULTSBEAN;

/**
 * 订单详情页
 */
public class OrdersDetailActivity extends BaseActivity {
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
    @Bind(R.id.price_of_hour)
    TextView priceOfHour;
    @Bind(R.id.price_of_type)
    TextView priceOfType;
    @Bind(R.id.station_str)
    TextView stationStr;

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

    private void initData() {
        RequestOrderDetailBean requestOrderDetailBean=new RequestOrderDetailBean();
        requestOrderDetailBean.setOrderNo("12336");
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
                        int status = resultsBean.getPayStatus();
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
