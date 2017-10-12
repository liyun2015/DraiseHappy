package com.or.draise_happy.ui.activity.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.or.draise_happy.R;
import com.or.draise_happy.api.config.ServiceFactory;
import com.or.draise_happy.base.BaseActivity;
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.MessResultsBean;
import com.or.draise_happy.ui.adapter.MessageAdapter;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.SPUtils;

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
 * Created by Administrator on 2017/2/18.
 */

public class MyMessageActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rcv_message_list)
    RecyclerView rcvMessageList;
    @Bind(R.id.swipe_refresh_message)
    SwipeRefreshLayout swipeRefreshMessage;
    private List<MessResultsBean.RstBean.ListBean> dataList = new ArrayList<>();
    private MessageAdapter meAdapter;
    private Handler handler;
    private int pageId = 1;
    private String count = "10";
    private boolean isDownFresh=false;
    private boolean isLoadMore=false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageId", String.valueOf(pageId));
        params.put("count", count);
        ServiceFactory.getProvideHttpService().messageList(params)
                .compose(this.<MessResultsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<MessResultsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(!isDownFresh&&!isLoadMore){
                            showLoadingView();
                        }
                    }
                })
                .filter(new Func1<MessResultsBean, Boolean>() {
                    @Override
                    public Boolean call(MessResultsBean messResultsBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno())) {
                            ToastUtil.makeText(mContext, messResultsBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno());
                    }
                })
                .map(new Func1<MessResultsBean, MessResultsBean.RstBean>() {
                    @Override
                    public MessResultsBean.RstBean call(MessResultsBean messResultsBean) {
                        return messResultsBean.getRst();
                    }
                })
                .subscribe(new Action1<MessResultsBean.RstBean>() {
                    @Override
                    public void call(MessResultsBean.RstBean resultsBean) {
                        if(resultsBean!=null){
                            initListView(resultsBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, " 数据加载失败！");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void initListView(MessResultsBean.RstBean resultsBean) {
        List<MessResultsBean.RstBean.ListBean> datasList = resultsBean.getList();
        if(isDownFresh){
            dataList.clear();
        }
        dataList.addAll(datasList);

        if (null == meAdapter) {
            meAdapter = new MessageAdapter(mContext,R.layout.item_message_list, dataList);
            rcvMessageList.setAdapter(meAdapter);
        } else {
            meAdapter.notifyDataSetChanged();
        }
        if (!resultsBean.getPageInfo().isHasNext()) {
            //数据全部加完了
            meAdapter.loadMoreEnd();
        } else {
            meAdapter.loadMoreComplete();

        }
        if (dataList.size() == 0) {
            meAdapter.setEmptyView(setEmptyDataView(R.drawable.iconfont_no_data,"暂无数据！"));
        }
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("消息");
        handler = new Handler();
        rcvMessageList.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshMessage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore=false;
                        isDownFresh=true;
                        pageId=1;
                        initData();
                        swipeRefreshMessage.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        rcvMessageList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(mContext, MessageDetailActivity.class);
                intent.putExtra(MessageDetailActivity.MESSAGE_TITLE, dataList.get(position).getTitle());
                intent.putExtra(MessageDetailActivity.MESSAGE_CONTENT, dataList.get(position).getContent());
                mContext.startActivity(intent);
                String flag =String.valueOf(dataList.get(position).getNews_id());
                SPUtils.put(mContext,flag, String.valueOf(dataList.get(position).getNews_id()));
                meAdapter.notifyDataSetChanged();
            }
        });
        meAdapter = new MessageAdapter(mContext,R.layout.item_message_list, dataList);
        rcvMessageList.setAdapter(meAdapter);
        meAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rcvMessageList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageId=pageId+1;
                        isLoadMore=true;
                        isDownFresh=false;
                        initData();
                    }
                }, 1000);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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
