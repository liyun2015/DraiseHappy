package com.or.goodlive.module.request;

/**
 * RequestLogin
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class RequestLogin {
    private String password;
    private String phone;
    private String new_password;
    private String resetPassword;

    public String getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.resetPassword = resetPassword;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
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

}
