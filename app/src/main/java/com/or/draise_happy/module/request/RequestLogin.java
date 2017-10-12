package com.or.draise_happy.module.request;

/**
 * RequestLogin
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class RequestLogin {
    private String password;
    private String phone;
    private String new_password;
    private String repeatPassword;
    private String third_platform_type;
    private String openid;
    private String nick;
    private String avatar_url;
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThird_platform_type() {
        return third_platform_type;
    }

    public void setThird_platform_type(String third_platform_type) {
        this.third_platform_type = third_platform_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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
