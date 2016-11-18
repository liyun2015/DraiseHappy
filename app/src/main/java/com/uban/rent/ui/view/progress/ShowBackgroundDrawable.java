/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.uban.rent.ui.view.progress;

/**
 * A {@code Drawable} that has ic_home_meun_member background.
 */
public interface ShowBackgroundDrawable {

    /**
     * Get whether this drawable is showing ic_home_meun_member background. The default is {@code true}.
     *
     * @return Whether this drawable is showing ic_home_meun_member background.
     */
    boolean getShowBackground();

    /**
     * Set whether this drawable should show ic_home_meun_member background. The default is {@code true}.
     *
     * @param show Whether background should be shown.
     */
    void setShowBackground(boolean show);
}
