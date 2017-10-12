package com.or.draise_happy.ui.activity.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.or.draise_happy.control.Events;
import com.or.draise_happy.control.RxBus;
import com.or.draise_happy.ui.view.welcome.AppIntroView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/2/13.
 */

public class WelcomeActivity extends AppIntroView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new FirstSlide());
        addSlide(new SecondSlide());
        addSlide(new ThreeSlide());

        setDepthAnimation();
        showSkipButton(false);
        showStatusBar(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);
        regiserEvent();
    }
    private void regiserEvent() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_FIRST)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        if(!isFinishing()){
                            finish();
                        }
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                })
                .create();
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
