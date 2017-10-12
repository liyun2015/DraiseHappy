package com.or.draise_happy.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.or.draise_happy.control.Events;
import com.or.draise_happy.control.RxBus;
import com.or.draise_happy.ui.activity.MainActivity;
import com.or.draise_happy.ui.activity.login.LoginActivity;
import com.or.draise_happy.ui.activity.other.WebViewActivity;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.SPUtils;

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
        RxBus.getInstance().send(Events.EVENTS_NEW_MESSAGE,new Object());
        SPUtils.put(context, Constants.EVENTS_HAVE_NEW_MESSAGE, "newmessage");
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "onReceive: "+ JPushInterface.ACTION_NOTIFICATION_OPENED);
            Log.d(TAG, "onReceive: "+intent.getAction());
            String mFlag = (String) SPUtils.get(context, Constants.PHPSESSID, "");

            if (TextUtils.isEmpty(mFlag)) {
                Intent loginIn = new Intent(context, LoginActivity.class);
                loginIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(loginIn);
            } else {
                    com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(mJsonString);
                    if (ExampleUtil.isRunningApp(context, Constants.APK_PACKAGENAME)) {
                        Intent intents = new Intent(context, WebViewActivity.class);
                        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intents.putExtra(WebViewActivity.WEB_VIEW_URL, jsonObject.getString("url"));
                        intents.putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, jsonObject.getString("id"));
                        intents.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, jsonObject.getString("type"));
                        context.startActivity(intents);
                    }else{
                        Intent[] intents = new Intent[2];
                        intents[0] = new Intent(context, MainActivity.class);
                        intents[1] = new Intent(context, WebViewActivity.class);
                        intents[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intents[1].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intents[1].putExtra(WebViewActivity.WEB_VIEW_URL, jsonObject.getString("url"));
                        intents[1].putExtra(WebViewActivity.WEB_VIEW_NEWS_ID, jsonObject.getString("id"));
                        intents[1].putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, jsonObject.getString("type"));
                        context.startActivities(intents);
                }

            }
        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {

    }
}
