package com.uban.rent.ui.activity.other;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;

import butterknife.Bind;

/**
 * 申请退款页面
 */
public class RefundOrderActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_order;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }
    private void initView() {
        setSupportActionBar(toolbar);
        toolbarContentText.setText("申请退款");
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
