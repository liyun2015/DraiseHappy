package com.uban.rent.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.view.filterview.DropDownMenu;
import com.uban.rent.ui.view.filterview.ListDropDownAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainListActivity extends BaseActivity {
    @Bind(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String headers[] = {"时租", "区域", "类型", "城市"};
    private List<View> popupViews = new ArrayList<>();

    private ListDropDownAdapter timeRentsAdapter;
    private ListDropDownAdapter districtsAdapter;
    private ListDropDownAdapter workTypesAdapter;
    private ListDropDownAdapter locationCityAdapter;

    private String timeRents[] = {"时租", "日租", "月租"};
    private String districts[] = {"不限", "朝阳", "海淀"};
    private String workTypes[] = {"全部", "Hot Desk", "独立工位", "开发工位", "会议室", "活动场地"};
    private String locationCity[] = {"不限", "北京", "上海"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        final ListView timeRentsView = new ListView(this);
        timeRentsAdapter = new ListDropDownAdapter(this, Arrays.asList(timeRents));
        timeRentsView.setDividerHeight(0);
        timeRentsView.setAdapter(timeRentsAdapter);


        final ListView districtsView = new ListView(this);
        districtsView.setDividerHeight(0);
        districtsAdapter = new ListDropDownAdapter(this, Arrays.asList(districts));
        districtsView.setAdapter(districtsAdapter);


        final ListView workTypesView = new ListView(this);
        workTypesView.setDividerHeight(0);
        workTypesAdapter = new ListDropDownAdapter(this, Arrays.asList(workTypes));
        workTypesView.setAdapter(workTypesAdapter);


        final ListView locationCityView = new ListView(this);
        locationCityView.setDividerHeight(0);
        locationCityAdapter = new ListDropDownAdapter(this, Arrays.asList(locationCity));
        locationCityView.setAdapter(locationCityAdapter);


        popupViews.add(timeRentsView);
        popupViews.add(districtsView);
        popupViews.add(workTypesView);
        popupViews.add(locationCityView);


        //add item click event
        timeRentsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeRentsAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : timeRents[position]);
                mDropDownMenu.closeMenu();
            }
        });

        districtsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                districtsAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : districts[position]);
                mDropDownMenu.closeMenu();
            }
        });

        workTypesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                workTypesAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : workTypes[position]);
                mDropDownMenu.closeMenu();
            }
        });
        locationCityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationCityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[3] : locationCity[position]);
                mDropDownMenu.closeMenu();
            }
        });

        //init context view
        View contentView = getLayoutInflater().inflate(R.layout.include_ren_custom_layout, null);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

        initCustomView(contentView);
    }

    private void initCustomView(View contentView) {

    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}