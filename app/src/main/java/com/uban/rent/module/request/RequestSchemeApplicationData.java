package com.uban.rent.module.request;

/**
 * RequestSchemeApplicationData 点击网页跳转app端传参
 * Created by dawabos on 2016/11/23.
 * Email dawabo@163.com
 */

public class RequestSchemeApplicationData{
    //     http://uban.com:8888/stationDetail?stationDetailId=123&spaceDetailId=234

    //空间
    private int spaceDetailId;
    private double locationX;
    private double locationY;
    //工位
    private int stationDetailId;
    //订单
    private int orderPriceType;
    private int orderPrice;


    public int getSpaceDetailId() {
        return spaceDetailId;
    }

    public void setSpaceDetailId(int spaceDetailId) {
        this.spaceDetailId = spaceDetailId;
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

    public int getStationDetailId() {
        return stationDetailId;
    }

    public void setStationDetailId(int stationDetailId) {
        this.stationDetailId = stationDetailId;
    }

    public int getOrderPriceType() {
        return orderPriceType;
    }

    public void setOrderPriceType(int orderPriceType) {
        this.orderPriceType = orderPriceType;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
