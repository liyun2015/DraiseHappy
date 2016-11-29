package com.uban.rent.module;
/**
 * VersionBean
 * Created by dawabos on 2016/11/29.
 * Email dawabo@163.com
 */

public class VersionBean {

    /**
     * statusCode : 0
     * msg : 成功
     * results : {"content":"ddd","downpath":"http://download.uban.com/app/uban_short_rent.apk"}
     */

    private int statusCode;
    private String msg;
    /**
     * content : ddd
     * downpath : http://download.uban.com/app/uban_short_rent.apk
     */

    private ResultsBean results;

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

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String content;
        private String downpath;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDownpath() {
            return downpath;
        }

        public void setDownpath(String downpath) {
            this.downpath = downpath;
        }
    }
}
