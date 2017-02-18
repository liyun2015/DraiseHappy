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
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    private List<CoverDataBean.RstBean.HomeactBean> coverDataList = new ArrayList<>();
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
        for (int i = 0; i < 2; i++) {
            CoverDataBean.RstBean.HomeactBean b = new CoverDataBean.RstBean.HomeactBean();
            coverDataList.add(b);
        }
        adapter = new MessageAdapter(R.layout.item_message_list, coverDataList);
        rcvMessageList.setAdapter(adapter);
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
                        coverDataList.clear();
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
