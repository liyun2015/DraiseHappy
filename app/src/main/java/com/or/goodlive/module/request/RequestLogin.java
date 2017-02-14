package com.or.goodlive.module.request;

/**
 * RequestLogin
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class RequestLogin {
    private String password;
    private String phone;

    private String name;

    private String verify_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }
}
