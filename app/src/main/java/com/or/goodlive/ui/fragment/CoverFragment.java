package com.or.goodlive.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.or.goodlive.R;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.ui.adapter.FragmentTabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/13.
 */

public class CoverFragment extends BaseFragment {

    public static CoverFragment newInstance() {
        Bundle args = new Bundle();
        CoverFragment fragment = new CoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cover_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }



}

