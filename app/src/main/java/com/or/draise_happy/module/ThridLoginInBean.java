package com.or.draise_happy.module;

/**
 * Created by Wang on 2017/2/26.
 */

public class ThridLoginInBean {
    private String err;
    private String errno;

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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public RstBean getRst() {
        return rst;
    }

    public void setRst(RstBean rst) {
        this.rst = rst;
    }

    private RstBean rst;
    public static class RstBean {

    }

}
