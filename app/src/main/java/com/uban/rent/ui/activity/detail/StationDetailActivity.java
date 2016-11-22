package com.uban.rent.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.CreateOrderParamaBean;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.module.request.RequestGoSpaceDetail;
import com.uban.rent.module.request.RequestGoWorkPlaceDetail;
import com.uban.rent.module.request.RequestWorkplaceDetail;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.ui.activity.order.CreateOrdersActivity;
import com.uban.rent.ui.activity.components.EquipmentServiceActivity;
import com.uban.rent.ui.adapter.BannerPicAdapter;
import com.uban.rent.ui.adapter.WorkPlaceServiceGradViewAdapter;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.banner.LoopViewPager;
import com.uban.rent.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * 工位详情页
 */
public class StationDetailActivity extends BaseActivity {
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
    AppBarLayout topView;
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
    @Bind(R.id.gridview_work_place_detail)
    GridView gridviewWorkPlaceDetail;
    @Bind(R.id.iv_show_equipment_service_list)
    ImageView ivShowEquipmentServiceList;
    private RequestGoSpaceDetail requestGoSpaceDetail;
    private RequestGoWorkPlaceDetail requestGoWorkPlaceDetail;
    private int mWorkPlaceId;
    private int mPrice;
    private int mPriceType;
    private static final String[] TITLE_PRICE_TYPE = new String[]{"元/时 (时租)", "元/天 (日租)", "元/月 (月租)"};
    private ArrayList<String> equipmentServicesImages;
    private ArrayList<String> equipmentServicesnames;
    private CreateOrderParamaBean createOrderParamaBean;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_workplace_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        equipmentServicesImages = new ArrayList<>();
        equipmentServicesnames = new ArrayList<>();
        requestGoSpaceDetail = (RequestGoSpaceDetail) getIntent().getSerializableExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL);
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
        viewpager.setBackgroundResource(R.drawable.ic_normal);
        imageDesc.setVisibility(View.GONE);
        List<String> images = new ArrayList<>();
        for (WorkplaceDetailBean.ResultsBean.PicListBean picListBean :
                resultsBean.getPicList()) {
            images.add(String.format(Constants.APP_IMG_URL_640_420, picListBean.getImgPath()));
        }
        if (images == null || images.size() <= 0) {
            return;
        }
        imageDesc.setVisibility(View.VISIBLE);
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

    private void setaEquipmentServiceList(WorkplaceDetailBean.ResultsBean resultsBean) {
        for (WorkplaceDetailBean.ResultsBean.ServiceListBean serviceListBean :
                resultsBean.getServiceList()) {
            equipmentServicesnames.add(serviceListBean.getFieldName());
            equipmentServicesImages.add(Constants.APP_IMG_URL_EQUIPMENT_SERVICE + serviceListBean.getFieldImg());
        }
    }

    private void initDataView(WorkplaceDetailBean.ResultsBean resultsBean) {
        setaEquipmentServiceList(resultsBean);
        setBannerImags(resultsBean);
        gridviewWorkPlaceDetail.setAdapter(new WorkPlaceServiceGradViewAdapter(mContext, resultsBean.getServiceList(), gridviewWorkPlaceDetail));
        toolbarContentText.setText(resultsBean.getSpaceCnName());
        tvWorkplaceName.setText(resultsBean.getSpaceCnName());
        tvWorkplaceStation.setText(String.valueOf(resultsBean.getRentNum()));
        tvWorkplaceAddress.setText(resultsBean.getAddress());
        tvWorkplaceDesc.setText(resultsBean.getRentDesc());
        tvWorkplaceTime.setText(resultsBean.getWorkTime());
        tvWorkplaceNotice.setText(resultsBean.getBuyDesc());
        tvPriceType.setText(TITLE_PRICE_TYPE[mPriceType]);
        tvPrice.setText(String.valueOf(mPrice));

        createOrderParamaBean = new CreateOrderParamaBean();
        createOrderParamaBean.setSpaceDeskName(resultsBean.getSpaceCnName());
        createOrderParamaBean.setSpaceDeskAddress(resultsBean.getAddress());
        createOrderParamaBean.setSpaceDeskId(resultsBean.getOfficespaceWorkdeskinfoId());
        createOrderParamaBean.setPriceType(mPriceType);
        createOrderParamaBean.setPrice(mPrice);
        createOrderParamaBean.setWorkHoursBegin(resultsBean.getWorkHoursBegin());
        createOrderParamaBean.setWorkHoursEnd(resultsBean.getWorkHoursEnd());
        createOrderParamaBean.setWorkDeskType(resultsBean.getWorkDeskType());
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
        getMenuInflater().inflate(R.menu.share_detail, menu);
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
                String msgTitle = tvWorkplaceName.getText().toString();
                String msgText = "http://m.uban.com/" + HeaderConfig.cityShorthand() + "/duanzu/gongwei-" + mWorkPlaceId + ".html";
                shareMsg(mContext, "工位详情分享", msgTitle, msgText);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void shareMsg(Context context, String activityTitle, String msgTitle, String msgText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    @OnClick({R.id.rl_go_space_detail, R.id.iv_show_equipment_service_list,R.id.order_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_go_space_detail:
                Intent intent = new Intent();
                intent.setClass(mContext, SpaceDetailActivity.class);
                intent.putExtra(SpaceDetailActivity.KEY_BUILD_SPACE_DETAIL, requestGoSpaceDetail);
                startActivity(intent);
                break;
            case R.id.iv_show_equipment_service_list:
                Intent serviceIntent = new Intent();
                serviceIntent.setClass(mContext, EquipmentServiceActivity.class);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_NAME_LIST, equipmentServicesnames);
                serviceIntent.putExtra(EquipmentServiceActivity.KEY_IMAGE_LIST, equipmentServicesImages);
                startActivity(serviceIntent);
                break;
            case R.id.order_create:
                Intent orderIntent = new Intent();
                orderIntent.setClass(mContext,CreateOrdersActivity.class);
                orderIntent.putExtra(CreateOrdersActivity.KEY_CREATE_ORDER_PARAME_BEAN,createOrderParamaBean);
                startActivity(orderIntent);
                break;
        }
    }

}
