package com.uban.rent.module.request;

/**
 * RequestLogin
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class RequestLogin {
    private String phone;
    private String code;
    private String weixinid;
    private String origin;
    private String wexinName;
    private int wexinSex;
    private String headphoto;

    public String getHeadphoto() {
        return headphoto;
    }

    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public String getWeixinid() {
        return weixinid;
    }

    public String getOrigin() {
        return origin;
    }

    public String getWexinName() {
        return wexinName;
    }

    public void setWexinName(String wexinName) {
        this.wexinName = wexinName;
    }

    public int getWexinSex() {
        return wexinSex;
    }

    public void setWexinSex(int wexinSex) {
        this.wexinSex = wexinSex;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
