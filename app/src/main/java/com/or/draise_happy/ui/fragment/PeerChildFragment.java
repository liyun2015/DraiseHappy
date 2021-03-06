package com.or.draise_happy.ui.fragment;

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
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.or.draise_happy.R;
import com.or.draise_happy.api.config.ServiceFactory;
import com.or.draise_happy.base.BaseFragment;
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.CoverDataBean;
import com.or.draise_happy.ui.activity.other.WebViewActivity;
import com.or.draise_happy.ui.adapter.YamingChildAdapter;
import com.or.draise_happy.ui.view.CustomLoadMoreView;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.ui.view.banner.BannerPicAdapter;
import com.or.draise_happy.ui.view.banner.CircleIndicator;
import com.or.draise_happy.ui.view.banner.LoopViewPager;
import com.or.draise_happy.util.Constants;

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

public class PeerChildFragment extends BaseFragment {
    @Bind(R.id.rcv_peer_child_list)
    RecyclerView rcvPeerChildList;
    @Bind(R.id.swipe_refresh_peer_child)
    SwipeRefreshLayout swipeRefreshPeerChild;
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
        return R.layout.fragment_peer_child;
    }

    public static PeerChildFragment newInstance(int columnType, String name) {
        Bundle args = new Bundle();
        PeerChildFragment fragment = new PeerChildFragment();
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

        rcvPeerChildList.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(rcvPeerChildList, true);
        swipeRefreshPeerChild.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore=false;
                        isDownFresh=true;
                        pageId = 1;
                        initData();
                        swipeRefreshPeerChild.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        rcvPeerChildList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = Constants.WEB_VIEW_HOSTURL + "type=news" + "&id=" + listBeen.get(position).getId();
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_VIEW_URL, url);
                intent.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, String.valueOf(listBeen.get(position).getId()));
                intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, "news");
                intent.putExtra(WebViewActivity.WEB_VIEW_TITLE_NAME, listBeen.get(position).getTitle());
                intent.putExtra(WebViewActivity.WEB_VIEW_CONTENT_NAME, listBeen.get(position).getSub());
                intent.putExtra(WebViewActivity.WEB_VIEW_DESC, listBeen.get(position).getCategory_name());
                intent.putExtra(WebViewActivity.WEB_VIEW_FAVOR_STATE, listBeen.get(position).getIs_like());
                intent.putExtra(WebViewActivity.WEB_VIEW_PIC, listBeen.get(position).getTitle_pic());
                intent.putExtra(WebViewActivity.WEB_VIEW_COMMENT_NUM, String.valueOf(listBeen.get(position).getComment_num()));
                mContext.startActivity(intent);
            }
        });
        yamingChildAdapter = new YamingChildAdapter(R.layout.item_yaming_list, listBeen);
        rcvPeerChildList.setAdapter(yamingChildAdapter);
        yamingChildAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rcvPeerChildList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore=true;
                        isDownFresh=false;
                        pageId=pageId+1;
                        initData();
                    }
                }, 1000);
            }
        },rcvPeerChildList);
        yamingChildAdapter.setLoadMoreView(new CustomLoadMoreView());
    }
    private void initData() {
        Map<String, Integer> params = new HashMap<>();
        params.put("category_id", titleId);
        params.put("pageId", pageId);
        params.put("count", count);
        ServiceFactory.getProvideHttpService().getNewsList(params)
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
            rcvPeerChildList.setAdapter(yamingChildAdapter);
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
            bannerTopView.setVisibility(View.VISIBLE);
            BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(mContext);
            bannerPicAdapter.setData(datasBannerList);
            if(isDownFresh){
                bannerHomePageView.removeAllViews();
            }
            bannerHomePageView.setAdapter(bannerPicAdapter);
            bannerHomePageView.setLooperPic(true);
            indicator.setViewPager(bannerHomePageView);
        }else{
            bannerTopView.setVisibility(View.GONE);
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
