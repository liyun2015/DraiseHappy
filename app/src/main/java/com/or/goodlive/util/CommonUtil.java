package com.or.goodlive.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.or.goodlive.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2016/11/19.
 */

public class CommonUtil {

    public static PopupWindowListener popupWindowListener;

    public static void setPopupWindowListener(PopupWindowListener myListener) {
        popupWindowListener = myListener;
    }

    public static interface PopupWindowListener {
        void myDissmiss();
    }
    /**
     * 弹出底部popupWindow
     *
     * @param context
     * @param popupWindow
     * @param view
     *            显示的view
     * @param popupWindowWidth
     * @param popupWindowHieght
     * @param parent
     *            父view
     * @exception
     *                ： 如果一个页面中有两个事件需要弹出PopupWindow时，需要在调用页面做好判断，
     *                否则会出现在两个事件点击后弹出的是同一个PopupWindow窗口
     */
    public static PopupWindow ShowBottomPopupWindow(Context context,
                                                    PopupWindow popupWindow, View view, int popupWindowWidth,
                                                    int popupWindowHieght, View parent) {
        if (null == popupWindow) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int heightPx = (int) (dm.density * popupWindowHieght);
            popupWindow = new PopupWindow(view, popupWindowWidth, heightPx);
        }

        popupWindow.setAnimationStyle(R.style.animation_style);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                popupWindowListener.myDissmiss();
            }

        });
        popupWindow.update();
        return popupWindow;
    }
    // wifi获取ip
    public static String getLoginIp(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return intToIp(ipAddress);
        } else {
            return getLocalIpAddress();
        }
    }

    // GPRS获取IP
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }
    public static String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

}
