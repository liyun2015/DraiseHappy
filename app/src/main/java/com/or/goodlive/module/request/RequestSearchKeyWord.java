package com.or.goodlive.module.request;

/**
 * RequestSearchKeyWord 联想词
 * Created by dawabos on 2016/11/24.
 * Email dawabo@163.com
 */

public class RequestSearchKeyWord {
    private String title;
    private String news_id;
    private String table_name;
    private String content;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
