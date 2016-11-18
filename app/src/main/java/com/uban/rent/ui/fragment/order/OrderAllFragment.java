package com.uban.rent.ui.fragment.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.uban.rent.R;
import com.uban.rent.base.BaseFragment;
import com.uban.rent.ui.adapter.UserOrdersAdapter;
import com.uban.rent.ui.view.GetMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderAllFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";

    @Bind(R.id.lv_user_order)
    GetMoreListView lvUserOrder;
    @Bind(R.id.swipe_refresh_user_order)
    SwipeRefreshLayout swipeRefreshUserOrder;

    private Handler handler;
    private String mParam1;
    private List<String> listBeen;
    private UserOrdersAdapter userOrdersAdapter;
    private int currentPage = 1;
    public static OrderAllFragment newInstance(String param1) {
        OrderAllFragment fragment = new OrderAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_all;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        listBeen = new ArrayList<>();
        handler = new Handler();
        initView();
        initData();
    }

    private void initData() {

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        for (int i = 0; i < 20; i++) {
            listBeen.add(i+"all");
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
                        swipeRefreshUserOrder.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }
    private void initAdapter(List<String> datasBeanList) {

        List<String> list = new ArrayList<>();
        list.addAll(datasBeanList);
        if (list.size() < 5) {
            lvUserOrder.setNoMore();
        } else {
            lvUserOrder.setHasMore();
        }
        currentPage++;
        listBeen.addAll(list);
        if (listBeen.size() <= 0) {
            //showEmptyView(true);
        }else {
          //  showEmptyView(false);
        }
        userOrdersAdapter.changeData(listBeen);
        lvUserOrder.getMoreComplete();
        swipeRefreshUserOrder.setRefreshing(false);
    }
}
