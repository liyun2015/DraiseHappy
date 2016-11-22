package com.uban.rent.module.request;

import java.io.Serializable;
import java.util.List;

/**创建订单
 * Created by Administrator on 2016/11/19.
 */

public class RequestCreatShortRentOrderBean implements Serializable{

    /**
     * msg : 成功
     * results : {"areaName":"朝阳","beginTime":1461196800,"buyerEmail":"","buyerId":"","cancleAt":1479520596000,"cellPhone":"17746543687","cityId":12,"cityName":"","company":"","companyType":0,"createAt":1479462287000,"creatorId":0,"creatorName":"","dealPrice":2000,"demand":"","endTime":1461196877,"failureTime":1461196800,"fullname":"","gender":0,"invalidDetail":"","invalidType":0,"isDeleted":1,"level":0,"mark":"","officespaceBasicinfo":{"activityForecast":"暂无活动","address":"北京市朝阳区望京SOHOT3","allRentNum":112,"artDesc":"首都第一印象建筑","businessAreaId":0,"cityId":12,"commissionRate":0,"director":"舒珏","directorPhone":"1350133906","districtId":0,"equipment":"4,5,6,8","intentPrice":0,"locationX":0,"locationY":0,"mapX":116.487145,"mapY":40.001781,"marketPrice":560,"minRentNum":1,"minRentTime":7,"minRentTimeType":2,"officespaceBasicinfoId":27,"otherSpace":"","payment":0,"perStationArea":7,"pic3dList":[{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_af0f62c9d12c4f698cde45178098908c.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":29,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_dbcc3ab7e20243d0b7618fa27758cbf6.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":30,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_9944f387165d480cb19eb3969b47157a.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":31,"spaceId":27}],"picList":[{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_df1ca10f8382494189171c62f7a554d1.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":25,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_ca6e1c47aa264903a560e7b2fa943619.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":26,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_2a392015dc4e42c3a58f5a3fd621c8d0.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":27,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_bcd15997681d4eb4a79e05ddfe07e44a.jpg","imgTitle":"望京SOHO3Q","imgType":1,"officespaceSpaceimgsId":28,"spaceId":27}],"priority":0,"propertyCorp":"锦融物业","recommend":0,"rentNum":111,"services":"15,16,18,19","spaceArea":10000,"spaceBrand":"SOHO中国","spaceBusinessArea":"望京","spaceCnName":"SOHO 3Q望京店","spaceDistrict":"朝阳","spaceFloor":"23","spaceHeight":3.8,"spaceNamePinyin":"bjwjsoho","spacePriceType":2,"spaceProfile":"办公场地，这里还会提供办公家具和其他的便利设施服务。","spaceProportion":20,"spaceType":2,"status":1,"type":1,"ubanPrice":0},"officespaceOrderId":910,"orderNo":"SR027161118009","orderPlatform":1,"origin":0,"owner":0,"payMoney":2000,"payStatus":0,"payType":0,"paymentAt":1452248014000,"paymentAtString":"","position":"","refundDesc":"","rentTime":5,"rentType":1,"service":"","spaceArea":0,"spaceId":27,"spaceName":"SOHO 3Q望京店","spaceNamePinyin":"bjwjsoho","state":4,"trade":"","tradeArea":"望京","tradeNo":"","type":0,"understandWay":"","workDeskId":0,"workDeskNum":2,"workDeskType":6}
     * statusCode : 0
     */

