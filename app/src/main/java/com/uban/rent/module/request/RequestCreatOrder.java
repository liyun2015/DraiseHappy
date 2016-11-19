package com.uban.rent.module.request;

import static com.baidu.location.b.g.S;

/**
 * Created by Administrator on 2016/11/19.
 */

public class RequestCreatOrder {

    /**
     * cellPhone : 17746543687
     * spaceId : 27
     * workDeskNum : 2
     * beginTime : 1461196800
     * endTime : 1461196877
     * cityId : 12
     * payMoney : 2000.5
     * reserved : android
     * workDeskType : 6
     * rentTime : 5
     * rentType : 1
     * failueTime : 1461196800
     */

    private String cellPhone;
    private int spaceId;
    private int workDeskNum;
    private int beginTime;
    private int endTime;
    private int cityId;
    private double payMoney;
    private String reserved;
    private int workDeskType;
    private int rentTime;
    private int rentType;
    private long failureTime;

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public int getWorkDeskNum() {
        return workDeskNum;
    }

    public void setWorkDeskNum(int workDeskNum) {
        this.workDeskNum = workDeskNum;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public int getWorkDeskType() {
        return workDeskType;
    }

    public void setWorkDeskType(int workDeskType) {
        this.workDeskType = workDeskType;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public int getRentType() {
        return rentType;
    }

    public void setRentType(int rentType) {
        this.rentType = rentType;
    }

    public long getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(long failureTime) {
        this.failureTime = failureTime;
    }
}
