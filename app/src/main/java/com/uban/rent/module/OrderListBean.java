package com.uban.rent.module;

import java.util.List;

/**
 * OrderListBean订单列表
 * Created by dawabos on 2016/11/22.
 * Email dawabo@163.com
 */

public class OrderListBean {

    /**
     * statusCode : 0
     * msg : 成功
     * results : [{"cityId":12,"cityName":"","cellPhone":"13693133934","spaceArea":0,"refundDesc":"","type":0,"endTime":1479783600,"rentTime":2,"level":0,"failureTime":1479780767,"cancleAt":null,"gender":0,"workDeskId":0,"modifyAt":null,"fullname":"","demand":"","tradeNo":"","beginTime":1479776400,"buyerEmail":"","mark":"","dealPrice":2000,"payStatus":0,"invalidDetail":"","company":"","understandWay":"","owner":0,"payType":1,"refundAt":null,"position":"","refundApplyAt":null,"spaceNamePinyin":"lianhebangong","trade":"","state":4,"paymentAt":1479744000000,"rentType":1,"tradeArea":"望京","workDeskType":3,"spaceName":"科技寺-北新桥","officespaceOrderId":1012,"workDeskNum":1,"paymentAtString":"","creatorId":0,"origin":0,"spaceId":130,"isDeleted":1,"buyerId":"","orderPlatform":1,"createAt":1479778968000,"payMoney":246,"creatorName":"","orderNo":"SR130161122006","areaName":"朝阳","invalidType":0,"service":"","companyType":0}]
     */

    private int statusCode;
    private String msg;
    /**
     * cityId : 12
     * cityName :
     * cellPhone : 13693133934
     * spaceArea : 0
     * refundDesc :
     * type : 0
     * endTime : 1479783600
     * rentTime : 2
     * level : 0
     * failureTime : 1479780767
     * cancleAt : null
     * gender : 0
     * workDeskId : 0
     * modifyAt : null
     * fullname :
     * demand :
     * tradeNo :
     * beginTime : 1479776400
     * buyerEmail :
     * mark :
     * dealPrice : 2000
     * payStatus : 0
     * invalidDetail :
     * company :
     * understandWay :
     * owner : 0
     * payType : 1
     * refundAt : null
     * position :
     * refundApplyAt : null
     * spaceNamePinyin : lianhebangong
     * trade :
     * state : 4
     * paymentAt : 1479744000000
     * rentType : 1
     * tradeArea : 望京
     * workDeskType : 3
     * spaceName : 科技寺-北新桥
     * officespaceOrderId : 1012
     * workDeskNum : 1
     * paymentAtString :
     * creatorId : 0
     * origin : 0
     * spaceId : 130
     * isDeleted : 1
     * buyerId :
     * orderPlatform : 1
     * createAt : 1479778968000
     * payMoney : 246
     * creatorName :
     * orderNo : SR130161122006
     * areaName : 朝阳
     * invalidType : 0
     * service :
     * companyType : 0
     */

    private List<ResultsBean> results;

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

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private int cityId;
        private String cityName;
        private String cellPhone;
        private int spaceArea;
        private String refundDesc;
        private int type;
        private int endTime;
        private int rentTime;
        private int level;
        private int failureTime;
        private Object cancleAt;
        private int gender;
        private int workDeskId;
        private Object modifyAt;
        private String fullname;
        private String demand;
        private String tradeNo;
        private int beginTime;
        private String buyerEmail;
        private String mark;
        private int dealPrice;
        private int payStatus;
        private String invalidDetail;
        private String company;
        private String understandWay;
        private int owner;
        private int payType;
        private Object refundAt;
        private String position;
        private Object refundApplyAt;
        private String spaceNamePinyin;
        private String trade;
        private int state;
        private long paymentAt;
        private int rentType;
        private String tradeArea;
        private int workDeskType;
        private String spaceName;
        private int officespaceOrderId;
        private int workDeskNum;
        private String paymentAtString;
        private int creatorId;
        private int origin;
        private int spaceId;
        private int isDeleted;
        private String buyerId;
        private int orderPlatform;
        private long createAt;
        private int payMoney;
        private String creatorName;
        private String orderNo;
        private String areaName;
        private int invalidType;
        private String service;
        private int companyType;

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public int getSpaceArea() {
            return spaceArea;
        }

        public void setSpaceArea(int spaceArea) {
            this.spaceArea = spaceArea;
        }

