package com.or.goodlive.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.CategoryDataBean;
import com.or.goodlive.module.ColumnBean;
import com.or.goodlive.ui.adapter.FragmentTabAdapter;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.ui.view.ViewChooseList;
import com.or.goodlive.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/13.
 */

public class YaMingFragment extends BaseFragment {
    @Bind(R.id.tab_yaming)
    TabLayout tabYaming;
    @Bind(R.id.view_pager_yaming)
    ViewPager viewPagerYaming;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.rb_btn_layout)
    LinearLayout rbBtnLayout;
    @Bind(R.id.top_view)
    View topView;
    private List<Fragment> listFragments;
    private ViewChooseList viewChooseList;
    private List<ColumnBean> columnListBeen=new ArrayList<>();

    public static YaMingFragment newInstance() {
        Bundle args = new Bundle();
        YaMingFragment fragment = new YaMingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yaming_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        registerEvent();
        initData();
    }
    private void initData() {
        getTitles();
    }
    private void getTitles() {
        Map<String, Integer> params = new HashMap<>();
        ServiceFactory.getProvideHttpService().getYimingTitList(params)
                .compose(RxSchedulersHelper.<CategoryDataBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .filter(new Func1<CategoryDataBean, Boolean>() {
                    @Override
                    public Boolean call(CategoryDataBean categoryDataBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(categoryDataBean.getErrno())) {
                            ToastUtil.makeText(mContext, categoryDataBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(categoryDataBean.getErrno());
                    }
                })
                .subscribe(new Action1<CategoryDataBean>() {
                    @Override
                    public void call(CategoryDataBean categoryDataBean) {
                        if (null != categoryDataBean.getRst()) {
                            initView(categoryDataBean.getRst());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, throwable.getMessage());
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }
    private void  registerEvent(){
        RxBus.with(this)
                .setEvent(Events.EVENTS_DISMISS_POP)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        disPop ();
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("loginEventError",throwable.getMessage());
                    }
                })
                .create();
    }
    private void disPop (){
        if(viewChooseList!=null&&viewChooseList.isShowing()) {
            viewChooseList.dismiss();
        }
    }
    @OnClick(R.id.add_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                showColumn();
                break;
        }
    }
    private void showColumn() {
        if(viewChooseList!=null){
            if(viewChooseList.isShowing()){
                viewChooseList.dismiss();
            }else{
                viewChooseList.showAsDropDown(topView);
            }
        }else{
            viewChooseList = new ViewChooseList(getContext(), rbBtnLayout.getMeasuredWidth(),columnListBeen);
            viewChooseList.showAsDropDown(topView);
            viewChooseList
                    .setOnSelectListener(new ViewChooseList.OnSelectListener() {
                        @Override
                        public void getValue(String showText) {

                            //onRefresh();
                        }
                    });
        }
    }
    private void initView(List<CategoryDataBean.RstBean> rst) {
        String[] TITLE_NAME = new String[rst.size()];
        for (int i=0;i<rst.size();i++){
            ColumnBean columnBean = new ColumnBean();
            columnBean.setValue(rst.get(i).getName());
            columnListBeen.add(i, columnBean);
            TITLE_NAME[i]=rst.get(i).getName();
        }
        listFragments = new ArrayList<>();
        viewPagerYaming.setOffscreenPageLimit(TITLE_NAME.length);
        for (int i = 0; i < TITLE_NAME.length; i++) {
            listFragments.add(YamingChildFragment.newInstance(rst.get(i).getId(),rst.get(i).getName()));
        }
        tabYaming.setTabMode(TabLayout.MODE_SCROLLABLE);
        FragmentTabAdapter fAdapter = new FragmentTabAdapter(getActivity().getSupportFragmentManager(),listFragments, Arrays.asList(TITLE_NAME));
        viewPagerYaming.setAdapter(fAdapter);
        tabYaming.setupWithViewPager(viewPagerYaming);

        tabYaming.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
