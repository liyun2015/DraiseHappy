/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.or.goodlive.ui.view.progress;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;

import com.or.goodlive.R;
import com.or.goodlive.ui.view.progress.internal.ThemeUtils;


abstract class BaseIndeterminateProgressDrawable extends BaseProgressDrawable
        implements Animatable {

    protected Animator[] mAnimators;

    @SuppressLint("NewApi")
    public BaseIndeterminateProgressDrawable(Context context) {
        int controlActivatedColor = ThemeUtils.getColorFromAttrRes(R.attr.colorControlActivated,
                context);
        // setTint() has been overridden for compatibility; DrawableCompat won't work because
        // wrapped Drawable won't be Animatable.
        setTint(controlActivatedColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (isStarted()) {
            invalidateSelf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {

        if (isStarted()) {
            return;
        }

        for (Animator animator : mAnimators) {
            animator.start();
        }
        invalidateSelf();
    }

    private boolean isStarted() {
        for (Animator animator : mAnimators) {
            if (animator.isStarted()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        for (Animator animator : mAnimators) {
            animator.end();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        for (Animator animator : mAnimators) {
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }
}
