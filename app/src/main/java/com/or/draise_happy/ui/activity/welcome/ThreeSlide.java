package com.or.draise_happy.ui.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.or.draise_happy.R;
import com.or.draise_happy.control.Events;
import com.or.draise_happy.control.RxBus;
import com.or.draise_happy.ui.activity.login.LoginActivity;

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
        RxBus.getInstance().send(Events.EVENTS_FIRST, new Object());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}