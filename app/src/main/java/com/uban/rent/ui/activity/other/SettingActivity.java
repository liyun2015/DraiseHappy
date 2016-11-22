package com.uban.rent.ui.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.activity.LoginActivity;
import com.uban.rent.ui.view.dialog.AlertDialogStyleApp;
import com.uban.rent.util.SPUtils;

import butterknife.Bind;

public class SettingActivity extends BaseActivity {
    private static final String[] ITEM_LIST = new String[]{"推荐给好友", "为我们打分", "退出登录"};
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_setting)
    ListView lvSetting;
    @Bind(R.id.activity_setting)
    LinearLayout activitySetting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, ITEM_LIST);
        lvSetting.setAdapter(stringArrayAdapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        siginOut();
                        break;
                }
            }
        });
    }

    /**
     * 退出登录
     */
    private void siginOut() {
        AlertDialogStyleApp alertDialogStyleApp = new AlertDialogStyleApp(mContext);
        alertDialogStyleApp.builder()
                .setMsg("退出登录")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtils.clear(mContext);
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(mContext, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("优办会员");
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
