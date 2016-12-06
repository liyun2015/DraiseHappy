package com.uban.rent.module.request;

/**
 * RequestSendValid
 * Created by dawabos on 2016/12/6.
 * Email dawabo@163.com
 */

public class RequestSendValid {
    private String phone;
    private String reserved;

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
