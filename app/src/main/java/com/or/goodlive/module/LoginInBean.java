package com.or.goodlive.module;

/**
 * LoginInBean
 * Created by dawabos on 2016/6/21.
 * Email dawabo@163.com
 */
public class LoginInBean {


    /**
     * msg : 成功
     * results : {"headphoto":"http://wx.qlogo.cn/mmopen/hnwQ6KbEHeX6Yb3M43g12WFreAy1lPNXUPTLfNnFWicP2icichvguVJrTjia2DO03rzL1XYyuicPjWBvicYvoibmG8GUqibeQDbkdocD/0","isBindWeixin":"1","isVip":0,"lastLoginTime":1481169529,"nickname":"138****0000","phone":"13800000000","points":0,"token":"P6v5D-5zBM2AeWuHGtLTwOa42JFezJUEPbkC5NhAoOedEEN8BX_yJGy5wfNnycrX4VtURXF29Nm1hrMGJGsZzqzjB9t2Lhu4n9gLuwZlf5JFfsf9dcPuDv9xPp9Cqm2MzmLODEhClFWici1tqF8TAADQwokjcJjRcdKzfSQQeTGSKps71la3DzvlAKPmbhyzLKkN-vSk4ODZjkEzVIEuvVLnXwt0_C3jTcpdEuMGtKf4o3rn56U12ZqvvAFJvcPr7VdcUl89KjqdWpQ3zbIebP91yYP5p90mAecuSFtDkJ_0NjCHXkb4t15U6Yz_eHpqrBmVClgVWoGnwFenbJhA0SDO18V_wHMAxunfyD1ufM41IxAx1S5eTf7XGfmHduEKTU4P-CbL5Oemn8i91kZUPenfvm6shijd7fFTj7YlgwGuYWLbgAac5QLQjOLr2TiNf7kO0Szy08VyK2kMsRN1-g0LCqncALiX6f6qyXBi5tHozyVdILOUH23NZSbH8OYhIKPU_ajVkJ3O4kd1zt3bogEAzFmUuIZDd98v650A2Zg","userId":11714,"wexinName":"武守磊","wexinSex":0}
     * statusCode : 0
     */

    private String msg;
    /**
     * headphoto : http://wx.qlogo.cn/mmopen/hnwQ6KbEHeX6Yb3M43g12WFreAy1lPNXUPTLfNnFWicP2icichvguVJrTjia2DO03rzL1XYyuicPjWBvicYvoibmG8GUqibeQDbkdocD/0
     * isBindWeixin : 1
     * isVip : 0
     * lastLoginTime : 1481169529
     * nickname : 138****0000
     * phone : 13800000000
     * points : 0
     * token : P6v5D-5zBM2AeWuHGtLTwOa42JFezJUEPbkC5NhAoOedEEN8BX_yJGy5wfNnycrX4VtURXF29Nm1hrMGJGsZzqzjB9t2Lhu4n9gLuwZlf5JFfsf9dcPuDv9xPp9Cqm2MzmLODEhClFWici1tqF8TAADQwokjcJjRcdKzfSQQeTGSKps71la3DzvlAKPmbhyzLKkN-vSk4ODZjkEzVIEuvVLnXwt0_C3jTcpdEuMGtKf4o3rn56U12ZqvvAFJvcPr7VdcUl89KjqdWpQ3zbIebP91yYP5p90mAecuSFtDkJ_0NjCHXkb4t15U6Yz_eHpqrBmVClgVWoGnwFenbJhA0SDO18V_wHMAxunfyD1ufM41IxAx1S5eTf7XGfmHduEKTU4P-CbL5Oemn8i91kZUPenfvm6shijd7fFTj7YlgwGuYWLbgAac5QLQjOLr2TiNf7kO0Szy08VyK2kMsRN1-g0LCqncALiX6f6qyXBi5tHozyVdILOUH23NZSbH8OYhIKPU_ajVkJ3O4kd1zt3bogEAzFmUuIZDd98v650A2Zg
     * userId : 11714
     * wexinName : 武守磊
     * wexinSex : 0
     */

    private ResultsBean results;
    private int statusCode;

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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static class ResultsBean {
        private String headphoto;
        private String isBindWeixin;
        private int isVip;
        private int lastLoginTime;
        private String nickname;
        private String phone;
        private int points;
        private String token;
        private int userId;
        private String wexinName;
        private int wexinSex;

        public String getHeadphoto() {
            return headphoto;
        }

        public void setHeadphoto(String headphoto) {
            this.headphoto = headphoto;
        }

        public String getIsBindWeixin() {
            return isBindWeixin;
        }

        public void setIsBindWeixin(String isBindWeixin) {
            this.isBindWeixin = isBindWeixin;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public int getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(int lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getWexinName() {
            return wexinName;
        }

        public void setWexinName(String wexinName) {
            this.wexinName = wexinName;
        }

        public int getWexinSex() {
            return wexinSex;
        }

        public void setWexinSex(int wexinSex) {
            this.wexinSex = wexinSex;
        }
    }
}
