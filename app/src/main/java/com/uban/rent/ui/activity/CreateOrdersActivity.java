package com.uban.rent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.util.CommonUtil;
import com.uban.rent.util.TimeUtils;
import com.uban.rent.ui.view.wheelview.OnWheelScrollListener;
import com.uban.rent.ui.view.wheelview.WheelView;
import com.uban.rent.ui.view.wheelview.adapter.NumericWheelAdapter;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建工位订单
 */
public class CreateOrdersActivity extends BaseActivity {
    public static final String KEY_SPACEDESK_ID = "spaceDeskId";
    public static final String KEY_SPACEDESK_NAME = "spaceDeskName";
    public static final String KEY_SPACEDESK_ADDRESS = "spaceDeskAddress";
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.numder_of_stations)
    TextView numder_of_stations;
    @Bind(R.id.darkening_background_layout)
    LinearLayout darkening_background_layout;
    @Bind(R.id.activity_create_orders)
    RelativeLayout activityCreateOrders;
    private int loctionNum = 1;
    private LayoutInflater mInflater;
    private WheelView timeWheelView;
    private WheelView month_wheelView;
    private WheelView day_wheelView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_orders;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        int spaceDeskId = getIntent().getIntExtra(KEY_SPACEDESK_ID, 0);
        String spaceDeskName = getIntent().getStringExtra(KEY_SPACEDESK_NAME);
        String spaceDeskAddress = getIntent().getStringExtra(KEY_SPACEDESK_ADDRESS);
        initView();
    }

    private void initView() {
        toolbarContentText.setText("创建订单");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        numder_of_stations.setText(String.valueOf(loctionNum));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
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

    private void goActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }


    @OnClick({R.id.order_create, R.id.add_btn, R.id.reduce_btn, R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create://提交订单
                goActivity(OrderPaymentActivity.class);
                break;
            case R.id.add_btn://加月
                loctionNum = loctionNum + 1;
                numder_of_stations.setText(String.valueOf(loctionNum));
                break;
            case R.id.reduce_btn://减月
                if (loctionNum > 1) {
                    loctionNum = loctionNum - 1;
                    numder_of_stations.setText(String.valueOf(loctionNum));
                }
                break;
            case R.id.start_time_layout://开始时间
                showTimePopupWindow();
                break;
            case R.id.end_time_layout://结束时间
                showTimePopupWindow();
                break;
            default:
                break;
        }
    }

    private PopupWindow timePopupWindow; // 显示popupwindow
    private int cruYear;
    private void showTimePopupWindow() {
        // 得到控件
        darkening_background_layout.setVisibility(View.VISIBLE);
        View timeView = mInflater.inflate(R.layout.choose_time_pop, null);
        // 确定按钮
        TextView budget_sure = (TextView) timeView.findViewById(R.id.time_sure);
        budget_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timePopupWindow.isShowing()) {
                    timePopupWindow.dismiss();
                    darkening_background_layout.setVisibility(View.GONE);
                }
            }
        });
        // 取消按钮
        TextView budget_cancle = (TextView) timeView
                .findViewById(R.id.time_cancle);
        budget_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timePopupWindow.isShowing()) {
                    timePopupWindow.dismiss();
                    darkening_background_layout.setVisibility(View.GONE);
                }
            }
        });
        Calendar c = Calendar.getInstance();
        cruYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
        //月
        month_wheelView = (WheelView) timeView.findViewById(R.id.month_wheelView);
        NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(this,1, 12, "%02d");
        numericWheelAdapter.setLabel("月");
        month_wheelView.setViewAdapter(numericWheelAdapter);
        month_wheelView.setCyclic(true);
        month_wheelView.addScrollingListener(scrollListener);
        //日
        day_wheelView = (WheelView) timeView.findViewById(R.id.day_wheelView);
        initDay(cruYear,curMonth);
        day_wheelView.setCyclic(true);
        // 小时控件
        timeWheelView = (WheelView) timeView.findViewById(R.id.time_wheelView);
        NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(this,1, 23, "%02d");
        numericWheelAdapter1.setLabel("时");
        timeWheelView.setViewAdapter(numericWheelAdapter1);
        timeWheelView.setCyclic(true);
        timeWheelView.addScrollingListener(scrollListener);
        int screeWidth = getWindowManager().getDefaultDisplay().getWidth();

        timePopupWindow = CommonUtil.ShowBottomPopupWindow(
                CreateOrdersActivity.this, timePopupWindow, timeView, screeWidth,
                258, activityCreateOrders);
        CommonUtil.setPopupWindowListener(new CommonUtil.PopupWindowListener() {

            @Override
            public void myDissmiss() {
                timePopupWindow = null;
                darkening_background_layout.setVisibility(View.GONE);
            }

        });
        month_wheelView.setCurrentItem(curMonth - 1);
        day_wheelView.setCurrentItem(curDate - 1);
    }
    private void initDay(int year, int month) {
        NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(this,1, TimeUtils.getDay(year, month), "%02d");
        numericWheelAdapter.setLabel("日");
        day_wheelView.setViewAdapter(numericWheelAdapter);
    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_month = timeWheelView.getCurrentItem() + 1;//月
            initDay(cruYear,n_month);
        }
    };

}
