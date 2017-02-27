package com.or.goodlive.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.activity.other.WebViewActivity;
import com.or.goodlive.ui.adapter.CoverAdapter;
import com.or.goodlive.ui.adapter.YamingChildAdapter;
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
 * Created by Administrator on 2017/2/13.
 */

public class CoverFragment extends BaseFragment {

    @Bind(R.id.rcv_cover_list)
    RecyclerView rcvCoverList;
    @Bind(R.id.swipe_refresh_cover)
    SwipeRefreshLayout swipeRefreshCover;
    @Bind(R.id.banner_home_page_view)
    LoopViewPager bannerHomePageView;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.header)
    RecyclerViewHeader header;
    private Integer category_id = 1;
    private Integer pageId = 1;
    private Integer count = 10;
    private CoverAdapter adapter;
    private Handler handler;
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<CoverDataBean.RstBean.ListBean> listBeen;
    private YamingChildAdapter yamingChildAdapter;
    public  String categoryName="cover";
    public static CoverFragment newInstance() {
        Bundle args = new Bundle();
        CoverFragment fragment = new CoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cover_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        Map<String, Integer> params = new HashMap<>();
        params.put("category_id", category_id);
        params.put("pageId", pageId);
        params.put("count", count);
        ServiceFactory.getProvideHttpService().getCoverList(params)
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
            yamingChildAdapter = new YamingChildAdapter(R.layout.item_yaming_list, listBeen);
            rcvCoverList.setAdapter(yamingChildAdapter);
        } else {
            yamingChildAdapter.notifyDataSetChanged();
        }
        if (listBeen.size() == 0) {
            yamingChildAdapter.setEmptyView(setEmptyDataView(R.drawable.iconfont_no_data,"暂无数据！"));
        }
    }

    private void initBannerView(CoverDataBean.RstBean rstBean) {
        List<CoverDataBean.RstBean.HomeactBean> datasBannerList = rstBean.getHomeact();
        if(datasBannerList.size()>0){
            BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(mContext);
            bannerPicAdapter.setData(datasBannerList,categoryName);
            bannerHomePageView.setAdapter(bannerPicAdapter);
            bannerHomePageView.setLooperPic(true);
            indicator.setViewPager(bannerHomePageView);
        }
    }
    private void initView() {
        handler = new Handler();
        listBeen = new ArrayList<>();
        rcvCoverList.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(rcvCoverList, true);
        swipeRefreshCover.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listBeen.clear();
                        pageIndex = 1;
                        initData();
                        swipeRefreshCover.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        rcvCoverList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = Constants.WEB_VIEW_HOSTURL+"type=cover"+"&id="+listBeen.get(position).getId();
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_VIEW_URL, url);
                intent.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, String.valueOf(listBeen.get(position).getId()));
                intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, "cover");
                intent.putExtra(WebViewActivity.WEB_VIEW_DESC,listBeen.get(position).getCategory_name() );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_right_image, menu);
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

