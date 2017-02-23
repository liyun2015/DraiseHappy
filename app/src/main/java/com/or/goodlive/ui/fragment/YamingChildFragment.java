package com.or.goodlive.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.adapter.YamingChildAdapter;
import com.or.goodlive.ui.view.GetMoreListView;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.ui.view.banner.BannerPicAdapter;
import com.or.goodlive.ui.view.banner.CircleIndicator;
import com.or.goodlive.ui.view.banner.LoopViewPager;
import com.or.goodlive.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/16.
 */

public class YamingChildFragment extends BaseFragment {
    @Bind(R.id.rcv_yaming_child_list)
    RecyclerView rcvYamingChildList;
    @Bind(R.id.swipe_refresh_yaming_child)
    SwipeRefreshLayout swipeRefreshYamingChild;
    @Bind(R.id.banner_home_page_view)
    LoopViewPager bannerHomePageView;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.header)
    RecyclerViewHeader header;
    private Handler handler;
    private List<CoverDataBean.RstBean.ListBean> listBeen;
    private YamingChildAdapter yamingChildAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private Integer pageId = 1;
    private Integer count = 10;
    public static final String KEY_TITLE = "titleid";
    public static final String NAME_TITLE = "titlename";
    private int titleId;
    public  String titleName="";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yaming_child;
    }

    public static YamingChildFragment newInstance(int columnType,String name) {
        Bundle args = new Bundle();
        YamingChildFragment fragment = new YamingChildFragment();
        args.putInt(KEY_TITLE,columnType);
        args.putString(NAME_TITLE,name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        listBeen = new ArrayList<>();
        handler = new Handler();
        Bundle bundle = getArguments();
        if (bundle!=null){
            titleId = bundle.getInt(KEY_TITLE);
            titleName = bundle.getString(NAME_TITLE);
        }
        initView();
        initData();
    }

    private void initView() {
        rcvYamingChildList.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(rcvYamingChildList, true);
        swipeRefreshYamingChild.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listBeen.clear();
                        pageIndex = 1;
                        initData();
                        swipeRefreshYamingChild.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        rcvYamingChildList.addOnItemTouchListener(new OnItemClickListener() {
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

    private void initData() {
        Map<String, Integer> params = new HashMap<>();
        params.put("category_id", titleId);
        params.put("pageId", pageId);
        params.put("count", count);
        ServiceFactory.getProvideHttpService().getYimingList(params)
                .compose(RxSchedulersHelper.<CoverDataBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<CoverDataBean, Boolean>() {
                    @Override
                    public Boolean call(CoverDataBean coverDataBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(coverDataBean.getErrno())) {
                            ToastUtil.makeText(mContext, coverDataBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(coverDataBean.getErrno());
                    }
                })
                .subscribe(new Action1<CoverDataBean>() {
                    @Override
                    public void call(CoverDataBean coverDataBean) {
                        if (null != coverDataBean.getRst().getHomeact()) {
                            initListData(coverDataBean.getRst());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }


    private void initListData(CoverDataBean.RstBean rstBean) {
        initBannerView(rstBean);
        initListViewData(rstBean);
    }
    private void initListViewData(CoverDataBean.RstBean rstBean) {
        List<CoverDataBean.RstBean.ListBean> datasList = rstBean.getList();

        listBeen.addAll(datasList);

        if (null == yamingChildAdapter) {
            yamingChildAdapter = new YamingChildAdapter(R.layout.item_yaming_list, listBeen,titleName);
            rcvYamingChildList.setAdapter(yamingChildAdapter);
        } else {
            yamingChildAdapter.notifyDataSetChanged();
        }
        if (listBeen.size() == 0) {
            yamingChildAdapter.setEmptyView(setEmptyDataView(R.drawable.iconfont_no_data,"暂无数据！"));
        }
    }
    private void initBannerView(CoverDataBean.RstBean rstBean) {
        List<CoverDataBean.RstBean.HomeactBean> datasBannerList = rstBean.getHomeact();
        List<String> drawables = new ArrayList<>();
        if(datasBannerList.size()>0){
            for (int i = 0; i < datasBannerList.size(); i++) {
                drawables.add(datasBannerList.get(i).getPhoto());
            }
            BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(mContext);
            bannerPicAdapter.setData(drawables);
            bannerHomePageView.setAdapter(bannerPicAdapter);
            bannerHomePageView.setLooperPic(true);
            indicator.setViewPager(bannerHomePageView);
        }
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
