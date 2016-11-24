package com.uban.rent.module;

/**
 * Created by Administrator on 2016/11/23.
 */

public class WXPayProviderBean {

    /**
     * statusCode : 0
     * msg : 成功
     * results : {"sign":"3F3E496B8D90970E8D3A2BEB7F723CDB","result_code":"SUCCESS","mch_id":"1413540402","err_code":"","cityId":12,"return_msg":"OK","prepay_id":"wx20161123145151366a009a120065846681","err_code_des":"","appid":"wx560d10dd4001d136","code_url":"","return_code":"SUCCESS","nonce_str":"BS4T4FX0REDmiLaU","trade_type":"APP"}
     */

    private int statusCode;
    private String msg;
    /**
     * sign : 3F3E496B8D90970E8D3A2BEB7F723CDB
     * result_code : SUCCESS
     * mch_id : 1413540402
     * err_code :
     * cityId : 12
     * return_msg : OK
     * prepay_id : wx20161123145151366a009a120065846681
     * err_code_des :
     * appid : wx560d10dd4001d136
     * code_url :
     * return_code : SUCCESS
     * nonce_str : BS4T4FX0REDmiLaU
     * trade_type : APP
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
        private String sign;
        private String result_code;
        private String mch_id;
        private String err_code;
        private int cityId;
        private String return_msg;
        private String prepay_id;
        private String err_code_des;
        private String appid;
        private String code_url;
        private String return_code;
        private String nonce_str;
        private String trade_type;
        private String timestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getErr_code() {
            return err_code;
        }

        public void setErr_code(String err_code) {
            this.err_code = err_code;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getErr_code_des() {
            return err_code_des;
        }

        public void setErr_code_des(String err_code_des) {
            this.err_code_des = err_code_des;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
