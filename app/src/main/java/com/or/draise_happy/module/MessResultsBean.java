package com.or.draise_happy.module;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class MessResultsBean {

    /**
     * err : 成功
     * errno : 0
     * rst : {"list":[{"content":"fdfd","create_time":1487650790,"id":1,"news_id":2,"push_date":"2017-02-20 00:00:00","push_time":1487520000,"status":1,"title":"2332","type":"cover"}],"pageInfo":{"hasNext":false,"hasPrev":false,"maxPage":"1","num":"1","page":"1","totalNum":"1"}}
     * timestamp : 1487816540
     */

    private String err;
    private String errno;
    /**
     * list : [{"content":"fdfd","create_time":1487650790,"id":1,"news_id":2,"push_date":"2017-02-20 00:00:00","push_time":1487520000,"status":1,"title":"2332","type":"cover"}]
     * pageInfo : {"hasNext":false,"hasPrev":false,"maxPage":"1","num":"1","page":"1","totalNum":"1"}
     */

    private RstBean rst;
    private String timestamp;

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

    public RstBean getRst() {
        return rst;
    }

    public void setRst(RstBean rst) {
        this.rst = rst;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class RstBean {
        /**
         * hasNext : false
         * hasPrev : false
         * maxPage : 1
         * num : 1
         * page : 1
         * totalNum : 1
         */

        private PageInfoBean pageInfo;
        /**
         * content : fdfd
         * create_time : 1487650790
         * id : 1
         * news_id : 2
         * push_date : 2017-02-20 00:00:00
         * push_time : 1487520000
         * status : 1
         * title : 2332
         * type : cover
         */

        private List<ListBean> list;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageInfoBean {
            private boolean hasNext;
            private boolean hasPrev;
            private String maxPage;
            private String num;
            private String page;
            private String totalNum;

            public boolean isHasNext() {
                return hasNext;
            }

            public void setHasNext(boolean hasNext) {
                this.hasNext = hasNext;
            }

            public boolean isHasPrev() {
                return hasPrev;
            }

            public void setHasPrev(boolean hasPrev) {
                this.hasPrev = hasPrev;
            }

            public String getMaxPage() {
                return maxPage;
            }

            public void setMaxPage(String maxPage) {
                this.maxPage = maxPage;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPage() {
                return page;
            }

            public void setPage(String page) {
                this.page = page;
            }

            public String getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(String totalNum) {
                this.totalNum = totalNum;
            }
        }

        public static class ListBean {
            private String content;
            private int create_time;
            private int id;
            private int news_id;
            private String push_date;
            private int push_time;
            private int status;
            private String title;
            private String type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNews_id() {
                return news_id;
            }

            public void setNews_id(int news_id) {
                this.news_id = news_id;
            }

            public String getPush_date() {
                return push_date;
            }

            public void setPush_date(String push_date) {
                this.push_date = push_date;
            }

            public int getPush_time() {
                return push_time;
            }

            public void setPush_time(int push_time) {
                this.push_time = push_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
