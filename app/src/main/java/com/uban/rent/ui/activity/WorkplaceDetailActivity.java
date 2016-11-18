package com.uban.rent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestWorkplaceDetail;
import com.uban.rent.network.config.ServiceFactory;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.banner.LoopViewPager;
import com.uban.rent.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.uban.rent.ui.activity.SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL;

/**
 * 工位详情页
 */
public class WorkplaceDetailActivity extends BaseActivity {
    public static final String KEY_BUILD_WORK_PLACE_DETAIL = "keyWorkplaceDetail";

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    LoopViewPager viewpager;
    @Bind(R.id.image_desc)
    TextView imageDesc;
    @Bind(R.id.activity_workplace)
    RelativeLayout activityWorkplace;
    @Bind(R.id.top_view)
    RelativeLayout topView;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.tv_workplace_name)
    TextView tvWorkplaceName;
    @Bind(R.id.tv_workplace_station)
    TextView tvWorkplaceStation;
    @Bind(R.id.tv_workplace_address)
    TextView tvWorkplaceAddress;
    @Bind(R.id.tv_workplace_desc)
    TextView tvWorkplaceDesc;
    @Bind(R.id.tv_workplace_time)
    TextView tvWorkplaceTime;
    @Bind(R.id.tv_workplace_notice)
    TextView tvWorkplaceNotice;
    @Bind(R.id.rl_go_space_detail)
    RelativeLayout rlGoSpaceDetail;
    @Bind(R.id.tv_price_type)
    TextView tvPriceType;
    private RequestGoSpaceDetail requestGoSpaceDetail;
    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetail;
    private int mWorkPlaceId;
    private int mPrice;
    private int mPriceType;
    private static final String[] TITLE_PRICE_TYPE = new String[]{"元/时 (时租)", "元/天 (日租)", "元/月 (月租)"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_workplace_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        requestGoSpaceDetail = (RequestGoSpaceDetail) getIntent().getSerializableExtra(KEY_BUILD_SPACE_DETAIL);
        requestGoWorkPlaceDetail = (RequestGoWorkPlaceDetail) getIntent().getSerializableExtra(KEY_BUILD_WORK_PLACE_DETAIL);
        mWorkPlaceId = requestGoWorkPlaceDetail.getWorkplaceDetailId();
        mPrice = requestGoWorkPlaceDetail.getPrice();
        mPriceType = requestGoWorkPlaceDetail.getPriceType();
        initView();
        initData();
    }

    private void initData() {
        RequestWorkplaceDetail requestWorkplaceDetail = new RequestWorkplaceDetail();
        requestWorkplaceDetail.setOfficespaceWorkdeskinfoId(mWorkPlaceId);
        ServiceFactory.getProvideHttpService().getOfficespaceWorkdeskInfo(requestWorkplaceDetail)
                .compose(this.<WorkplaceDetailBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<WorkplaceDetailBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<WorkplaceDetailBean, Boolean>() {
                    @Override
                    public Boolean call(WorkplaceDetailBean workplaceDetailBean) {
                        return workplaceDetailBean.getStatusCode() == Constants.STATUS_CODE_SUCCESS;
                    }
                })
                .map(new Func1<WorkplaceDetailBean, WorkplaceDetailBean.ResultsBean>() {
                    @Override
                    public WorkplaceDetailBean.ResultsBean call(WorkplaceDetailBean workplaceDetailBean) {
                        return workplaceDetailBean.getResults();
                    }
                })
                .subscribe(new Action1<WorkplaceDetailBean.ResultsBean>() {
                    @Override
                    public void call(WorkplaceDetailBean.ResultsBean resultsBean) {
                        initDataView(resultsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, getString(R.string.str_result_error) + throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void setBannerImags(WorkplaceDetailBean.ResultsBean resultsBean) {
        List<String> images = new ArrayList<>();
        for (WorkplaceDetailBean.ResultsBean.PicListBean picListBean :
                resultsBean.getPicList()) {
            images.add(String.format(Constants.APP_IMG_URL_640_420, picListBean.getImgPath()));
        }
        if (images == null || images.size() <= 0) {
            return;
        }
        BannerPicAdapter bannerPicAdapter = new BannerPicAdapter(mContext);
        bannerPicAdapter.setData(images);
        viewpager.setAdapter(bannerPicAdapter);
        imageDesc.setText(1 + "/" + viewpager.getAdapter().getCount());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageDesc.setText(position + 1 + "/" + viewpager.getAdapter().getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDataView(WorkplaceDetailBean.ResultsBean resultsBean) {
        setBannerImags(resultsBean);
        toolbarContentText.setText(resultsBean.getSpaceCnName());
        tvWorkplaceName.setText(resultsBean.getSpaceCnName());
        tvWorkplaceStation.setText(String.valueOf(resultsBean.getRentNum()));
        tvWorkplaceAddress.setText(resultsBean.getAddress());
        tvWorkplaceDesc.setText(resultsBean.getRentDesc());
        tvWorkplaceTime.setText(resultsBean.getWorkTime());
        tvWorkplaceNotice.setText(resultsBean.getBuyDesc());
        tvPriceType.setText(TITLE_PRICE_TYPE[mPriceType]);
        tvPrice.setText(String.valueOf(mPrice));
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("工位详情");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.workplace_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify ic_home_meun_member parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
                share_intent.setType("text/plain");//设置分享内容的类型
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享内容标题");//添加分享内容标题
                share_intent.putExtra(Intent.EXTRA_TEXT, "分享内容");//添加分享内容
                //创建分享的Dialog
                share_intent = Intent.createChooser(share_intent, "分享");
                startActivity(share_intent);
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

    @OnClick(R.id.rl_go_space_detail)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL, requestGoSpaceDetail);
        intent.setClass(mContext, SpaceDetailActivity.class);
        startActivity(intent);
    }
}
