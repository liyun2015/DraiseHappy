package com.uban.rent.module.request;

/**
 * RequestApplyMember
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class RequestApplyMember {

    /**
     * name : 涅法雷姆
     * phone : 13900000000
     * type : 1
     * status : 1
     */

    private String name;
    private String phone;
    private int type;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
