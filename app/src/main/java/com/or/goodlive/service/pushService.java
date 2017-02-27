package com.or.goodlive.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class pushService extends BroadcastReceiver {

    private static final String TAG = "pushService";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String mJsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (TextUtils.isEmpty(mJsonString)){
            return;
        }
        if (mJsonString.equals("{}")) {
            return;
        }

        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "onReceive: "+ JPushInterface.ACTION_NOTIFICATION_OPENED);
            Log.d(TAG, "onReceive: "+intent.getAction());
        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {

    }
}
