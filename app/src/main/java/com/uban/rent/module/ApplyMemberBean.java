package com.uban.rent.module;

/**
 * ApplyMemberBean 申请会员
 * Created by dawabos on 2016/12/1.
 * Email dawabo@163.com
 */

public class ApplyMemberBean {

    /**
     * statusCode : 0
     * msg : 成功
     * results : {"phone":"13900000000","createAt":1480577635,"status":1,"cityId":12,"name":"巴德","officespaceMemberId":33,"startAt":0,"endAt":0,"type":1,"isDeleted":0,"memberNo":"U20161201005"}
     */

    private int statusCode;
    private String msg;
    /**
     * phone : 13900000000
     * createAt : 1480577635
     * status : 1
     * cityId : 12
     * name : 巴德
     * officespaceMemberId : 33
     * startAt : 0
     * endAt : 0
     * type : 1
     * isDeleted : 0
     * memberNo : U20161201005
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
        private String phone;
        private int createAt;
        private int status;
        private int cityId;
        private String name;
        private int officespaceMemberId;
        private int startAt;
        private int endAt;
        private int type;
        private int isDeleted;
        private String memberNo;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getCreateAt() {
            return createAt;
        }

        public void setCreateAt(int createAt) {
            this.createAt = createAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOfficespaceMemberId() {
            return officespaceMemberId;
        }

        public void setOfficespaceMemberId(int officespaceMemberId) {
            this.officespaceMemberId = officespaceMemberId;
        }

        public int getStartAt() {
            return startAt;
        }

        public void setStartAt(int startAt) {
            this.startAt = startAt;
        }

        public int getEndAt() {
            return endAt;
        }

        public void setEndAt(int endAt) {
            this.endAt = endAt;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(String memberNo) {
            this.memberNo = memberNo;
        }
    }
}
