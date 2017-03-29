package com.or.goodlive.ui.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.BaseResultsBean;
import com.or.goodlive.module.DetailsBean;
import com.or.goodlive.module.MessResultsBean;
import com.or.goodlive.module.request.RequestSearchKeyWord;
import com.or.goodlive.service.WebAppInterface;
import com.or.goodlive.ui.activity.CommentListActivity;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.KeyboardUtils;
import com.or.goodlive.util.SPUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


public class WebViewActivity extends BaseActivity {
    public static final String WEB_VIEW_URL = "url";
    public static final String WEB_VIEW_DESC = "益直播";
    public static final String WEB_VIEW_NEWS_ID = "news_id";
    public static final String WEB_VIEW_TABLE_NAME = "table_name";
    public static final String WEB_VIEW_TITLE_NAME = "title_name";
    public static final String WEB_VIEW_CONTENT_NAME = "content_name";
    public static final String WEB_VIEW_FAVOR_STATE = "web_view_favor_state";
    public static final String WEB_VIEW_PIC = "web_view_pic";
    public static final String WEB_VIEW_COMMENT_NUM = "comment_num";
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.activity_web_view)
    RelativeLayout activityWebView;
    @Bind(R.id.image_comments)
    ImageView image_comments;
    @Bind(R.id.top_view)
    LinearLayout topView;
    @Bind(R.id.image_share)
    ImageView imageShare;
    @Bind(R.id.image_like)
    ImageView imageLike;
    @Bind(R.id.text_comment)
    TextView textComment;
    @Bind(R.id.add_comment_btn)
    TextView addCommentBtn;
    @Bind(R.id.edit_comment)
    EditText editComment;
    @Bind(R.id.add_comment_layout)
    RelativeLayout addCommentLayout;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.comment_top_layout)
    RelativeLayout commentTopLayout;
    @Bind(R.id.comment_num)
    TextView commentNum;
    private String desc;
    private String url;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    private String table_name, news_id;
    private String title = "";
    private String contentStr = "";
    private int favorState = 0;
    private int isfavorState = 0;
    private String imgPathList = "";
    private int comment_num = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        //初始化
        initView();
        initSocial();
        resterEvent();
    }

    private void resterEvent() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_COMMENT_NUM)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        comment_num=comment_num+1;
                        commentNum.setText(String.valueOf(comment_num));
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                })
                .create();
    }

    private void initSocial() {
        SHARE_MEDIA[] shareMedias = new SHARE_MEDIA[]{
                SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA
        };
        mShareListener = new CustomShareListener(WebViewActivity.this);
        mShareAction = new ShareAction(WebViewActivity.this).setDisplayList(shareMedias)
                .setCallback(mShareListener);
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<WebViewActivity> mActivity;

        private CustomShareListener(WebViewActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                //ToastUtil.makeText(mActivity.get(),"收藏成功啦");
                // Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.LINKEDIN
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    // Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST
                    && platform != SHARE_MEDIA.LINKEDIN
                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                //   Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                ToastUtil.makeText(mActivity.get(), "分享失败啦");
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            //  Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra(WEB_VIEW_URL);
        desc = intent.getStringExtra(WEB_VIEW_DESC);
        table_name = intent.getStringExtra(WEB_VIEW_TABLE_NAME);
        news_id = intent.getStringExtra(WEB_VIEW_NEWS_ID);
        title = intent.getStringExtra(WEB_VIEW_TITLE_NAME);
        contentStr = intent.getStringExtra(WEB_VIEW_CONTENT_NAME);
        favorState = intent.getIntExtra(WEB_VIEW_FAVOR_STATE, 0);
        imgPathList = intent.getStringExtra(WEB_VIEW_PIC);
        getDetailData();
    }

    private void getDetailData() {
        if("cover".equals(table_name)){
            getCoverDetailData();
            bottomView.setVisibility(View.VISIBLE);
        }else if("yiming".equals(table_name)){
            getYimingDetailData();
            bottomView.setVisibility(View.VISIBLE);
        }else if("news".equals(table_name)){
            getNewsDetailData();
            bottomView.setVisibility(View.VISIBLE);
        }else{
            bottomView.setVisibility(View.GONE);
        }
    }



    private void getNewsDetailData() {
        Map<String, String> params = new HashMap<>();
        params.put("id", news_id);
        ServiceFactory.getProvideHttpService().getNewsDetail(params)
                .compose(this.<DetailsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<DetailsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .filter(new Func1<DetailsBean, Boolean>() {
                    @Override
                    public Boolean call(DetailsBean messResultsBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno())) {
                            ToastUtil.makeText(mContext, messResultsBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno());
                    }
                })
                .map(new Func1<DetailsBean, DetailsBean.RstBean>() {
                    @Override
                    public DetailsBean.RstBean call(DetailsBean messResultsBean) {
                        return messResultsBean.getRst();
                    }
                })
                .subscribe(new Action1<DetailsBean.RstBean>() {
                    @Override
                    public void call(DetailsBean.RstBean resultsBean) {
                        if(resultsBean.getData()!=null){

                            initDataView(resultsBean.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, " 数据加载失败！");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                    }
                });
    }

    private void getYimingDetailData() {
        Map<String, String> params = new HashMap<>();
        params.put("id", news_id);
        ServiceFactory.getProvideHttpService().getYimingDetail(params)
                .compose(this.<DetailsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<DetailsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .filter(new Func1<DetailsBean, Boolean>() {
                    @Override
                    public Boolean call(DetailsBean messResultsBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno())) {
                            ToastUtil.makeText(mContext, messResultsBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno());
                    }
                })
                .map(new Func1<DetailsBean, DetailsBean.RstBean>() {
                    @Override
                    public DetailsBean.RstBean call(DetailsBean messResultsBean) {
                        return messResultsBean.getRst();
                    }
                })
                .subscribe(new Action1<DetailsBean.RstBean>() {
                    @Override
                    public void call(DetailsBean.RstBean resultsBean) {
                        if(resultsBean.getData()!=null){

                            initDataView(resultsBean.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, " 数据加载失败！");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                    }
                });
    }

    private void getCoverDetailData() {
        Map<String, String> params = new HashMap<>();
        params.put("id", news_id);
        ServiceFactory.getProvideHttpService().getCoverDetail(params)
                .compose(this.<DetailsBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<DetailsBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .filter(new Func1<DetailsBean, Boolean>() {
                    @Override
                    public Boolean call(DetailsBean messResultsBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno())) {
                            ToastUtil.makeText(mContext, messResultsBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(messResultsBean.getErrno());
                    }
                })
                .map(new Func1<DetailsBean, DetailsBean.RstBean>() {
                    @Override
                    public DetailsBean.RstBean call(DetailsBean messResultsBean) {
                        return messResultsBean.getRst();
                    }
                })
                .subscribe(new Action1<DetailsBean.RstBean>() {
                    @Override
                    public void call(DetailsBean.RstBean resultsBean) {
                        if(resultsBean.getData()!=null){

                            initDataView(resultsBean.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, " 数据加载失败！");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                    }
                });
    }

    private void initDataView(DetailsBean.RstBean.DataBean data) {
        if(TextUtils.isEmpty(imgPathList)||TextUtils.isEmpty(title)||TextUtils.isEmpty(contentStr)){
            imgPathList=data.getTitle_pic();
            title=data.getTitle();
            contentStr=data.getSub();
        }
        favorState=Integer.parseInt(data.getIs_like());
        comment_num=Integer.parseInt(data.getComment_num());
        if (1 == favorState) {
            imageLike.setImageResource(R.drawable.love_icon);
            isfavorState = 0;
        } else {
            imageLike.setImageResource(R.drawable.unlove_icon);
            isfavorState = 1;
        }
        commentNum.setText(String.valueOf(comment_num));
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText(desc);
        showLoadingView();
        //WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCacheEnabled(true);
        // WebView中的JS代码绑定
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideLoadingView();
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        String uid = (String) SPUtils.get(this,Constants.USER_ID,"");
        url = url+"&uid="+uid;
        webView.loadUrl(url);

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
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_like, R.id.add_comment_btn, R.id.text_comment, R.id.image_comments, R.id.image_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_comment_btn://发表
                commentTopLayout.setVisibility(View.VISIBLE);
                addCommentLayout.setVisibility(View.GONE);
                KeyboardUtils.hideSoftInput(mActivity);
                String content = editComment.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.makeText(mContext, "内容不能为空！");
                } else {
                    sendComment(content);
                }
                break;
            case R.id.text_comment:
                commentTopLayout.setVisibility(View.GONE);
                addCommentLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.image_like://点赞
                addFavor();
                break;
            case R.id.image_comments://评论
                goToCommentList();
                break;
            case R.id.image_share://分享
                String shareTitle = title;
                String shareMsg = contentStr;
                String shareUrl = Constants.WEB_VIEW_HOSTURL + "type=" + table_name + "&id=" + news_id;
                UMImage shareImage = new UMImage(this, imgPathList);
                ShareBoardConfig config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
//                config.setTitleVisibility(false); // 隐藏title
//                config.setCancelButtonVisibility(false); // 隐藏取消按钮
                mShareAction.withTitle(shareTitle)
                        .withText(shareMsg)
                        .withMedia(shareImage)
                        .withTargetUrl(shareUrl)
                        .open(config);
                break;
        }
    }


    private void goToCommentList() {
        Intent intent = new Intent();
        intent.setClass(mContext, CommentListActivity.class);
        intent.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, news_id);
        intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, table_name);
        startActivity(intent);
    }

    private void addFavor() {
        RequestSearchKeyWord requestSearchKeyWord = new RequestSearchKeyWord();
        requestSearchKeyWord.setNews_id(news_id);
        requestSearchKeyWord.setTable_name(table_name);
        requestSearchKeyWord.setStatus(isfavorState);
        ServiceFactory.getProvideHttpService().addfavor(requestSearchKeyWord)
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
                        if (1 == favorState) {
                            ToastUtil.makeText(mContext, "取消成功！");
                            imageLike.setImageResource(R.drawable.unlove_icon);
                            isfavorState = 1;
                            favorState=0;
                        } else {
                            ToastUtil.makeText(mContext, "点赞成功！");
                            imageLike.setImageResource(R.drawable.love_icon);
                            favorState=1;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "点赞失败！");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
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
                        goToCommentList();
                        comment_num=comment_num+1;
                        commentNum.setText(String.valueOf(comment_num));
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
