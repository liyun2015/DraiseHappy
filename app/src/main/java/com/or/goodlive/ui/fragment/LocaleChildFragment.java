package com.or.goodlive.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.or.goodlive.R;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.adapter.YamingChildAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/16.
 */

public class LocaleChildFragment extends BaseFragment {
    public static final String KEY_TITLE = "title";
    @Bind(R.id.rcv_yaming_locale_list)
    RecyclerView rcvYamingLocaleList;
    @Bind(R.id.swipe_refresh_locale_child)
    SwipeRefreshLayout swipeRefreshLocaleChild;

    private int shortRentFlag;
    private Handler handler;
    private List<CoverDataBean.RstBean.HomeactBean> listBeen;
    private YamingChildAdapter yamingChildAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_locale_child;
    }

    public static LocaleChildFragment newInstance(int workDeskType) {
        Bundle args = new Bundle();
        LocaleChildFragment fragment = new LocaleChildFragment();
        args.putInt(KEY_TITLE, workDeskType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        listBeen = new ArrayList<>();
        handler = new Handler();
        Bundle bundle = getArguments();
        if (bundle != null) {
            shortRentFlag = bundle.getInt(KEY_TITLE);
        }
        initView();
        initData(shortRentFlag);
    }

    private void initView() {
        rcvYamingLocaleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLocaleChild.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listBeen.clear();
                        pageIndex = 1;
                        initData(shortRentFlag);
                        swipeRefreshLocaleChild.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        rcvYamingLocaleList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                OrderListBean.ResultsBean.DatasBean resultsBean = listBeen.get(position);
//                Intent intent = new Intent();
//                intent.setClass(mContext, OrdersDetailActivity.class);
//                intent.putExtra(OrdersDetailActivity.KEY_ORDER_NUMBER,resultsBean.getOrderNo());
//                mContext.startActivity(intent);
            }
        });

    }

    private void initData(int shortRentFlag) {
        for (int i = 0; i < 2; i++) {
            CoverDataBean.RstBean.HomeactBean b = new CoverDataBean.RstBean.HomeactBean();
            listBeen.add(b);
        }
        yamingChildAdapter = new YamingChildAdapter(R.layout.item_yaming_list, listBeen);
        rcvYamingLocaleList.setAdapter(yamingChildAdapter);
//        RequestRentOrderList requestRentOrderList = new RequestRentOrderList();
//        requestRentOrderList.setShortRentFlag(shortRentFlag);
//        requestRentOrderList.setPageIndex(pageIndex);
//        requestRentOrderList.setPageSize(pageSize);
//        ServiceFactory.getProvideHttpService().getShortRentOrder(requestRentOrderList)
//                .compose(this.<OrderListBean>bindToLifecycle())
//                .compose(RxSchedulersHelper.<OrderListBean>io_main())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        showLoadingView();
//                    }
//                })
//                .filter(new Func1<OrderListBean, Boolean>() {
//                    @Override
//                    public Boolean call(OrderListBean orderListBean) {
//                        return orderListBean.getStatusCode()== Constants.STATUS_CODE_SUCCESS;
//                    }
//                })
//                .map(new Func1<OrderListBean, OrderListBean.ResultsBean>() {
//                    @Override
//                    public OrderListBean.ResultsBean call(OrderListBean orderListBean) {
//                        return orderListBean.getResults();
//                    }
//                })
//                .subscribe(new Action1<OrderListBean.ResultsBean>() {
//                    @Override
//                    public void call(OrderListBean.ResultsBean resultsBean) {
//                        initAdapter(resultsBean.getDatas());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        hideLoadingView();
//                        swipeRefreshUserOrder.setRefreshing(false);
//                        lvUserOrder.getMoreComplete();
//                        Log.e(TAG,throwable.getMessage());
//                        ToastUtil.makeText(mContext, getString(R.string.str_result_error) );
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//                        hideLoadingView();
//                    }
//                });
    }


    private void initAdapter(List<CoverDataBean.RstBean.HomeactBean> datasBeanList) {
//        List<OrderListBean.ResultsBean.DatasBean> list = new ArrayList<>();
//        list.addAll(datasBeanList);
//        if (list.size()<10){
//            lvUserOrder.setNoMore();
//        }else {
//            lvUserOrder.setHasMore();
//        }
//        pageIndex++;
//        listBeen.addAll(list);
//        ordersListAdapter.changeData(listBeen);
//        lvUserOrder.getMoreComplete();
//        swipeRefreshUserOrder.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}