package com.uban.rent.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseFragment;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.OrderListBean;
import com.uban.rent.module.request.RequestRentOrderList;
import com.uban.rent.ui.activity.order.OrdersDetailActivity;
import com.uban.rent.ui.adapter.OrdersListAdapter;
import com.uban.rent.ui.view.GetMoreListView;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * OrderAllMobileWorkFragment 订单列表
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class OrderAllFragment extends BaseFragment {
    public static final String KEY_TITLE = "title";
    @Bind(R.id.lv_user_order)
    GetMoreListView lvUserOrder;
    @Bind(R.id.swipe_refresh_user_order)
    SwipeRefreshLayout swipeRefreshUserOrder;
    private int workDeskType;
    private Handler handler;
    private List<OrderListBean.ResultsBean.DatasBean> listBeen;
    private OrdersListAdapter ordersListAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_all;
    }
    public static OrderAllFragment newInstance(int workDeskType) {
        Bundle args = new Bundle();
        OrderAllFragment fragment = new OrderAllFragment();
        args.putInt(KEY_TITLE,workDeskType);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        listBeen = new ArrayList<>();
        handler = new Handler();
        Bundle bundle = getArguments();
        if (bundle!=null){
            workDeskType = bundle.getInt(KEY_TITLE);
        }
        isPrepared = true;
        initView();
        initData(workDeskType);
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }*/
   /* protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        initData(workDeskType);
    }*/



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
                        pageIndex = 1;
                        initData(workDeskType);
                        lvUserOrder.setHasMore();
                        swipeRefreshUserOrder.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        lvUserOrder.setOnGetMoreListener(new GetMoreListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData(workDeskType);
                    }
                }, 1000);
            }
        });

        lvUserOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OrderListBean.ResultsBean.DatasBean resultsBean = listBeen.get(position);
                Intent intent = new Intent();
                intent.setClass(mContext, OrdersDetailActivity.class);
                intent.putExtra(OrdersDetailActivity.KEY_ORDER_NUMBER,resultsBean.getOrderNo());
                mContext.startActivity(intent);
            }
        });
    }
    private void initData(int workDeskType) {
        RequestRentOrderList requestRentOrderList = new RequestRentOrderList();
        requestRentOrderList.setWorkDeskTypes(workDeskType);
        requestRentOrderList.setPageIndex(pageIndex);
        requestRentOrderList.setPageSize(pageSize);
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
                .map(new Func1<OrderListBean, OrderListBean.ResultsBean>() {
                    @Override
                    public OrderListBean.ResultsBean call(OrderListBean orderListBean) {
                        return orderListBean.getResults();
                    }
                })
                .subscribe(new Action1<OrderListBean.ResultsBean>() {
                    @Override
                    public void call(OrderListBean.ResultsBean resultsBean) {
                        mHasLoadedOnce = true;
                        initAdapter(resultsBean.getDatas());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        hideLoadingView();
                        swipeRefreshUserOrder.setRefreshing(false);
                        lvUserOrder.getMoreComplete();
                        Log.e(TAG,throwable.getMessage());
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error) );
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }


    private void initAdapter(List<OrderListBean.ResultsBean.DatasBean> datasBeanList) {
        List<OrderListBean.ResultsBean.DatasBean> list = new ArrayList<>();
        list.addAll(datasBeanList);
        if (list.size()<10){
            lvUserOrder.setNoMore();
        }else {
            lvUserOrder.setHasMore();
        }
        pageIndex++;
        listBeen.addAll(list);
        ordersListAdapter.changeData(listBeen);
        lvUserOrder.getMoreComplete();
        swipeRefreshUserOrder.setRefreshing(false);
    }

}
