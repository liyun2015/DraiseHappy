package com.uban.rent.ui.activity.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.uban.rent.ui.activity.welcome.slide.FirstSlide;
import com.uban.rent.ui.activity.welcome.slide.FourSlide;
import com.uban.rent.ui.activity.welcome.slide.SecondSlide;
import com.uban.rent.ui.activity.welcome.slide.ThreeSlide;
import com.uban.rent.ui.view.welcome.AppIntro2;

public class WelcomeActivity extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new FirstSlide());
        addSlide(new SecondSlide());
        addSlide(new ThreeSlide());
        addSlide(new FourSlide());

        /**
         * setFadeAnimation()
         setZoomAnimation()
         setFlowAnimation()
         setSlideOverAnimation()
         setDepthAnimation()
         */
        setDepthAnimation();
        // Hide Skip/Done button.
        //setFadeAnimation
        showSkipButton(false);
        showStatusBar(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
