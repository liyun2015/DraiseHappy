package com.uban.rent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
    private int loctionNum=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_orders;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        int spaceDeskId = getIntent().getIntExtra(KEY_SPACEDESK_ID,0);
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


    @OnClick({R.id.order_create, R.id.add_btn, R.id.reduce_btn,R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create://提交订单
                goActivity(OrderPaymentActivity.class);
                break;
            case R.id.add_btn://加
                loctionNum=loctionNum+1;
                numder_of_stations.setText(String.valueOf(loctionNum));
                break;
            case R.id.reduce_btn://减
                if(loctionNum>1){
                    loctionNum=loctionNum-1;
                    numder_of_stations.setText(String.valueOf(loctionNum));
                }
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
