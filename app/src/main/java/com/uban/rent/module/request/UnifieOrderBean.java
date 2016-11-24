package com.uban.rent.module.request;

/**
 * Created by Administrator on 2016/11/23.
 */

public class UnifieOrderBean {

    /**
     * body : 优办-测试支付
     * out_trade_no : UBAN_201611221755
     * total_fee : 1
     * spbill_create_ip : 58.132.182.39
     * trade_type : APP
     * notify_url : http://bj.uban.com
     */

    private String body;
    private String out_trade_no;
    private String total_fee;
    private String spbill_create_ip;
    private String trade_type;
    private String notify_url;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
