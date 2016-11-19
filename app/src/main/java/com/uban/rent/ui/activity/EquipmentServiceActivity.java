package com.uban.rent.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.adapter.EquipmentServiceAdapter;

import java.util.ArrayList;

import butterknife.Bind;

public class EquipmentServiceActivity extends BaseActivity {
    public static final String KEY_IMAGE_LIST = "images";
    public static final String KEY_NAME_LIST = "names";
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_equipment_service)
    ListView lvEquipmentService;
    @Bind(R.id.activity_equipment_service)
    LinearLayout activityEquipmentService;

    private ArrayList<String> servicesImages;
    private ArrayList<String> servicesnames;
    private EquipmentServiceAdapter equipmentServiceAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_equipment_service;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        servicesImages = new ArrayList<>();
        servicesnames = new ArrayList<>();
        servicesImages = getIntent().getStringArrayListExtra(KEY_IMAGE_LIST);
        servicesnames = getIntent().getStringArrayListExtra(KEY_NAME_LIST);

        equipmentServiceAdapter = new EquipmentServiceAdapter(mContext,servicesnames,servicesImages);
        lvEquipmentService.setAdapter(equipmentServiceAdapter);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("设备和服务");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
