package com.or.goodlive.module;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ColumnBean {
    private boolean isClick=false;
    private String value;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
