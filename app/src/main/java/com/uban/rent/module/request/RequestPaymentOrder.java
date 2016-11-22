package com.uban.rent.module.request;

/**
 * Created by Administrator on 2016/11/21.
 */

public class RequestPaymentOrder {

    /**
     * orderNo : SR027161118009
     * payType : 1
     * payStatus : 1
     * paymentAtString : 2016-01-08 18:13:34
     * dealPrice : 2000.0
     */

    private String orderNo;
    private int payType;
    private int payStatus;
    private String paymentAtString;
    private double dealPrice;
    private int state;
    private int workDeskType;
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getWorkDeskType() {
        return workDeskType;
    }
    public void setWorkDeskType(int workDeskType) {
        this.workDeskType = workDeskType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPaymentAtString() {
        return paymentAtString;
    }

    public void setPaymentAtString(String paymentAtString) {
        this.paymentAtString = paymentAtString;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }
}
