package com.uban.rent.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.ui.view.flowlayout.FlowLayout;
import com.uban.rent.ui.view.flowlayout.TagAdapter;
import com.uban.rent.ui.view.flowlayout.TagFlowLayout;

import java.util.Set;

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
    @Bind(R.id.tag_flow_layout)
    TagFlowLayout tagFlowLayout;
    @Bind(R.id.search_history)
    UbanListView searchHistory;
    private String[] mVals = new String[]
            {"望京", "国贸CBD", "望京", "西直门", "国贸CBD", "国贸CBD", "国贸CBD", "国贸CBD", "望京", "国贸CBD", "望京", "西直门", "国贸CBD"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
       TagAdapter<String> tagAdapter =  new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_search_tag_view, tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(mContext, mVals[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Toast.makeText(mContext, selectPosSet.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mVals);
        searchHistory.setAdapter(mAdapter);
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
