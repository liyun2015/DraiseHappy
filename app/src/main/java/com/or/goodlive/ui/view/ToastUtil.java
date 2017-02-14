package com.or.goodlive.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.or.goodlive.R;


public class ToastUtil {
	/**
	 * toast工具类
	 * @param context
	 * @param msg
     */
	public static void makeText(Context context, String msg) {
		Toast toast = new Toast(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.view_default_toast, null, false);

		TextView text = (TextView) layout.findViewById(R.id.toastMessage);
		text.setText(msg);
		text.setTextColor(Color.parseColor("#FFFFFF"));
		toast.setView(layout);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
	public static void makeTextCenter(Context context, String msg) {
		Toast toast = new Toast(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.view_default_toast, null, false);
		TextView text = (TextView) layout.findViewById(R.id.toastMessage);
		text.setText(msg);
		text.setTextColor(Color.parseColor("#FFFFFF"));
		toast.setView(layout);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
