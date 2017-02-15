package com.or.goodlive.module.request;

import static com.baidu.location.b.g.S;

/**
 * Created by Administrator on 2017/2/14.
 */

public class RegisterBean {


    /**
     * err : 成功
     * errno : 0
     * rst : {"success":true,"wait_time":60}
     * timestamp : 1487063685
     */

    private String err;
    private String errno;
    /**
     * success : true
     * wait_time : 60
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
        private boolean success;
        private int wait_time;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        private String msg;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getWait_time() {
            return wait_time;
        }

        public void setWait_time(int wait_time) {
            this.wait_time = wait_time;
        }
    }
}
