package com.or.goodlive.module;

import java.util.List;

/**
 * SearchKeyWord
 * Created by dawabos on 2016/11/24.
 * Email dawabo@163.com
 */

public class SearchKeyWord {


    /**
     * err : 成功
     * errno : 0
     * rst : [{"id":"6","table_name":"news","title":"添加商品"},{"id":"3","table_name":"scene","title":"商品"}]
     * timestamp : 1487844114
     */

    private String err;
    private String errno;
    private String timestamp;
    /**
     * id : 6
     * table_name : news
     * title : 添加商品
     */

    private List<RstBean> rst;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<RstBean> getRst() {
        return rst;
    }

    public void setRst(List<RstBean> rst) {
        this.rst = rst;
    }

    public static class RstBean {
        private String id;
        private String table_name;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTable_name() {
            return table_name;
        }

        public void setTable_name(String table_name) {
            this.table_name = table_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
