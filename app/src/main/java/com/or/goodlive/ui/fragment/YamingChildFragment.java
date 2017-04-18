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
import android.widget.FrameLayout;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.activity.other.WebViewActivity;
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
    @Bind(R.id.banner_top_view)
    FrameLayout bannerTopView;
    private Handler handler;
    private List<CoverDataBean.RstBean.ListBean> listBeen;
    private YamingChildAdapter yamingChildAdapter;
    private int pageId = 1;
    private int count = 10;
    public static final String KEY_TITLE = "titleid";
    public static final String NAME_TITLE = "titlename";
    private int titleId;
    public String titleName = "";
    private boolean isDownFresh=false;
    private boolean isLoadMore=false;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yaming_child;
    }

    public static YamingChildFragment newInstance(int columnType, String name) {
        Bundle args = new Bundle();
        YamingChildFragment fragment = new YamingChildFragment();
        args.putInt(KEY_TITLE, columnType);
        args.putString(NAME_TITLE, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        listBeen = new ArrayList<>();
        handler = new Handler();
        Bundle bundle = getArguments();
        if (bundle != null) {
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
                        isLoadMore=false;
                        isDownFresh=true;
                        pageId = 1;
                        initData();
                        swipeRefreshYamingChild.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        rcvYamingChildList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = Constants.WEB_VIEW_HOSTURL + "type=yiming" + "&id=" + listBeen.get(position).getId();
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_VIEW_URL, url);
                intent.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, String.valueOf(listBeen.get(position).getId()));
                intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, "yiming");
                intent.putExtra(WebViewActivity.WEB_VIEW_TITLE_NAME, listBeen.get(position).getTitle());
                intent.putExtra(WebViewActivity.WEB_VIEW_CONTENT_NAME, listBeen.get(position).getSub());
                intent.putExtra(WebViewActivity.WEB_VIEW_DESC, listBeen.get(position).getTitle());
                intent.putExtra(WebViewActivity.WEB_VIEW_FAVOR_STATE, listBeen.get(position).getIs_like());
                intent.putExtra(WebViewActivity.WEB_VIEW_PIC, listBeen.get(position).getTitle_pic());
                intent.putExtra(WebViewActivity.WEB_VIEW_COMMENT_NUM, String.valueOf(listBeen.get(position).getComment_num()));
                mContext.startActivity(intent);
            }
        });
        yamingChildAdapter = new YamingChildAdapter(R.layout.item_yaming_list, listBeen);
        rcvYamingChildList.setAdapter(yamingChildAdapter);
        yamingChildAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rcvYamingChildList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore=true;
                        isDownFresh=false;
                        pageId=pageId+1;
                        initData();
                    }
                }, 1000);
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
                        if(!isDownFresh&&!isLoadMore){
                            showLoadingView();
                        }
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
        if(isDownFresh){
            listBeen.clear();
        }
        listBeen.addAll(datasList);

        if (null == yamingChildAdapter) {
            yamingChildAdapter = new YamingChildAdapter(R.layout.item_yaming_list, listBeen);
            rcvYamingChildList.setAdapter(yamingChildAdapter);
        } else {
            yamingChildAdapter.notifyDataSetChanged();
        }
        if (!rstBean.getPageInfo().isHasNext()) {
            //数据全部加完了
            yamingChildAdapter.loadMoreEnd();
        } else {
            yamingChildAdapter.loadMoreComplete();

        }
        if (listBeen.size() == 0) {
            yamingChildAdapter.setEmptyView(setEmptyDataView(R.drawable.iconfont_no_data, "暂无数据！"));
        }
    }

    private void initBannerView(CoverDataBean.RstBean rstBean) {
        List<CoverDataBean.RstBean.HomeactBean> datasBannerList = rstBean.getHomeact();
        if (datasBannerList.size() > 0) {
            bannerHomePageView.setVisibility(View.VISIBLE);
            BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(mContext);
            bannerPicAdapter.setData(datasBannerList);
            if(isDownFresh){
                bannerHomePageView.removeAllViews();
            }
            bannerHomePageView.setAdapter(bannerPicAdapter);
            bannerHomePageView.setLooperPic(true);
            indicator.setViewPager(bannerHomePageView);
        }else{
            bannerHomePageView.setVisibility(View.GONE);
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
