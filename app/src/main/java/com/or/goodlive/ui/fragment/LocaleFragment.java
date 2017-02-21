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

public class LocaleFragment extends BaseFragment {

    @Bind(R.id.tab_locale)
    TabLayout tabLocale;
    @Bind(R.id.view_pager_locale)
    ViewPager viewPagerLocale;
    private List<Fragment> listFragments;

    private static final String[] TITLE_NAME = new String[]{"初心谷", "善经济", "益项目"};
    public static LocaleFragment newInstance() {
        Bundle args = new Bundle();
        LocaleFragment fragment = new LocaleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_locale_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }
    private void initView() {
        listFragments = new ArrayList<>();
        viewPagerLocale.setOffscreenPageLimit(TITLE_NAME.length);
        for (int i = 0; i < TITLE_NAME.length; i++) {
            listFragments.add(LocaleChildFragment.newInstance(i));
        }
        tabLocale.setTabMode(TabLayout.MODE_SCROLLABLE);
        FragmentTabAdapter fAdapter = new FragmentTabAdapter(getActivity().getSupportFragmentManager(),listFragments, Arrays.asList(TITLE_NAME));
        viewPagerLocale.setAdapter(fAdapter);
        tabLocale.setupWithViewPager(viewPagerLocale);

        tabLocale.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
