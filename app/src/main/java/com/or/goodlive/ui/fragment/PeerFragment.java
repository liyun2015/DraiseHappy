package com.or.goodlive.ui.fragment;

import android.os.Bundle;

import com.or.goodlive.R;
import com.or.goodlive.base.BaseFragment;

/**
 * Created by Administrator on 2017/2/13.
 */

public class PeerFragment extends BaseFragment {

    public static PeerFragment newInstance() {
        Bundle args = new Bundle();
        PeerFragment fragment = new PeerFragment();
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
