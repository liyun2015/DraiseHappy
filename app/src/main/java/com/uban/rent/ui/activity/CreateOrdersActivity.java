package com.uban.rent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建工位订单
 */
public class CreateOrdersActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.order_create)
    TextView orderCreate;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.price_of_hour)
    TextView priceOfHour;
    @Bind(R.id.station_str)
    TextView stationStr;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.numder_of_stations)
    TextView numderOfStations;
    @Bind(R.id.reduce_btn)
    ImageView reduceBtn;
    @Bind(R.id.right_arrows)
    ImageView rightArrows;
    @Bind(R.id.start_time)
    TextView startTime;
    @Bind(R.id.right_arrows1)
    ImageView rightArrows1;
    @Bind(R.id.end_time)
    TextView endTime;
    @Bind(R.id.right_textstr)
    TextView rightTextstr;
    @Bind(R.id.total_time)
    TextView totalTime;
    @Bind(R.id.switch_btn_one)
    CheckBox switchBtnOne;
    @Bind(R.id.switch_btn_two)
    CheckBox switchBtnTwo;
    @Bind(R.id.top_view)
    RelativeLayout topView;
    @Bind(R.id.total_price)
    TextView totalPrice;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.textView2)
    TextView textView2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_orders;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
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


    @OnClick({R.id.order_create, R.id.add_btn, R.id.reduce_btn,R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create://提交订单
                goActivity(OrdersDetailActivity.class);
                break;
            case R.id.add_btn://加
                break;
            case R.id.reduce_btn://减
                break;
            case R.id.start_time_layout://开始时间
                break;
            case R.id.end_time_layout://结束时间
                break;
            default:
                break;
        }
    }

}
