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
    private int workDeskType;

    /**
     * 营业时间起 格式为09:00
     */
    private String workHoursBegin;
    /**
     * 营业时间止 格式为18:00
     */
    private String workHoursEnd;

    public String getWorkHoursBegin() {
        return workHoursBegin;
    }

    public void setWorkHoursBegin(String workHoursBegin) {
        this.workHoursBegin = workHoursBegin;
    }

    public String getWorkHoursEnd() {
        return workHoursEnd;
    }

    public void setWorkHoursEnd(String workHoursEnd) {
        this.workHoursEnd = workHoursEnd;
    }

    public int getWorkDeskType() {
        return workDeskType;
    }

    public void setWorkDeskType(int workDeskType) {
        this.workDeskType = workDeskType;
    }

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

}
