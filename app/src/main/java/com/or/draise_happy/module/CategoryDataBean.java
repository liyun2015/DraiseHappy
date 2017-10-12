package com.or.draise_happy.module;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class CategoryDataBean {

    /**
     * err : 成功
     * errno : 0
     * rst : [{"create_time":0,"fid":0,"id":1,"img":"","name":"直播","sort":1,"status":1,"update_time":"0000-00-00 00:00:00"},{"create_time":0,"fid":0,"id":2,"img":"","name":"音乐","sort":1,"status":1,"update_time":"0000-00-00 00:00:00"},{"create_time":1487048573,"fid":0,"id":3,"img":"","name":"教育","sort":3,"status":1,"update_time":"2017-02-14 13:02:53"}]
     * timestamp : 1487734720
     */

    private String err;
    private String errno;
    private String timestamp;
    /**
     * create_time : 0
     * fid : 0
     * id : 1
     * img :
     * name : 直播
     * sort : 1
     * status : 1
     * update_time : 0000-00-00 00:00:00
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
        private int create_time;
        private int fid;
        private int id;
        private String img;
        private String name;
        private int sort;
        private int status;
        private String update_time;

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
