package com.uban.rent.module;

/**
 * LoginInBean
 * Created by dawabos on 2016/6/21.
 * Email dawabo@163.com
 */
public class LoginInBean {

    /**
     * statusCode : 0
     * msg : 成功
     * results : {"headphoto":"","phone":"13900000000","token":"P6v5D-5zBM2AeWuHGtLTwIM4cFXkgSpEXwnS5afqpF8Lf3vFf3QVxkg71FM2B1bykyL_uYFHS4qC8IqMC4bL6XWhSns_zUB_fynZADKhTH6uBRRgntwoaP9SEq8Xw_Byv1Z7E9B0Nb51U09NaSonvIlt14G-PZ16zhTJDpML7H-kmSyt9IFZpMh_MvaaC4sW5oF9nmM5Vonhd2S9PWONKy0mk5j1vL_9vagCaMsECK3bmh79xDF3ZHazhKzZ8lEqeIN0OAfF1El4tNkjdy2mXCY9bDb1Ut4BEIs_gx6Nxd5ftcIPMgTKXIFXD-XUoCIy","nickname":"139****0000","wexinSex":0,"userId":30,"isBindWeixin":"1","points":0,"wexinName":""}
     */

    private int statusCode;
    private String msg;
    /**
     * headphoto :
     * phone : 13900000000
     * token : P6v5D-5zBM2AeWuHGtLTwIM4cFXkgSpEXwnS5afqpF8Lf3vFf3QVxkg71FM2B1bykyL_uYFHS4qC8IqMC4bL6XWhSns_zUB_fynZADKhTH6uBRRgntwoaP9SEq8Xw_Byv1Z7E9B0Nb51U09NaSonvIlt14G-PZ16zhTJDpML7H-kmSyt9IFZpMh_MvaaC4sW5oF9nmM5Vonhd2S9PWONKy0mk5j1vL_9vagCaMsECK3bmh79xDF3ZHazhKzZ8lEqeIN0OAfF1El4tNkjdy2mXCY9bDb1Ut4BEIs_gx6Nxd5ftcIPMgTKXIFXD-XUoCIy
     * nickname : 139****0000
     * wexinSex : 0
     * userId : 30
     * isBindWeixin : 1
     * points : 0
     * wexinName :
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
        private String headphoto;
        private String phone;
        private String token;
        private String nickname;
        private int wexinSex;
        private int userId;
        private String isBindWeixin;
        private int points;
        private String wexinName;
        private long lastLoginTime;

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getHeadphoto() {
            return headphoto;
        }

        public void setHeadphoto(String headphoto) {
            this.headphoto = headphoto;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getWexinSex() {
            return wexinSex;
        }

        public void setWexinSex(int wexinSex) {
            this.wexinSex = wexinSex;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getIsBindWeixin() {
            return isBindWeixin;
        }

        public void setIsBindWeixin(String isBindWeixin) {
            this.isBindWeixin = isBindWeixin;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public String getWexinName() {
            return wexinName;
        }

        public void setWexinName(String wexinName) {
            this.wexinName = wexinName;
        }
    }
}