    private String msg;
    /**
     * areaName : 朝阳
     * beginTime : 1461196800
     * buyerEmail :
     * buyerId :
     * cancleAt : 1479520596000
     * cellPhone : 17746543687
     * cityId : 12
     * cityName :
     * company :
     * companyType : 0
     * createAt : 1479462287000
     * creatorId : 0
     * creatorName :
     * dealPrice : 2000
     * demand :
     * endTime : 1461196877
     * failureTime : 1461196800
     * fullname :
     * gender : 0
     * invalidDetail :
     * invalidType : 0
     * isDeleted : 1
     * level : 0
     * mark :
     * officespaceBasicinfo : {"activityForecast":"暂无活动","address":"北京市朝阳区望京SOHOT3","allRentNum":112,"artDesc":"首都第一印象建筑","businessAreaId":0,"cityId":12,"commissionRate":0,"director":"舒珏","directorPhone":"1350133906","districtId":0,"equipment":"4,5,6,8","intentPrice":0,"locationX":0,"locationY":0,"mapX":116.487145,"mapY":40.001781,"marketPrice":560,"minRentNum":1,"minRentTime":7,"minRentTimeType":2,"officespaceBasicinfoId":27,"otherSpace":"","payment":0,"perStationArea":7,"pic3dList":[{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_af0f62c9d12c4f698cde45178098908c.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":29,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_dbcc3ab7e20243d0b7618fa27758cbf6.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":30,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_9944f387165d480cb19eb3969b47157a.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":31,"spaceId":27}],"picList":[{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_df1ca10f8382494189171c62f7a554d1.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":25,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_ca6e1c47aa264903a560e7b2fa943619.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":26,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_2a392015dc4e42c3a58f5a3fd621c8d0.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":27,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_bcd15997681d4eb4a79e05ddfe07e44a.jpg","imgTitle":"望京SOHO3Q","imgType":1,"officespaceSpaceimgsId":28,"spaceId":27}],"priority":0,"propertyCorp":"锦融物业","recommend":0,"rentNum":111,"services":"15,16,18,19","spaceArea":10000,"spaceBrand":"SOHO中国","spaceBusinessArea":"望京","spaceCnName":"SOHO 3Q望京店","spaceDistrict":"朝阳","spaceFloor":"23","spaceHeight":3.8,"spaceNamePinyin":"bjwjsoho","spacePriceType":2,"spaceProfile":"办公场地，这里还会提供办公家具和其他的便利设施服务。","spaceProportion":20,"spaceType":2,"status":1,"type":1,"ubanPrice":0}
     * officespaceOrderId : 910
     * orderNo : SR027161118009
     * orderPlatform : 1
     * origin : 0
     * owner : 0
     * payMoney : 2000
     * payStatus : 0
     * payType : 0
     * paymentAt : 1452248014000
     * paymentAtString :
     * position :
     * refundDesc :
     * rentTime : 5
     * rentType : 1
     * service :
     * spaceArea : 0
     * spaceId : 27
     * spaceName : SOHO 3Q望京店
     * spaceNamePinyin : bjwjsoho
     * state : 4
     * trade :
     * tradeArea : 望京
     * tradeNo :
     * type : 0
     * understandWay :
     * workDeskId : 0
     * workDeskNum : 2
     * workDeskType : 6
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

    public static class ResultsBean implements Serializable {

        private String areaName;
        private int beginTime;
        private String buyerEmail;
        private String buyerId;
        private long cancleAt;
        private String cellPhone;
        private int cityId;
        private String cityName;
        private String company;
        private int companyType;
        private long createAt;
        private int creatorId;
        private String creatorName;
        private int dealPrice;
        private String demand;
        private int endTime;
        private int failureTime;
        private String fullname;
        private int gender;
        private String invalidDetail;
        private int invalidType;
        private int isDeleted;
        private int level;
        private String mark;
        /**
         * activityForecast : 暂无活动
         * address : 北京市朝阳区望京SOHOT3
         * allRentNum : 112
         * artDesc : 首都第一印象建筑
         * businessAreaId : 0
         * cityId : 12
         * commissionRate : 0
         * director : 舒珏
         * directorPhone : 1350133906
         * districtId : 0
         * equipment : 4,5,6,8
         * intentPrice : 0
         * locationX : 0
         * locationY : 0
         * mapX : 116.487145
         * mapY : 40.001781
         * marketPrice : 560
         * minRentNum : 1
         * minRentTime : 7
         * minRentTimeType : 2
         * officespaceBasicinfoId : 27
         * otherSpace :
         * payment : 0
         * perStationArea : 7
         * pic3dList : [{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_af0f62c9d12c4f698cde45178098908c.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":29,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_dbcc3ab7e20243d0b7618fa27758cbf6.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":30,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_9944f387165d480cb19eb3969b47157a.jpg","imgTitle":"望京SOHO3Q","imgType":2,"officespaceSpaceimgsId":31,"spaceId":27}]
         * picList : [{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_df1ca10f8382494189171c62f7a554d1.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":25,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_ca6e1c47aa264903a560e7b2fa943619.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":26,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_2a392015dc4e42c3a58f5a3fd621c8d0.jpg","imgTitle":"望京SOHO外景","imgType":1,"officespaceSpaceimgsId":27,"spaceId":27},{"cityId":12,"imgDesc":"","imgFormat":".jpg","imgPath":"bjwjsoho_lunbo_bcd15997681d4eb4a79e05ddfe07e44a.jpg","imgTitle":"望京SOHO3Q","imgType":1,"officespaceSpaceimgsId":28,"spaceId":27}]
         * priority : 0
         * propertyCorp : 锦融物业
         * recommend : 0
         * rentNum : 111
         * services : 15,16,18,19
         * spaceArea : 10000
         * spaceBrand : SOHO中国
         * spaceBusinessArea : 望京
         * spaceCnName : SOHO 3Q望京店
         * spaceDistrict : 朝阳
         * spaceFloor : 23
         * spaceHeight : 3.8
         * spaceNamePinyin : bjwjsoho
         * spacePriceType : 2
         * spaceProfile : 办公场地，这里还会提供办公家具和其他的便利设施服务。
         * spaceProportion : 20
         * spaceType : 2
         * status : 1
         * type : 1
         * ubanPrice : 0
         */

