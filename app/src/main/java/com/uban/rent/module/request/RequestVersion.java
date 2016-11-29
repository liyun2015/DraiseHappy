package com.uban.rent.module.request;
/**
 * RequestVersion 版本更新
 * Created by dawabos on 2016/11/29.
 * Email dawabo@163.com
 */
public class RequestVersion {
    private int appType;
    private String name;

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
