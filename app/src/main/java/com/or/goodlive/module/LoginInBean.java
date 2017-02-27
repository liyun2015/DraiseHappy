package com.or.goodlive.module;

/**
 * LoginInBean
 * Created by dawabos on 2016/6/21.
 * Email dawabo@163.com
 */
public class LoginInBean {


    /**
     * err : 成功
     * errno : 0
     * rst : {"user":{"PHPSESSID":"toj1rde42dkbka2m6886fdsqm0","avatar_url":"","citycode":110,"create_time":1487125528,"deal_id":0,"device":"1","easemob_password":"","easemob_username":"","email":"","email_verified":0,"gender":"woman","grade":0,"id":1395,"last_login_time":0,"last_update_time":1487125528,"married":0,"name":"liyun","parlor_id":-1,"phone":"13693133934","phone_verified":1,"points":0,"qq_id":"","source":"okhttp/3.4.1","third_platform_type":"","user_level_id":0,"user_token":"","user_type":"0","valid":"valid","year_income":0}}
     * timestamp : 1487154011
     */

    private String err;
    private String errno;
    /**
     * user : {"PHPSESSID":"toj1rde42dkbka2m6886fdsqm0","avatar_url":"","citycode":110,"create_time":1487125528,"deal_id":0,"device":"1","easemob_password":"","easemob_username":"","email":"","email_verified":0,"gender":"woman","grade":0,"id":1395,"last_login_time":0,"last_update_time":1487125528,"married":0,"name":"liyun","parlor_id":-1,"phone":"13693133934","phone_verified":1,"points":0,"qq_id":"","source":"okhttp/3.4.1","third_platform_type":"","user_level_id":0,"user_token":"","user_type":"0","valid":"valid","year_income":0}
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
         * PHPSESSID : toj1rde42dkbka2m6886fdsqm0
         * avatar_url :
         * citycode : 110
         * create_time : 1487125528
         * deal_id : 0
         * device : 1
         * easemob_password :
         * easemob_username :
         * email :
         * email_verified : 0
         * gender : woman
         * grade : 0
         * id : 1395
         * last_login_time : 0
         * last_update_time : 1487125528
         * married : 0
         * name : liyun
         * parlor_id : -1
         * phone : 13693133934
         * phone_verified : 1
         * points : 0
         * qq_id :
         * source : okhttp/3.4.1
         * third_platform_type :
         * user_level_id : 0
         * user_token :
         * user_type : 0
         * valid : valid
         * year_income : 0
         */
        private String PHPSESSID;
        private String name;

        public String getPHPSESSID() {
            return PHPSESSID;
        }

        public void setPHPSESSID(String PHPSESSID) {
            this.PHPSESSID = PHPSESSID;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        private UserBean user;
        private String  avatar_url;
        private String  nick;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            private String PHPSESSID;
            private String avatar_url;
            private int citycode;
            private int create_time;
            private int deal_id;
            private String device;
            private String easemob_password;
            private String easemob_username;
            private String email;
            private int email_verified;
            private String gender;
            private int grade;
            private int id;
            private int last_login_time;
            private int last_update_time;
            private int married;
            private String name;
            private int parlor_id;
            private String phone;
            private int phone_verified;
            private int points;
            private String qq_id;
            private String source;
            private String third_platform_type;
            private int user_level_id;
            private String user_token;
            private String user_type;
            private String valid;
            private int year_income;

            public String getPHPSESSID() {
                return PHPSESSID;
            }

            public void setPHPSESSID(String PHPSESSID) {
                this.PHPSESSID = PHPSESSID;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public int getCitycode() {
                return citycode;
            }

            public void setCitycode(int citycode) {
                this.citycode = citycode;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getDeal_id() {
                return deal_id;
            }

            public void setDeal_id(int deal_id) {
                this.deal_id = deal_id;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
            }

            public String getEasemob_password() {
                return easemob_password;
            }

            public void setEasemob_password(String easemob_password) {
                this.easemob_password = easemob_password;
            }

            public String getEasemob_username() {
                return easemob_username;
            }

            public void setEasemob_username(String easemob_username) {
                this.easemob_username = easemob_username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getEmail_verified() {
                return email_verified;
            }

            public void setEmail_verified(int email_verified) {
                this.email_verified = email_verified;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLast_login_time() {
                return last_login_time;
            }

            public void setLast_login_time(int last_login_time) {
                this.last_login_time = last_login_time;
            }

            public int getLast_update_time() {
                return last_update_time;
            }

            public void setLast_update_time(int last_update_time) {
                this.last_update_time = last_update_time;
            }

            public int getMarried() {
                return married;
            }

            public void setMarried(int married) {
                this.married = married;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParlor_id() {
                return parlor_id;
            }

            public void setParlor_id(int parlor_id) {
                this.parlor_id = parlor_id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getPhone_verified() {
                return phone_verified;
            }

            public void setPhone_verified(int phone_verified) {
                this.phone_verified = phone_verified;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }

            public String getQq_id() {
                return qq_id;
            }

            public void setQq_id(String qq_id) {
                this.qq_id = qq_id;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getThird_platform_type() {
                return third_platform_type;
            }

            public void setThird_platform_type(String third_platform_type) {
                this.third_platform_type = third_platform_type;
            }

            public int getUser_level_id() {
                return user_level_id;
            }

            public void setUser_level_id(int user_level_id) {
                this.user_level_id = user_level_id;
            }

            public String getUser_token() {
                return user_token;
            }

            public void setUser_token(String user_token) {
                this.user_token = user_token;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getValid() {
                return valid;
            }

            public void setValid(String valid) {
                this.valid = valid;
            }

            public int getYear_income() {
                return year_income;
            }

            public void setYear_income(int year_income) {
                this.year_income = year_income;
            }
        }
    }
}
