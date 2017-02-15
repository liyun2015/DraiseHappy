package com.or.goodlive.module;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CoverDataBean {


    /**
     * err : 成功
     * errno : 0
     * rst : {"homeact":[{"create_time":1453951238,"etime":1514649600,"id":2,"note":"","photo":"201604022151417552.jpg","postion":1,"sort":2,"status":1,"stime":1459440000,"update_time":"2017-02-13 11:25:14","url":"http://www.yunpaper.com/youngbox/page/regcoupon.html?qrcode=MjAxNjA5MjEwMSwyMDE2MDkyMTAyLDIwMTYwOTIxMDMsMjAxNjA5MjEwNCwyMDE2MDkyMTA1"}],"list":[],"pageInfo":{"hasNext":false,"hasPrev":false,"maxPage":"0","num":"10","page":"1","totalNum":"0"}}
     * timestamp : 1487157272
     */

    private String err;
    private String errno;
    /**
     * homeact : [{"create_time":1453951238,"etime":1514649600,"id":2,"note":"","photo":"201604022151417552.jpg","postion":1,"sort":2,"status":1,"stime":1459440000,"update_time":"2017-02-13 11:25:14","url":"http://www.yunpaper.com/youngbox/page/regcoupon.html?qrcode=MjAxNjA5MjEwMSwyMDE2MDkyMTAyLDIwMTYwOTIxMDMsMjAxNjA5MjEwNCwyMDE2MDkyMTA1"}]
     * list : []
     * pageInfo : {"hasNext":false,"hasPrev":false,"maxPage":"0","num":"10","page":"1","totalNum":"0"}
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
         * maxPage : 0
         * num : 10
         * page : 1
         * totalNum : 0
         */

        private PageInfoBean pageInfo;
        /**
         * create_time : 1453951238
         * etime : 1514649600
         * id : 2
         * note :
         * photo : 201604022151417552.jpg
         * postion : 1
         * sort : 2
         * status : 1
         * stime : 1459440000
         * update_time : 2017-02-13 11:25:14
         * url : http://www.yunpaper.com/youngbox/page/regcoupon.html?qrcode=MjAxNjA5MjEwMSwyMDE2MDkyMTAyLDIwMTYwOTIxMDMsMjAxNjA5MjEwNCwyMDE2MDkyMTA1
         */

        private List<HomeactBean> homeact;
        private List<?> list;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public List<HomeactBean> getHomeact() {
            return homeact;
        }

        public void setHomeact(List<HomeactBean> homeact) {
            this.homeact = homeact;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
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

        public static class HomeactBean {
            private int create_time;
            private int etime;
            private int id;
            private String note;
            private String photo;
            private int postion;
            private int sort;
            private int status;
            private int stime;
            private String update_time;
            private String url;

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getEtime() {
                return etime;
            }

            public void setEtime(int etime) {
                this.etime = etime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getPostion() {
                return postion;
            }

            public void setPostion(int postion) {
                this.postion = postion;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStime() {
                return stime;
            }

            public void setStime(int stime) {
                this.stime = stime;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
