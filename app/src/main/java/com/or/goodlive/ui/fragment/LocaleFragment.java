package com.or.goodlive.ui.fragment;

import android.os.Bundle;

import com.or.goodlive.R;
import com.or.goodlive.base.BaseFragment;

/**
 * Created by Administrator on 2017/2/13.
 */

public class LocaleFragment extends BaseFragment {

    public static LocaleFragment newInstance() {
        Bundle args = new Bundle();
        LocaleFragment fragment = new LocaleFragment();
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