        private OfficespaceBasicinfoBean officespaceBasicinfo;
        private int officespaceOrderId;
        private String orderNo;
        private int orderPlatform;
        private int origin;
        private int owner;
        private int payMoney;
        private int payStatus;
        private int payType;
        private long paymentAt;
        private String paymentAtString;
        private String position;
        private String refundDesc;
        private int rentTime;
        private int rentType;
        private String service;
        private int spaceArea;
        private int spaceId;
        private String spaceName;
        private String spaceNamePinyin;
        private int state;
        private String trade;
        private String tradeArea;
        private String tradeNo;
        private int type;
        private String understandWay;
        private int workDeskId;
        private int workDeskNum;
        private int workDeskType;
        private String creatAt;
        private int failureAt;
        private int unitPrice;

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getCreatAt() {
            return creatAt;
        }

        public void setCreatAt(String creatAt) {
            this.creatAt = creatAt;
        }

        public int getFailureAt() {
            return failureAt;
        }

        public void setFailureAt(int failureAt) {
            this.failureAt = failureAt;
        }
        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
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

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
        }

        public long getCancleAt() {
            return cancleAt;
        }

        public void setCancleAt(long cancleAt) {
            this.cancleAt = cancleAt;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getCompanyType() {
            return companyType;
        }

        public void setCompanyType(int companyType) {
            this.companyType = companyType;
        }

        public long getCreateAt() {
            return createAt;
        }

        public void setCreateAt(long createAt) {
            this.createAt = createAt;
        }

        public int getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(int creatorId) {
            this.creatorId = creatorId;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public int getDealPrice() {
            return dealPrice;
        }

        public void setDealPrice(int dealPrice) {
            this.dealPrice = dealPrice;
        }

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getFailureTime() {
            return failureTime;
        }

        public void setFailureTime(int failureTime) {
            this.failureTime = failureTime;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getInvalidDetail() {
            return invalidDetail;
        }

        public void setInvalidDetail(String invalidDetail) {
            this.invalidDetail = invalidDetail;
        }

        public int getInvalidType() {
            return invalidType;
        }

        public void setInvalidType(int invalidType) {
            this.invalidType = invalidType;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public OfficespaceBasicinfoBean getOfficespaceBasicinfo() {
            return officespaceBasicinfo;
        }

        public void setOfficespaceBasicinfo(OfficespaceBasicinfoBean officespaceBasicinfo) {
            this.officespaceBasicinfo = officespaceBasicinfo;
        }

        public int getOfficespaceOrderId() {
            return officespaceOrderId;
        }

        public void setOfficespaceOrderId(int officespaceOrderId) {
            this.officespaceOrderId = officespaceOrderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOrderPlatform() {
            return orderPlatform;
        }

        public void setOrderPlatform(int orderPlatform) {
            this.orderPlatform = orderPlatform;
        }

        public int getOrigin() {
            return origin;
        }

        public void setOrigin(int origin) {
            this.origin = origin;
        }

        public int getOwner() {
            return owner;
        }

        public void setOwner(int owner) {
            this.owner = owner;
        }

        public int getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(int payMoney) {
            this.payMoney = payMoney;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public long getPaymentAt() {
            return paymentAt;
        }

        public void setPaymentAt(long paymentAt) {
            this.paymentAt = paymentAt;
        }

        public String getPaymentAtString() {
            return paymentAtString;
        }

        public void setPaymentAtString(String paymentAtString) {
            this.paymentAtString = paymentAtString;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRefundDesc() {
            return refundDesc;
        }

        public void setRefundDesc(String refundDesc) {
            this.refundDesc = refundDesc;
        }

        public int getRentTime() {
            return rentTime;
        }

        public void setRentTime(int rentTime) {
            this.rentTime = rentTime;
        }

        public int getRentType() {
            return rentType;
        }

        public void setRentType(int rentType) {
            this.rentType = rentType;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public int getSpaceArea() {
            return spaceArea;
        }

        public void setSpaceArea(int spaceArea) {
            this.spaceArea = spaceArea;
        }

        public int getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(int spaceId) {
            this.spaceId = spaceId;
        }

        public String getSpaceName() {
            return spaceName;
        }

        public void setSpaceName(String spaceName) {
            this.spaceName = spaceName;
        }

        public String getSpaceNamePinyin() {
            return spaceNamePinyin;
        }

        public void setSpaceNamePinyin(String spaceNamePinyin) {
            this.spaceNamePinyin = spaceNamePinyin;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        public String getTradeArea() {
            return tradeArea;
        }

        public void setTradeArea(String tradeArea) {
            this.tradeArea = tradeArea;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUnderstandWay() {
            return understandWay;
        }

        public void setUnderstandWay(String understandWay) {
            this.understandWay = understandWay;
        }

        public int getWorkDeskId() {
            return workDeskId;
        }

        public void setWorkDeskId(int workDeskId) {
            this.workDeskId = workDeskId;
        }

        public int getWorkDeskNum() {
            return workDeskNum;
        }

        public void setWorkDeskNum(int workDeskNum) {
            this.workDeskNum = workDeskNum;
        }

        public int getWorkDeskType() {
            return workDeskType;
        }

        public void setWorkDeskType(int workDeskType) {
            this.workDeskType = workDeskType;
        }

        public static class OfficespaceBasicinfoBean implements Serializable {
            private String activityForecast;
            private String address;
            private int allRentNum;
            private String artDesc;
            private int businessAreaId;
            private int cityId;
            private int commissionRate;
            private String director;
            private String directorPhone;
            private int districtId;
            private String equipment;
            private int intentPrice;
            private int locationX;
            private int locationY;
            private double mapX;
            private double mapY;
            private int marketPrice;
            private int minRentNum;
            private int minRentTime;
            private int minRentTimeType;
            private int officespaceBasicinfoId;
            private String otherSpace;
            private int payment;
            private int perStationArea;
            private int priority;
            private String propertyCorp;
            private int recommend;
            private int rentNum;
            private String services;
            private int spaceArea;
            private String spaceBrand;
            private String spaceBusinessArea;
            private String spaceCnName;
            private String spaceDistrict;
            private String spaceFloor;
            private double spaceHeight;
            private String spaceNamePinyin;
            private int spacePriceType;
            private String spaceProfile;
            private int spaceProportion;
            private int spaceType;
            private int status;
            private int type;
            private int ubanPrice;
            /**
             * cityId : 12
             * imgDesc :
             * imgFormat : .jpg
             * imgPath : bjwjsoho_lunbo_af0f62c9d12c4f698cde45178098908c.jpg
             * imgTitle : 望京SOHO3Q
             * imgType : 2
             * officespaceSpaceimgsId : 29
             * spaceId : 27
             */

            private List<Pic3dListBean> pic3dList;
            /**
             * cityId : 12
             * imgDesc :
             * imgFormat : .jpg
             * imgPath : bjwjsoho_lunbo_df1ca10f8382494189171c62f7a554d1.jpg
             * imgTitle : 望京SOHO外景
             * imgType : 1
             * officespaceSpaceimgsId : 25
             * spaceId : 27
             */

            private List<PicListBean> picList;

            public String getActivityForecast() {
                return activityForecast;
            }

            public void setActivityForecast(String activityForecast) {
                this.activityForecast = activityForecast;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAllRentNum() {
                return allRentNum;
            }

            public void setAllRentNum(int allRentNum) {
                this.allRentNum = allRentNum;
            }

            public String getArtDesc() {
                return artDesc;
            }

            public void setArtDesc(String artDesc) {
                this.artDesc = artDesc;
            }

            public int getBusinessAreaId() {
                return businessAreaId;
            }

            public void setBusinessAreaId(int businessAreaId) {
                this.businessAreaId = businessAreaId;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public int getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(int commissionRate) {
                this.commissionRate = commissionRate;
            }

            public String getDirector() {
                return director;
            }

            public void setDirector(String director) {
                this.director = director;
            }

            public String getDirectorPhone() {
                return directorPhone;
            }

            public void setDirectorPhone(String directorPhone) {
                this.directorPhone = directorPhone;
            }

            public int getDistrictId() {
                return districtId;
            }

            public void setDistrictId(int districtId) {
                this.districtId = districtId;
            }

            public String getEquipment() {
                return equipment;
            }

            public void setEquipment(String equipment) {
                this.equipment = equipment;
            }

            public int getIntentPrice() {
                return intentPrice;
            }

            public void setIntentPrice(int intentPrice) {
                this.intentPrice = intentPrice;
            }

            public int getLocationX() {
                return locationX;
            }

            public void setLocationX(int locationX) {
                this.locationX = locationX;
            }

            public int getLocationY() {
                return locationY;
            }

            public void setLocationY(int locationY) {
                this.locationY = locationY;
            }

            public double getMapX() {
                return mapX;
            }

            public void setMapX(double mapX) {
                this.mapX = mapX;
            }

            public double getMapY() {
                return mapY;
            }

            public void setMapY(double mapY) {
                this.mapY = mapY;
            }

            public int getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(int marketPrice) {
                this.marketPrice = marketPrice;
            }

            public int getMinRentNum() {
                return minRentNum;
            }

            public void setMinRentNum(int minRentNum) {
                this.minRentNum = minRentNum;
            }

            public int getMinRentTime() {
                return minRentTime;
            }

            public void setMinRentTime(int minRentTime) {
                this.minRentTime = minRentTime;
            }

            public int getMinRentTimeType() {
                return minRentTimeType;
            }

            public void setMinRentTimeType(int minRentTimeType) {
                this.minRentTimeType = minRentTimeType;
            }

            public int getOfficespaceBasicinfoId() {
                return officespaceBasicinfoId;
            }

            public void setOfficespaceBasicinfoId(int officespaceBasicinfoId) {
                this.officespaceBasicinfoId = officespaceBasicinfoId;
            }

            public String getOtherSpace() {
                return otherSpace;
            }

            public void setOtherSpace(String otherSpace) {
                this.otherSpace = otherSpace;
            }

            public int getPayment() {
                return payment;
            }

            public void setPayment(int payment) {
                this.payment = payment;
            }

            public int getPerStationArea() {
                return perStationArea;
            }

            public void setPerStationArea(int perStationArea) {
                this.perStationArea = perStationArea;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public String getPropertyCorp() {
                return propertyCorp;
            }

            public void setPropertyCorp(String propertyCorp) {
                this.propertyCorp = propertyCorp;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public int getRentNum() {
                return rentNum;
            }

            public void setRentNum(int rentNum) {
                this.rentNum = rentNum;
            }

            public String getServices() {
                return services;
            }

            public void setServices(String services) {
                this.services = services;
            }

            public int getSpaceArea() {
                return spaceArea;
            }

            public void setSpaceArea(int spaceArea) {
                this.spaceArea = spaceArea;
            }

            public String getSpaceBrand() {
                return spaceBrand;
            }

            public void setSpaceBrand(String spaceBrand) {
                this.spaceBrand = spaceBrand;
            }

            public String getSpaceBusinessArea() {
                return spaceBusinessArea;
            }

            public void setSpaceBusinessArea(String spaceBusinessArea) {
                this.spaceBusinessArea = spaceBusinessArea;
            }

            public String getSpaceCnName() {
                return spaceCnName;
            }

            public void setSpaceCnName(String spaceCnName) {
                this.spaceCnName = spaceCnName;
            }

            public String getSpaceDistrict() {
                return spaceDistrict;
            }

            public void setSpaceDistrict(String spaceDistrict) {
                this.spaceDistrict = spaceDistrict;
            }

            public String getSpaceFloor() {
                return spaceFloor;
            }

            public void setSpaceFloor(String spaceFloor) {
                this.spaceFloor = spaceFloor;
            }

            public double getSpaceHeight() {
                return spaceHeight;
            }

            public void setSpaceHeight(double spaceHeight) {
                this.spaceHeight = spaceHeight;
            }

            public String getSpaceNamePinyin() {
                return spaceNamePinyin;
            }

            public void setSpaceNamePinyin(String spaceNamePinyin) {
                this.spaceNamePinyin = spaceNamePinyin;
            }

            public int getSpacePriceType() {
                return spacePriceType;
            }

            public void setSpacePriceType(int spacePriceType) {
                this.spacePriceType = spacePriceType;
            }

            public String getSpaceProfile() {
                return spaceProfile;
            }

            public void setSpaceProfile(String spaceProfile) {
                this.spaceProfile = spaceProfile;
            }

            public int getSpaceProportion() {
                return spaceProportion;
            }

            public void setSpaceProportion(int spaceProportion) {
                this.spaceProportion = spaceProportion;
            }

            public int getSpaceType() {
                return spaceType;
            }

            public void setSpaceType(int spaceType) {
                this.spaceType = spaceType;
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

            public int getUbanPrice() {
                return ubanPrice;
            }

            public void setUbanPrice(int ubanPrice) {
                this.ubanPrice = ubanPrice;
            }

            public List<Pic3dListBean> getPic3dList() {
                return pic3dList;
            }

            public void setPic3dList(List<Pic3dListBean> pic3dList) {
                this.pic3dList = pic3dList;
            }

            public List<PicListBean> getPicList() {
                return picList;
            }

            public void setPicList(List<PicListBean> picList) {
                this.picList = picList;
            }

            public static class Pic3dListBean implements Serializable {
                private int cityId;
                private String imgDesc;
                private String imgFormat;
                private String imgPath;
                private String imgTitle;
                private int imgType;
                private int officespaceSpaceimgsId;
                private int spaceId;

                public int getCityId() {
                    return cityId;
                }

                public void setCityId(int cityId) {
                    this.cityId = cityId;
                }

                public String getImgDesc() {
                    return imgDesc;
                }

                public void setImgDesc(String imgDesc) {
                    this.imgDesc = imgDesc;
                }

                public String getImgFormat() {
                    return imgFormat;
                }

                public void setImgFormat(String imgFormat) {
                    this.imgFormat = imgFormat;
                }

                public String getImgPath() {
                    return imgPath;
                }

                public void setImgPath(String imgPath) {
                    this.imgPath = imgPath;
                }

                public String getImgTitle() {
                    return imgTitle;
                }

                public void setImgTitle(String imgTitle) {
                    this.imgTitle = imgTitle;
                }

                public int getImgType() {
                    return imgType;
                }

                public void setImgType(int imgType) {
                    this.imgType = imgType;
                }

                public int getOfficespaceSpaceimgsId() {
                    return officespaceSpaceimgsId;
                }

                public void setOfficespaceSpaceimgsId(int officespaceSpaceimgsId) {
                    this.officespaceSpaceimgsId = officespaceSpaceimgsId;
                }

                public int getSpaceId() {
                    return spaceId;
                }

                public void setSpaceId(int spaceId) {
                    this.spaceId = spaceId;
                }
            }

            public static class PicListBean implements Serializable {
                private int cityId;
                private String imgDesc;
                private String imgFormat;
                private String imgPath;
                private String imgTitle;
                private int imgType;
                private int officespaceSpaceimgsId;
                private int spaceId;

                public int getCityId() {
                    return cityId;
                }

                public void setCityId(int cityId) {
                    this.cityId = cityId;
                }

                public String getImgDesc() {
                    return imgDesc;
                }

                public void setImgDesc(String imgDesc) {
                    this.imgDesc = imgDesc;
                }

                public String getImgFormat() {
                    return imgFormat;
                }

                public void setImgFormat(String imgFormat) {
                    this.imgFormat = imgFormat;
                }

                public String getImgPath() {
                    return imgPath;
                }

                public void setImgPath(String imgPath) {
                    this.imgPath = imgPath;
                }

                public String getImgTitle() {
                    return imgTitle;
                }

                public void setImgTitle(String imgTitle) {
                    this.imgTitle = imgTitle;
                }

                public int getImgType() {
                    return imgType;
                }

                public void setImgType(int imgType) {
                    this.imgType = imgType;
                }

                public int getOfficespaceSpaceimgsId() {
                    return officespaceSpaceimgsId;
                }

                public void setOfficespaceSpaceimgsId(int officespaceSpaceimgsId) {
                    this.officespaceSpaceimgsId = officespaceSpaceimgsId;
                }

                public int getSpaceId() {
                    return spaceId;
                }

                public void setSpaceId(int spaceId) {
                    this.spaceId = spaceId;
                }
            }
        }
    }
}
