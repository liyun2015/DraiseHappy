package com.or.goodlive.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.or.goodlive.R;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.ui.adapter.FragmentTabAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/13.
 */

public class YaMingFragment extends BaseFragment {
    @Bind(R.id.tab_yaming)
    TabLayout tabYaming;
    @Bind(R.id.view_pager_yaming)
    ViewPager viewPagerYaming;
    private List<Fragment> listFragments;

    private static final String[] TITLE_NAME = new String[]{"思客", "益家", "国学", "生命道场"};

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
        initView();
    }

    private void initView() {
        listFragments = new ArrayList<>();
        viewPagerYaming.setOffscreenPageLimit(TITLE_NAME.length);
        for (int i = 0; i < TITLE_NAME.length; i++) {
            listFragments.add(YamingChildFragment.newInstance(i));
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
