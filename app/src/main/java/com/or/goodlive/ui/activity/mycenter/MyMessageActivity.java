package com.or.goodlive.ui.activity.mycenter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.BaseResultsBean;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.module.MessResultsBean;
import com.or.goodlive.ui.adapter.MessageAdapter;
import com.or.goodlive.ui.adapter.YamingChildAdapter;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.ImageLoadUtils;

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
    private MessageAdapter adapter;
    private Handler handler;

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
        ServiceFactory.getProvideHttpService().messageList(params)
                .compose(this.<MessResultsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<MessResultsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
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

        dataList.addAll(datasList);

        if (null == adapter) {
            adapter = new MessageAdapter(R.layout.item_message_list, dataList);
            rcvMessageList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        if (dataList.size() == 0) {
            adapter.setEmptyView(setEmptyDataView(R.drawable.iconfont_no_data,"暂无数据！"));
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
                        dataList.clear();
                        initData();
                        swipeRefreshMessage.setRefreshing(false);
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
