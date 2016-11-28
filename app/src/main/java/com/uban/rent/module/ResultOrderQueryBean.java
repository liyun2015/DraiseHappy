package com.uban.rent.module;

/**
 * Created by Administrator on 2016/11/28.
 */

public class ResultOrderQueryBean {

    /**
     * statusCode : 0
     * msg : 查询订单成功！
     * results : {"trade_state":"SUCCESS","cityId":12,"trade_state_desc":""}
     */

    private int statusCode;
    private String msg;
    /**
     * trade_state : SUCCESS
     * cityId : 12
     * trade_state_desc :
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
        private String trade_state;
        private int cityId;
        private String trade_state_desc;

        public String getTrade_state() {
            return trade_state;
        }

        public void setTrade_state(String trade_state) {
            this.trade_state = trade_state;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getTrade_state_desc() {
            return trade_state_desc;
        }

        public void setTrade_state_desc(String trade_state_desc) {
            this.trade_state_desc = trade_state_desc;
        }
    }
}
