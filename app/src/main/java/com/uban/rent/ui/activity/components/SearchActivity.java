package com.uban.rent.ui.activity.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.Events;
import com.uban.rent.control.RxBus;
import com.uban.rent.control.events.SearchHomeViewEvents;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.ui.view.UbanListView;
import com.uban.rent.ui.view.flowlayout.FlowLayout;
import com.uban.rent.ui.view.flowlayout.TagAdapter;
import com.uban.rent.ui.view.flowlayout.TagFlowLayout;
import com.uban.rent.util.KeyboardUtils;

import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener{

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
    @Bind(R.id.clean_history_dates)
    TextView cleanHistoryDates;
    private String[] mVals = new String[]
            {"望京", "国贸CBD", "望京", "西直门", "国贸CBD", "国贸CBD", "国贸CBD", "国贸CBD", "望京", "国贸CBD", "望京", "西直门", "国贸CBD"};

    private static final String KEY_HISTORY = "history";
    private static final String KEY_SP_HISTORY = "sp_history";
    public static final String HISTORY_HINT =  "暂时没有搜索记录";
    private ArrayAdapter historyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        //历史记录
        loadHistory();
    }

    private void initView() {
        editSearch.setOnEditorActionListener(this);
        TagAdapter<String> tagAdapter = new TagAdapter<String>(mVals) {
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
                sendEvents(mVals[position]);
                saveHistory(mVals[position]);
                return true;
            }
        });
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //  Toast.makeText(mContext, selectPosSet.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendEvents(String values){
        SearchHomeViewEvents searchHomeViewEvents = new SearchHomeViewEvents();
        searchHomeViewEvents.setKeyWords(values);
        RxBus.getInstance().send(Events.EVENTS_SEARCH_TYPE,searchHomeViewEvents);
        finish();
    }

    private void loadHistory(){
        SharedPreferences sp = getSharedPreferences(KEY_SP_HISTORY, 0);
        String history = sp.getString(KEY_HISTORY, HISTORY_HINT);
        cleanHistoryDates.setVisibility(history.equals(HISTORY_HINT)?View.GONE:View.VISIBLE);
        final String[] historys = history.split(",");
        // 保留前6条数据
        if (historys.length > 6) {
            String[] newArrays = new String[6]; // 实现数组之间的复制
            System.arraycopy(historys, 0, newArrays, 0, 6);
        }
        if (historyAdapter == null) {
            historyAdapter = new ArrayAdapter<>(this,
                    R.layout.item_search_history_view, historys);
            searchHistory.setAdapter(historyAdapter);
        } else {
            historyAdapter.notifyDataSetChanged();
        }

        searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendEvents(historys[i]);
            }
        });
    }

    public void saveHistory(String text) {
        // 获取搜索框信息
        SharedPreferences sp = getSharedPreferences(KEY_SP_HISTORY,
                Context.MODE_PRIVATE);
        String oldText = sp.getString(KEY_HISTORY, "");

        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(oldText);
        builder.append(text + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!oldText.contains(text + ",")) {
            SharedPreferences.Editor myeditor = sp.edit();
            myeditor.putString(KEY_HISTORY, builder.toString());
            myeditor.commit();
        }

    }

    // 清除搜索记录
    public void cleanHistory() {
        searchHistory.setVisibility(View.GONE);
        cleanHistoryDates.setVisibility(View.GONE);
        SharedPreferences sp = getSharedPreferences(KEY_SP_HISTORY, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    @OnClick({R.id.search_back, R.id.clean_history_dates})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;
            case R.id.clean_history_dates:
                cleanHistory();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String inputStr = editSearch.getText().toString().trim();
            if (TextUtils.isEmpty(inputStr)){
                KeyboardUtils.hideSoftInput(mActivity);
                ToastUtil.makeText(mContext,"搜索内容不能为空");
            }else {
                sendEvents(inputStr);
                saveHistory(inputStr);
            }
            return true;
        }
        return false;
    }
}
