package com.or.goodlive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.BaseResultsBean;
import com.or.goodlive.module.CommentBean;
import com.or.goodlive.module.MessResultsBean;
import com.or.goodlive.module.request.RequestSearchKeyWord;
import com.or.goodlive.ui.activity.other.WebViewActivity;
import com.or.goodlive.ui.adapter.CommentAdapter;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Wang on 2017/2/23.
 */

public class CommentListActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rcv_comment_list)
    RecyclerView rcv_comment_list;
    @Bind(R.id.swipe_refresh_comment_list)
    SwipeRefreshLayout swipe_refresh_comment_list;
    @Bind(R.id.add_comment_btn)
    TextView addCommentBtn;
    @Bind(R.id.edit_comment)
    EditText editComment;
    private Handler handler;
    private String table_name,news_id;
    private String pageId = "1";
    private String count = "1000";
    private List<CommentBean.RstBean.ListBean> dataList = new ArrayList<>();
    private CommentAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("news_id",news_id);
        params.put("table_name",table_name);
        params.put("pageId", pageId);
        params.put("count", count);
        ServiceFactory.getProvideHttpService().getCommentList(params)
                .compose(this.<CommentBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<CommentBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<CommentBean, Boolean>() {
                    @Override
                    public Boolean call(CommentBean messResultsBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno())) {
                            ToastUtil.makeText(mContext, messResultsBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno());
                    }
                })
                .map(new Func1<CommentBean, CommentBean.RstBean>() {
                    @Override
                    public CommentBean.RstBean call(CommentBean messResultsBean) {
                        return messResultsBean.getRst();
                    }
                })
                .subscribe(new Action1<CommentBean.RstBean>() {
                    @Override
                    public void call(CommentBean.RstBean resultsBean) {
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

    private void initListView(CommentBean.RstBean resultsBean) {
        List<CommentBean.RstBean.ListBean> datasList = resultsBean.getList();

        dataList.addAll(datasList);

        if (null == adapter) {
            adapter = new CommentAdapter(R.layout.item_comment_list, dataList);
            rcv_comment_list.setAdapter(adapter);
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
        toolbarContentText.setText("评论");
        handler = new Handler();
        rcv_comment_list.setLayoutManager(new LinearLayoutManager(this));
        swipe_refresh_comment_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        initData();
                        swipe_refresh_comment_list.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        Intent intent = getIntent();
        table_name= intent.getStringExtra(WebViewActivity.WEB_VIEW_TABLE_NAME);
        news_id  =intent.getStringExtra(WebViewActivity.WEB_VIEW_NEWS_ID);
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
    @OnClick({R.id.add_comment_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_comment_btn://发表
                String content = editComment.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    ToastUtil.makeText(mContext, "内容不能为空！");
                }else{

                    sendComment(content);
                }
                break;
        }
    }

    private void sendComment(String content) {
        RequestSearchKeyWord requestSearchKeyWord = new RequestSearchKeyWord();
        requestSearchKeyWord.setNews_id(news_id);
        requestSearchKeyWord.setTable_name(table_name);
        requestSearchKeyWord.setContent(content);
        ServiceFactory.getProvideHttpService().addComment(requestSearchKeyWord)
                .compose(this.<BaseResultsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<BaseResultsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<BaseResultsBean, Boolean>() {
                    @Override
                    public Boolean call(BaseResultsBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<BaseResultsBean, BaseResultsBean.RstBean>() {
                    @Override
                    public BaseResultsBean.RstBean call(BaseResultsBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<BaseResultsBean.RstBean>() {
                    @Override
                    public void call(BaseResultsBean.RstBean resultsBean) {
                        ToastUtil.makeText(mContext, "发布成功！");
                        dataList.clear();
                        editComment.setText("");
                        initData();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "发布失败！");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }
}
