package com.or.draise_happy.module.request;

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
        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }
        public static class UserBean {
            private String PHPSESSID;

            public String getPHPSESSID() {
                return PHPSESSID;
            }

            public void setPHPSESSID(String PHPSESSID) {
                this.PHPSESSID = PHPSESSID;
            }
        }
    }
}
