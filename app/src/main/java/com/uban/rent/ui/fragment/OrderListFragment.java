package com.uban.rent.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uban.rent.R;
import com.uban.rent.base.BaseFragment;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.OrderListBean;
import com.uban.rent.module.request.RequestRentOrderList;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.activity.order.OrdersDetailActivity;
import com.uban.rent.ui.adapter.OrdersListAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * OrderListFragment 订单列表
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class OrderListFragment extends BaseFragment {
    public static final String KEY_TITLE = "title";
    @Bind(R.id.lv_user_order)
    ListView lvUserOrder;
    @Bind(R.id.swipe_refresh_user_order)
    SwipeRefreshLayout swipeRefreshUserOrder;
    private static final String[] TITLE_NAME = new String[]{"全部", "移动办公", "会议/活动"};
    private int workDeskType;
    private Handler handler;
    private List<OrderListBean.ResultsBean> listBeen;
    private OrdersListAdapter ordersListAdapter;
    private int currentPage = 1;
    private static final int KEY_ORDER_ALL = 0;
    private static final int KEY_MOBILE_OFFICE = 1;
    private static final int KEY_MEETIONGS_EVENTS = 2;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_all;
    }

    public static OrderListFragment newInstance(int workDeskType) {
        Bundle args = new Bundle();
        OrderListFragment fragment = new OrderListFragment();
        args.putInt(KEY_TITLE,workDeskType);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        workDeskType = bundle.getInt(KEY_TITLE);
        listBeen = new ArrayList<>();
        handler = new Handler();
        initView();
        initData(workDeskType);

    }
    private void initView() {
        ordersListAdapter = new OrdersListAdapter(mContext, listBeen);
        lvUserOrder.setAdapter(ordersListAdapter);
        swipeRefreshUserOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listBeen.clear();
                        initData(workDeskType);
                        swipeRefreshUserOrder.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        lvUserOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OrderListBean.ResultsBean resultsBean = listBeen.get(position);
                Intent intent = new Intent();
                intent.setClass(mContext, OrdersDetailActivity.class);
                intent.putExtra(OrdersDetailActivity.KEY_ORDER_NO,resultsBean.getOrderNo());
                mContext.startActivity(intent);
            }
        });
    }
    private void initData(int workDeskType) {
        RequestRentOrderList requestRentOrderList = new RequestRentOrderList();
        requestRentOrderList.setWorkDeskTypes(workDeskType);
        ServiceFactory.getProvideHttpService().getShortRentOrder(requestRentOrderList)
                .compose(this.<OrderListBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<OrderListBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<OrderListBean, Boolean>() {
                    @Override
                    public Boolean call(OrderListBean orderListBean) {
                        return orderListBean.getStatusCode()== Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .subscribe(new Action1<OrderListBean>() {
                    @Override
                    public void call(OrderListBean orderListBean) {
                        initAdapter(orderListBean.getResults());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadingView();
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error) + throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }


    private void initAdapter(List<OrderListBean.ResultsBean> datasBeanList) {
        List<OrderListBean.ResultsBean> list = new ArrayList<>();
        list.addAll(datasBeanList);
        currentPage++;
        listBeen.addAll(list);
        ordersListAdapter.changeData(listBeen);
        swipeRefreshUserOrder.setRefreshing(false);
    }

}
