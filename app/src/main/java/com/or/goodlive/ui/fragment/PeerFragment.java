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
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.ui.adapter.FragmentTabAdapter;
import com.or.goodlive.ui.view.ViewChooseList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

import static android.R.attr.tag;

/**
 * Created by Administrator on 2017/2/13.
 */

public class PeerFragment extends BaseFragment {

    @Bind(R.id.tab_peer)
    TabLayout tabPeer;
    @Bind(R.id.view_pager_peer)
    ViewPager viewPagerPeer;
    @Bind(R.id.add_btn)
    ImageView addBtn;
    @Bind(R.id.rb_btn_layout)
    LinearLayout rbBtnLayout;
    @Bind(R.id.top_view)
    View topView;
    private List<Fragment> listFragments;

    private static final String[] TITLE_NAME = new String[]{"授渔", "益起来", "high"};
    private ViewChooseList viewChooseList;

    public static PeerFragment newInstance() {
        Bundle args = new Bundle();
        PeerFragment fragment = new PeerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_peer_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        registerEvent();
        listFragments = new ArrayList<>();
        viewPagerPeer.setOffscreenPageLimit(TITLE_NAME.length);
        for (int i = 0; i < TITLE_NAME.length; i++) {
            listFragments.add(PeerChildFragment.newInstance(i));
        }
        tabPeer.setTabMode(TabLayout.MODE_SCROLLABLE);
        FragmentTabAdapter fAdapter = new FragmentTabAdapter(getActivity().getSupportFragmentManager(), listFragments, Arrays.asList(TITLE_NAME));
        viewPagerPeer.setAdapter(fAdapter);
        tabPeer.setupWithViewPager(viewPagerPeer);

        tabPeer.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    @OnClick(R.id.add_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                showColumn();
                break;
        }
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
    private void showColumn() {
        if(viewChooseList!=null){
            if(viewChooseList.isShowing()){
                viewChooseList.dismiss();
            }else{
                viewChooseList.showAsDropDown(topView);
            }
        }else{
            viewChooseList = new ViewChooseList(getContext(), rbBtnLayout.getMeasuredWidth());
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
}
