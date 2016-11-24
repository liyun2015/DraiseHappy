package com.uban.rent.ui.activity.welcome.slide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uban.rent.R;
import com.uban.rent.api.config.HeaderConfig;
import com.uban.rent.ui.activity.components.LoginActivity;
import com.uban.rent.ui.activity.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class SecondSlide extends Fragment {

    @Bind(R.id.iv_welcome_image)
    ImageView ivWelcomeImage;
    @Bind(R.id.activity_welcome)
    RelativeLayout activityWelcome;
    @Bind(R.id.btn_welcome_submit)
    Button btnWelcomeSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_welcome, container, false);
        ButterKnife.bind(this, view);
        ivWelcomeImage.setBackgroundResource(R.drawable.ic_normal);
        btnWelcomeSubmit.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_welcome_submit)
    public void onClick() {
        if (HeaderConfig.isEmptyUbanToken()){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }else {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        getActivity().finish();
    }
}
