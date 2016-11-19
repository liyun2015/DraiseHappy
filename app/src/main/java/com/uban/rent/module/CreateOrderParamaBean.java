package com.uban.rent.module;

import java.io.Serializable;

/**
 * CreateOrderParamaBean 创建订单参数
 * Created by dawabos on 2016/11/19.
 * Email dawabo@163.com
 */

public class CreateOrderParamaBean implements Serializable{
    private int spaceDeskId;
    private String spaceDeskName;
    private String spaceDeskAddress;
    private int price;
    private int priceType;
    private int workDeskPriceType;

    public int getSpaceDeskId() {
        return spaceDeskId;
    }

    public void setSpaceDeskId(int spaceDeskId) {
        this.spaceDeskId = spaceDeskId;
    }

    public String getSpaceDeskName() {
        return spaceDeskName;
    }

    public void setSpaceDeskName(String spaceDeskName) {
        this.spaceDeskName = spaceDeskName;
    }

    public String getSpaceDeskAddress() {
        return spaceDeskAddress;
    }

    public void setSpaceDeskAddress(String spaceDeskAddress) {
        this.spaceDeskAddress = spaceDeskAddress;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getWorkDeskPriceType() {
        return workDeskPriceType;
    }

    public void setWorkDeskPriceType(int workDeskPriceType) {
        this.workDeskPriceType = workDeskPriceType;
    }
}
