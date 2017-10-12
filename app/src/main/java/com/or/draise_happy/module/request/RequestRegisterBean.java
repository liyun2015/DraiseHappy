package com.or.goodlive.module.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/8.
 */

public class RequestRegisterBean implements Serializable {
    private String password;

    public String getPhone() {
        return phone;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    private String name;

    private String verify_code;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
