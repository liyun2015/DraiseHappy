package com.or.goodlive.module;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CoverDataBean {

    /**
     * err : 成功
     * errno : 0
     * rst : {"homeact":[{"create_time":1487561169,"etime":1514649600,"id":2,"note":"dfd","photo":"http://zhibo.xiaozhubanjia.com/photo/201702201126053040.jpg","postion":1,"sort":2,"status":1,"stime":1459440000,"update_time":"2017-02-20 11:26:09","url":"http://www.yunpaper.com/youngbox/page/regcoupon.html?qrcode=MjAxNjA5MjEwMSwyMDE2MDkyMTAyLDIwMTYwOTIxMDMsMjAxNjA5MjEwNCwyMDE2MDkyMTA1"}],"list":[{"author":"李岩","category_id":1,"category_name":"直播","comment_num":0,"content":"","create_time":1487472001,"id":6,"is_like":0,"like_num":0,"pic":"http://zhibo.xiaozhubanjia.com/photo/201702131809107466.jpg","praise_num":0,"pub_time":"0000-00-00 00:00:00","share_num":0,"status":1,"sub":"反对大幅度反对法","title":"添加商品","title_pic":"http://zhibo.xiaozhubanjia.com/photo/201702131812285192.jpg","type":1,"update_time":"2017-02-19 10:40:01","video_url":""}],"pageInfo":{"hasNext":false,"hasPrev":false,"maxPage":"1","num":"3","page":"1","totalNum":"3"}}
     * timestamp : 1487578315
     */

    private String err;
    private String errno;
    /**
     * homeact : [{"create_time":1487561169,"etime":1514649600,"id":2,"note":"dfd","photo":"http://zhibo.xiaozhubanjia.com/photo/201702201126053040.jpg","postion":1,"sort":2,"status":1,"stime":1459440000,"update_time":"2017-02-20 11:26:09","url":"http://www.yunpaper.com/youngbox/page/regcoupon.html?qrcode=MjAxNjA5MjEwMSwyMDE2MDkyMTAyLDIwMTYwOTIxMDMsMjAxNjA5MjEwNCwyMDE2MDkyMTA1"}]
     * list : [{"author":"李岩","category_id":1,"category_name":"直播","comment_num":0,"content":"","create_time":1487472001,"id":6,"is_like":0,"like_num":0,"pic":"http://zhibo.xiaozhubanjia.com/photo/201702131809107466.jpg","praise_num":0,"pub_time":"0000-00-00 00:00:00","share_num":0,"status":1,"sub":"反对大幅度反对法","title":"添加商品","title_pic":"http://zhibo.xiaozhubanjia.com/photo/201702131812285192.jpg","type":1,"update_time":"2017-02-19 10:40:01","video_url":""}]
     * pageInfo : {"hasNext":false,"hasPrev":false,"maxPage":"1","num":"3","page":"1","totalNum":"3"}
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
         * num : 3
         * page : 1
         * totalNum : 3
         */

        private PageInfoBean pageInfo;
        /**
         * create_time : 1487561169
         * etime : 1514649600
         * id : 2
         * note : dfd
         * photo : http://zhibo.xiaozhubanjia.com/photo/201702201126053040.jpg
         * postion : 1
         * sort : 2
         * status : 1
         * stime : 1459440000
         * update_time : 2017-02-20 11:26:09
         * url : http://www.yunpaper.com/youngbox/page/regcoupon.html?qrcode=MjAxNjA5MjEwMSwyMDE2MDkyMTAyLDIwMTYwOTIxMDMsMjAxNjA5MjEwNCwyMDE2MDkyMTA1
         */

        private List<HomeactBean> homeact;
        /**
         * author : 李岩
         * category_id : 1
         * category_name : 直播
         * comment_num : 0
         * content :
         * create_time : 1487472001
         * id : 6
         * is_like : 0
         * like_num : 0
         * pic : http://zhibo.xiaozhubanjia.com/photo/201702131809107466.jpg
         * praise_num : 0
         * pub_time : 0000-00-00 00:00:00
         * share_num : 0
         * status : 1
         * sub : 反对大幅度反对法
         * title : 添加商品
         * title_pic : http://zhibo.xiaozhubanjia.com/photo/201702131812285192.jpg
         * type : 1
         * update_time : 2017-02-19 10:40:01
         * video_url :
         */

        private List<ListBean> list;

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

        public static class ListBean {
            private String author;
            private int category_id;
            private String category_name;
            private int comment_num;
            private String content;
            private int create_time;
            private int id;
            private int is_like;
            private int like_num;
            private String pic;
            private int praise_num;
            private String pub_time;
            private int share_num;
            private int status;
            private String sub;
            private String title;
            private String title_pic;
            private int type;
            private String update_time;
            private String video_url;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public int getComment_num() {
                return comment_num;
            }

            public void setComment_num(int comment_num) {
                this.comment_num = comment_num;
            }

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

            public int getIs_like() {
                return is_like;
            }

            public void setIs_like(int is_like) {
                this.is_like = is_like;
            }

            public int getLike_num() {
                return like_num;
            }

            public void setLike_num(int like_num) {
                this.like_num = like_num;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getPraise_num() {
                return praise_num;
            }

            public void setPraise_num(int praise_num) {
                this.praise_num = praise_num;
            }

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }

            public int getShare_num() {
                return share_num;
            }

            public void setShare_num(int share_num) {
                this.share_num = share_num;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSub() {
                return sub;
            }

            public void setSub(String sub) {
                this.sub = sub;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle_pic() {
                return title_pic;
            }

            public void setTitle_pic(String title_pic) {
                this.title_pic = title_pic;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }
        }
    }
}
