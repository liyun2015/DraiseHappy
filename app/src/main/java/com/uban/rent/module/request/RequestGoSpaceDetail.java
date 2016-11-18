package com.uban.rent.module.request;

import java.io.Serializable;

/**
 * RequestGoSpaceDetail 跳转详情
 * Created by dawabos on 2016/11/18.
 * Email dawabo@163.com
 */

public class RequestGoSpaceDetail implements Serializable{

    private int officeSpaceBasicInfoId;
    private double locationX;
    private double locationY;

    public int getOfficeSpaceBasicInfoId() {
        return officeSpaceBasicInfoId;
    }

    public void setOfficeSpaceBasicInfoId(int officeSpaceBasicInfoId) {
        this.officeSpaceBasicInfoId = officeSpaceBasicInfoId;
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
