package com.uban.rent.module;

import java.util.List;

/**
 * VerifyMemberBean
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class VerifyMemberBean {

    /**
     * msg : 成功
     * results : [{"createAt":1478169717,"endAt":1482040370,"isDeleted":1,"memberNo":"U20161118001","name":"涅法雷姆","officespaceMemberId":123,"phone":"13900000000","startAt":1479448370,"status":2,"type":1},{"createAt":1468169717,"endAt":1472040370,"isDeleted":1,"memberNo":"U20161117001","name":"涅法雷姆","officespaceMemberId":122,"phone":"13900000000","startAt":1469448370,"status":2,"type":1}]
     * statusCode : 0
     */

    private String msg;
    private int statusCode;
    /**
     * createAt : 1478169717
     * endAt : 1482040370
     * isDeleted : 1
     * memberNo : U20161118001
     * name : 涅法雷姆
     * officespaceMemberId : 123
     * phone : 13900000000
     * startAt : 1479448370
     * status : 2
     * type : 1
     */

    private List<ResultsBean> results;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private int createAt;
        private int endAt;
        private int isDeleted;
        private String memberNo;
        private String name;
        private int officespaceMemberId;
        private String phone;
        private int startAt;
        private int status;
        private int type;

        public int getCreateAt() {
            return createAt;
        }

        public void setCreateAt(int createAt) {
            this.createAt = createAt;
        }

        public int getEndAt() {
            return endAt;
        }

        public void setEndAt(int endAt) {
            this.endAt = endAt;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStartAt() {
            return startAt;
        }

        public void setStartAt(int startAt) {
            this.startAt = startAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
