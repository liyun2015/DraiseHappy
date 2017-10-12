package com.or.goodlive.ui.view.welcome;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.github.paolorotolo.appintro.util.LogHelper;

import java.util.ArrayList;

/**
 * AppIntroView 引导页view
 *
 * Created by dawabos on 2016/11/30.
 * Email dawabo@163.com
 */
public abstract class AppIntroView extends AppIntroBase {
    private static final String TAG = LogHelper.makeLogTag(AppIntroView.class);

    protected View customBackgroundView;
    protected FrameLayout backgroundFrame;
    private ArrayList<Integer> transitionColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backgroundFrame = (FrameLayout) findViewById(com.github.paolorotolo.appintro.R.id.background);
    }

    @Override
    protected int getLayoutId() {
        return com.github.paolorotolo.appintro.R.layout.intro_layout2;
    }

    /**
     * Shows or hides Done button, replaced with setProgressButtonEnabled
     *
     * @deprecated use {@link #setProgressButtonEnabled(boolean)} instead.
     */
    @Deprecated
    public void showDoneButton(boolean showDone) {
        setProgressButtonEnabled(showDone);
    }

    /**
     * Override Next button
     *
     * @param imageSkipButton your drawable resource
     */
    public void setImageSkipButton(final Drawable imageSkipButton) {
        final ImageButton nextButton = (ImageButton) findViewById(com.github.paolorotolo.appintro.R.id.skip);
        nextButton.setImageDrawable(imageSkipButton);

    }

    public void setBackgroundView(View view) {
        customBackgroundView = view;
        if (customBackgroundView != null) {
            backgroundFrame.addView(customBackgroundView);
        }
    }

    /**
     * For color transition, will be shown only if color values are properly set;
     * Size of the color array must be equal to the number of slides added
     *
     * @param colors Set color values
     */
    public void setAnimationColors(ArrayList<Integer> colors) {
        transitionColors = colors;
    }
}
