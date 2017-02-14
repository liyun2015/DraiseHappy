package com.or.goodlive.module;

/**
 * BaseResultsBean
 * Created by dawabos on 2016/6/22.
 * Email dawabo@163.com
 */
public class BaseResultsBean {
    private int statusCode;
    private String msg;
    private String results;
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getResults() {
        return results;
    }
    public void setResults(String results) {
        this.results = results;
    }
}
