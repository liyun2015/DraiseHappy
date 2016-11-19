package com.uban.rent.module.request;

import java.io.Serializable;

/**
 * RequestGoSpaceDetail 跳转详情
 * Created by dawabos on 2016/11/18.
 * Email dawabo@163.com
 */

public class RequestGoWorkPlaceDetail implements Serializable {
    private int workplaceDetailId;
    private int priceType;
    private int price;

    public int getWorkplaceDetailId() {
        return workplaceDetailId;
    }

    public void setWorkplaceDetailId(int workplaceDetailId) {
        this.workplaceDetailId = workplaceDetailId;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