        public String getRefundDesc() {
            return refundDesc;
        }

        public void setRefundDesc(String refundDesc) {
            this.refundDesc = refundDesc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getRentTime() {
            return rentTime;
        }

        public void setRentTime(int rentTime) {
            this.rentTime = rentTime;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getFailureTime() {
            return failureTime;
        }

        public void setFailureTime(int failureTime) {
            this.failureTime = failureTime;
        }

        public Object getCancleAt() {
            return cancleAt;
        }

        public void setCancleAt(Object cancleAt) {
            this.cancleAt = cancleAt;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getWorkDeskId() {
            return workDeskId;
        }

        public void setWorkDeskId(int workDeskId) {
            this.workDeskId = workDeskId;
        }

        public Object getModifyAt() {
            return modifyAt;
        }

        public void setModifyAt(Object modifyAt) {
            this.modifyAt = modifyAt;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(int beginTime) {
            this.beginTime = beginTime;
        }

        public String getBuyerEmail() {
            return buyerEmail;
        }

        public void setBuyerEmail(String buyerEmail) {
            this.buyerEmail = buyerEmail;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public int getDealPrice() {
            return dealPrice;
        }

        public void setDealPrice(int dealPrice) {
            this.dealPrice = dealPrice;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getInvalidDetail() {
            return invalidDetail;
        }

        public void setInvalidDetail(String invalidDetail) {
            this.invalidDetail = invalidDetail;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getUnderstandWay() {
            return understandWay;
        }

        public void setUnderstandWay(String understandWay) {
            this.understandWay = understandWay;
        }

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public Object getRefundAt() {
            return refundAt;
        }

        public void setRefundAt(Object refundAt) {
            this.refundAt = refundAt;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Object getRefundApplyAt() {
            return refundApplyAt;
        }

        public void setRefundApplyAt(Object refundApplyAt) {
            this.refundApplyAt = refundApplyAt;
        }

        public String getSpaceNamePinyin() {
            return spaceNamePinyin;
        }

        public void setSpaceNamePinyin(String spaceNamePinyin) {
            this.spaceNamePinyin = spaceNamePinyin;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getPaymentAt() {
            return paymentAt;
        }

        public void setPaymentAt(long paymentAt) {
            this.paymentAt = paymentAt;
        }

        public int getRentType() {
            return rentType;
        }

        public void setRentType(int rentType) {
            this.rentType = rentType;
        }

        public String getTradeArea() {
            return tradeArea;
        }

        public void setTradeArea(String tradeArea) {
            this.tradeArea = tradeArea;
        }

        public int getWorkDeskType() {
            return workDeskType;
        }

        public void setWorkDeskType(int workDeskType) {
            this.workDeskType = workDeskType;
        }

        public String getSpaceName() {
            return spaceName;
        }

        public void setSpaceName(String spaceName) {
            this.spaceName = spaceName;
        }

        public int getOfficespaceOrderId() {
            return officespaceOrderId;
        }

        public void setOfficespaceOrderId(int officespaceOrderId) {
            this.officespaceOrderId = officespaceOrderId;
        }

        public int getWorkDeskNum() {
            return workDeskNum;
        }

        public void setWorkDeskNum(int workDeskNum) {
            this.workDeskNum = workDeskNum;
        }

        public String getPaymentAtString() {
            return paymentAtString;
        }

        public void setPaymentAtString(String paymentAtString) {
            this.paymentAtString = paymentAtString;
        }

        public int getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(int creatorId) {
            this.creatorId = creatorId;
        }

        public int getOrigin() {
            return origin;
        }

        public void setOrigin(int origin) {
            this.origin = origin;
        }

        public int getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(int spaceId) {
            this.spaceId = spaceId;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
        }

        public int getOrderPlatform() {
            return orderPlatform;
        }

        public void setOrderPlatform(int orderPlatform) {
            this.orderPlatform = orderPlatform;
        }

        public long getCreateAt() {
            return createAt;
        }

        public void setCreateAt(long createAt) {
            this.createAt = createAt;
        }

        public int getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(int payMoney) {
            this.payMoney = payMoney;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getInvalidType() {
            return invalidType;
        }

        public void setInvalidType(int invalidType) {
            this.invalidType = invalidType;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public int getCompanyType() {
            return companyType;
        }

        public void setCompanyType(int companyType) {
            this.companyType = companyType;
        }
    }
}