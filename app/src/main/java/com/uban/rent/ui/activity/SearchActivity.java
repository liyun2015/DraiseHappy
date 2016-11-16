package com.uban.rent.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.search_back)
    ImageView searchBack;
    @Bind(R.id.edit_search)
    AppCompatEditText editSearch;
    @Bind(R.id.activity_search)
    LinearLayout activitySearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.search_back)
    public void onClick() {
        finish();
    }
}
