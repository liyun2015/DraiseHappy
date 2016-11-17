package com.uban.rent.module.request;

/**
 * RequestHomeData 首页地图
 * Created by dawabos on 2016/11/17.
 * Email dawabo@163.com
 */

public class RequestHomeData {

    /**
     * locationX : 116.486388
     * locationY : 40.000828
     * shortRentFlag : 1
     */

    private double locationX;
    private double locationY;
    private int shortRentFlag;
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public int getShortRentFlag() {
        return shortRentFlag;
    }

    public void setShortRentFlag(int shortRentFlag) {
        this.shortRentFlag = shortRentFlag;
    }
}
