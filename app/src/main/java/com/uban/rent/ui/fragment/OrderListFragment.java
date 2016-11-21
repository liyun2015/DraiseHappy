package com.uban.rent.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.uban.rent.R;
import com.uban.rent.base.BaseFragment;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.request.RequestRentOrderList;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.adapter.UserOrdersAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by Unan on 2016/11/21.
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
    private List<String> listBeen;
    private UserOrdersAdapter userOrdersAdapter;
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
    private void initData(int workDeskType) {
        RequestRentOrderList requestRentOrderList = new RequestRentOrderList();
        requestRentOrderList.setWorkDeskTypes(workDeskType);
        ServiceFactory.getProvideHttpService().getShortRentOrder(requestRentOrderList)
                .compose(RxSchedulersHelper.<String>io_main())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        for (int i = 0; i < 20; i++) {
            listBeen.add(i+"");
        }
        initAdapter(listBeen);
    }

    private void initView() {
        userOrdersAdapter = new UserOrdersAdapter(mContext, listBeen);
        lvUserOrder.setAdapter(userOrdersAdapter);
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
    }
    private void initAdapter(List<String> datasBeanList) {

        List<String> list = new ArrayList<>();
        list.addAll(datasBeanList);
        currentPage++;
        listBeen.addAll(list);
        userOrdersAdapter.changeData(listBeen);
        swipeRefreshUserOrder.setRefreshing(false);
    }

}
