package com.uban.rent.module.request;

/**
 * RequestSpaceDetail 空间详情
 * Created by dawabos on 2016/11/17.
 * Email dawabo@163.com
 */
public class RequestSpaceDetail {
    private int officespaceBasicinfoId;
    private double locationX;
    private double locationY;

    public int getShortRentFlag() {
        return shortRentFlag;
    }

    public void setShortRentFlag(int shortRentFlag) {
        this.shortRentFlag = shortRentFlag;
    }

    private int shortRentFlag;//工位类型标志  0：全部，1工位信息  2会议室活动场地


    public int getOfficespaceBasicinfoId() {
        return officespaceBasicinfoId;
    }

    public void setOfficespaceBasicinfoId(int officespaceBasicinfoId) {
        this.officespaceBasicinfoId = officespaceBasicinfoId;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }
}
