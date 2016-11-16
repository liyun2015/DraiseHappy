package com.uban.rent.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.uban.rent.R;


/**
 * MaterialDesignProgress
 * Created by dawabos on 2016/8/12.
 * Email dawabo@163.com
 */
public class LoadingProgress extends Dialog {
    public LoadingProgress(Context context) {
        super(context, R.style.actionSheetDialog);
        init();
    }

    private void init() {
        setContentView(R.layout.view_material_design_progress);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
//		params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.7f;
        params.format = PixelFormat.TRANSPARENT;

        // 触摸对话框以外的地方取消对话框
        setCanceledOnTouchOutside(false);
    }
}
