package com.or.goodlive.ui.activity.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.or.goodlive.R;
import com.or.goodlive.ui.activity.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/13.
 */

public class ThreeSlide extends Fragment {

    @Bind(R.id.iv_welcome_image)
    ImageView ivWelcomeImage;
    @Bind(R.id.activity_welcome)
    RelativeLayout activityWelcome;
    @Bind(R.id.btn_welcome_submit)
    View btnWelcomeSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_welcome, container, false);
        ButterKnife.bind(this, view);
        ivWelcomeImage.setBackgroundResource(R.drawable.ic_welcome_show_3);
        return view;
    }
    @OnClick(R.id.iv_welcome_image)
    public void onClick() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}