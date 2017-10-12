package com.or.draise_happy.ui.activity;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.or.draise_happy.R;
import com.or.draise_happy.api.config.ServiceFactory;
import com.or.draise_happy.base.BaseActivity;
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.SearchKeyWord;
import com.or.draise_happy.module.request.RequestSearchKeyWord;
import com.or.draise_happy.ui.activity.other.WebViewActivity;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {


    @Bind(R.id.search_back)
    ImageView searchBack;
    @Bind(R.id.edit_search)
    AppCompatEditText editSearch;
    @Bind(R.id.top_title_view)
    RelativeLayout topTitleView;
    @Bind(R.id.lv_search_key_word)
    ListView lvSearchKeyWord;
    @Bind(R.id.activity_search)
    LinearLayout activitySearch;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    private ArrayAdapter<String> stringArrayAdapter;
    private List<SearchKeyWord.RstBean> keyWordList;
    private List<String> arrList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        keyWordList = new ArrayList<>();
        arrList = new ArrayList<>();
        initView();
    }

    private void initView() {
        editSearch.setOnEditorActionListener(this);
        RxTextView.textChanges(editSearch)
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        if (!TextUtils.isEmpty(charSequence.toString())) {
                            searchKeyWord(charSequence.toString());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        lvSearchKeyWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String typeStr = keyWordList.get(position).getTable_name();
                String id = keyWordList.get(position).getId();
                String url = Constants.WEB_VIEW_HOSTURL + "type="+typeStr+ "&id=" + id;
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_VIEW_URL, url);
                intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, typeStr);
                intent.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, String.valueOf(keyWordList.get(position).getId()));
                intent.putExtra(WebViewActivity.WEB_VIEW_DESC, keyWordList.get(position).getTitle());
                startActivity(intent);
                finish();
            }
        });
    }


    private void searchKeyWord(String mKeyWord) {
        RequestSearchKeyWord requestSearchKeyWord = new RequestSearchKeyWord();
        requestSearchKeyWord.setTitle(mKeyWord);
        ServiceFactory.getProvideHttpService().getSearchKeyWords(requestSearchKeyWord)
                .compose(this.<SearchKeyWord>bindToLifecycle())
                .compose(RxSchedulersHelper.<SearchKeyWord>io_main())
                .filter(new Func1<SearchKeyWord, Boolean>() {
                    @Override
                    public Boolean call(SearchKeyWord searchKeyWord) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(searchKeyWord.getErrno())) {
                            ToastUtil.makeText(mContext, searchKeyWord.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(searchKeyWord.getErrno());
                    }
                })
                .filter(new Func1<SearchKeyWord, Boolean>() {
                    @Override
                    public Boolean call(SearchKeyWord searchKeyWord) {
                        return searchKeyWord != null;
                    }
                })
                .filter(new Func1<SearchKeyWord, Boolean>() {
                    @Override
                    public Boolean call(SearchKeyWord searchKeyWord) {
                        return searchKeyWord.getRst() != null;
                    }
                })
                .subscribe(new Action1<SearchKeyWord>() {
                    @Override
                    public void call(SearchKeyWord searchKeyWord) {
                        initDataList(searchKeyWord);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    private void initDataList(SearchKeyWord searchKeyWord) {
        keyWordList.addAll(searchKeyWord.getRst());
        arrList.clear();
        for (int i = 0; i < searchKeyWord.getRst().size(); i++) {
            arrList.add(i, searchKeyWord.getRst().get(i).getTitle());
        }
        if (arrList.size()>0) {
            noDataLayout.setVisibility(View.GONE);
            lvSearchKeyWord.setVisibility(View.VISIBLE);
        } else {
            lvSearchKeyWord.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
        if (stringArrayAdapter == null) {
            stringArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, arrList);
            lvSearchKeyWord.setAdapter(stringArrayAdapter);
        } else {
            stringArrayAdapter.notifyDataSetChanged();
        }
        KeyboardUtils.hideSoftInput(mActivity);
    }


    @OnClick({R.id.search_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;

        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String inputStr = editSearch.getText().toString().trim();
            if (TextUtils.isEmpty(inputStr)) {
                KeyboardUtils.hideSoftInput(mActivity);
                ToastUtil.makeText(mContext, "搜索内容不能为空");
            } else {
                searchKeyWord(inputStr);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
