package com.uban.rent.module;

import java.util.List;

/**
 * SearchKeyWord
 * Created by dawabos on 2016/11/24.
 * Email dawabo@163.com
 */

public class SearchKeyWord {


    /**
     * statusCode : 0
     * msg : 成功
     * results : ["融科望京中心","望京大厦","望京SOHO","望京国际商业中心","东亚望京中心","望京科技园区","望京绿地中心中国锦"]
     */

    private int statusCode;
    private String msg;
    private List<String> results;

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

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
