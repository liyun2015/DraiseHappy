package com.uban.rent.ui.activity.order;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;

import butterknife.Bind;

/**
 * 订单详情页
 */
public class OrdersDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_orders_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }
    private void initView() {
        setSupportActionBar(toolbar);
        toolbarContentText.setText("订单详情");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
