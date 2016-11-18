package com.uban.rent.module;

import java.io.Serializable;
import java.util.List;

/**
 * HomeDatasBean 首页结果数据
 * Created by dawabos on 2016/11/17.
 * Email dawabo@163.com
 */

public class HomeDatasBean{

    /**
     * msg : 成功
     * results : {"datas":[{"activityForecast":"","address":"","allRentNum":0,"artDesc":"","businessAreaId":53,"cityId":12,"commissionRate":0,"director":"","directorPhone":"","distance":1960,"districtId":105035,"equipment":"","intentPrice":0,"mapX":116.481352,"mapY":40.018014,"marketPrice":0,"minRentNum":0,"minRentTime":0,"minRentTimeType":0,"officespaceBasicinfoId":190,"otherSpace":"","payment":0,"perStationArea":0,"priority":0,"propertyCorp":"","recommend":0,"rentNum":0,"services":"","shortestFlag":1,"spaceArea":0,"spaceBrand":"","spaceBusinessArea":"","spaceCnName":"梦想加-望京","spaceDistrict":"朝阳","spaceFloor":"","spaceHeight":0,"spaceNamePinyin":"bangongmengxiang","spacePriceType":0,"spaceProfile":"","spaceProportion":0,"spaceType":0,"status":0,"type":2,"ubanPrice":0},{"activityForecast":"","address":"","allRentNum":0,"artDesc":"","businessAreaId":98,"cityId":12,"commissionRate":0,"director":"","directorPhone":"","distance":8039,"districtId":105035,"equipment":"","intentPrice":0,"mapX":116.500388,"mapY":39.929405,"marketPrice":0,"minRentNum":0,"minRentTime":0,"minRentTimeType":0,"officespaceBasicinfoId":127,"otherSpace":"","payment":0,"perStationArea":0,"priority":0,"propertyCorp":"","recommend":0,"rentNum":0,"services":"","shortestFlag":0,"spaceArea":0,"spaceBrand":"","spaceBusinessArea":"","spaceCnName":"时空堂","spaceDistrict":"朝阳","spaceFloor":"","spaceHeight":0,"spaceNamePinyin":"lianhebang","spacePriceType":0,"spaceProfile":"","spaceProportion":0,"spaceType":0,"status":0,"type":2,"ubanPrice":0},{"activityForecast":"","address":"","allRentNum":0,"artDesc":"","businessAreaId":53,"cityId":12,"commissionRate":0,"director":"","directorPhone":"","distance":8300,"districtId":105035,"equipment":"","intentPrice":0,"mapX":116.422302,"mapY":39.944719,"marketPrice":0,"minRentNum":0,"minRentTime":0,"minRentTimeType":0,"officespaceBasicinfoId":130,"otherSpace":"","payment":0,"perStationArea":0,"priority":0,"propertyCorp":"","recommend":0,"rentNum":0,"services":"","shortestFlag":0,"spaceArea":0,"spaceBrand":"","spaceBusinessArea":"","spaceCnName":"科技寺-北新桥","spaceDistrict":"朝阳","spaceFloor":"","spaceHeight":0,"spaceNamePinyin":"lianhebangong","spacePriceType":0,"spaceProfile":"","spaceProportion":0,"spaceType":0,"status":0,"type":2,"ubanPrice":0}],"totals":3}
     * statusCode : 0
     */

    private String msg;
    /**
     * datas : [{"activityForecast":"","address":"","allRentNum":0,"artDesc":"","businessAreaId":53,"cityId":12,"commissionRate":0,"director":"","directorPhone":"","distance":1960,"districtId":105035,"equipment":"","intentPrice":0,"mapX":116.481352,"mapY":40.018014,"marketPrice":0,"minRentNum":0,"minRentTime":0,"minRentTimeType":0,"officespaceBasicinfoId":190,"otherSpace":"","payment":0,"perStationArea":0,"priority":0,"propertyCorp":"","recommend":0,"rentNum":0,"services":"","shortestFlag":1,"spaceArea":0,"spaceBrand":"","spaceBusinessArea":"","spaceCnName":"梦想加-望京","spaceDistrict":"朝阳","spaceFloor":"","spaceHeight":0,"spaceNamePinyin":"bangongmengxiang","spacePriceType":0,"spaceProfile":"","spaceProportion":0,"spaceType":0,"status":0,"type":2,"ubanPrice":0},{"activityForecast":"","address":"","allRentNum":0,"artDesc":"","businessAreaId":98,"cityId":12,"commissionRate":0,"director":"","directorPhone":"","distance":8039,"districtId":105035,"equipment":"","intentPrice":0,"mapX":116.500388,"mapY":39.929405,"marketPrice":0,"minRentNum":0,"minRentTime":0,"minRentTimeType":0,"officespaceBasicinfoId":127,"otherSpace":"","payment":0,"perStationArea":0,"priority":0,"propertyCorp":"","recommend":0,"rentNum":0,"services":"","shortestFlag":0,"spaceArea":0,"spaceBrand":"","spaceBusinessArea":"","spaceCnName":"时空堂","spaceDistrict":"朝阳","spaceFloor":"","spaceHeight":0,"spaceNamePinyin":"lianhebang","spacePriceType":0,"spaceProfile":"","spaceProportion":0,"spaceType":0,"status":0,"type":2,"ubanPrice":0},{"activityForecast":"","address":"","allRentNum":0,"artDesc":"","businessAreaId":53,"cityId":12,"commissionRate":0,"director":"","directorPhone":"","distance":8300,"districtId":105035,"equipment":"","intentPrice":0,"mapX":116.422302,"mapY":39.944719,"marketPrice":0,"minRentNum":0,"minRentTime":0,"minRentTimeType":0,"officespaceBasicinfoId":130,"otherSpace":"","payment":0,"perStationArea":0,"priority":0,"propertyCorp":"","recommend":0,"rentNum":0,"services":"","shortestFlag":0,"spaceArea":0,"spaceBrand":"","spaceBusinessArea":"","spaceCnName":"科技寺-北新桥","spaceDistrict":"朝阳","spaceFloor":"","spaceHeight":0,"spaceNamePinyin":"lianhebangong","spacePriceType":0,"spaceProfile":"","spaceProportion":0,"spaceType":0,"status":0,"type":2,"ubanPrice":0}]
     * totals : 3
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
        private int totals;
        /**
         * activityForecast :
         * address :
         * allRentNum : 0
         * artDesc :
         * businessAreaId : 53
         * cityId : 12
         * commissionRate : 0
         * director :
         * directorPhone :
         * distance : 1960
         * districtId : 105035
         * equipment :
         * intentPrice : 0
         * mapX : 116.481352
         * mapY : 40.018014
         * marketPrice : 0
         * minRentNum : 0
         * minRentTime : 0
         * minRentTimeType : 0
         * officespaceBasicinfoId : 190
         * otherSpace :
         * payment : 0
         * perStationArea : 0
         * priority : 0
         * propertyCorp :
         * recommend : 0
         * rentNum : 0
         * services :
         * shortestFlag : 1
         * spaceArea : 0
         * spaceBrand :
         * spaceBusinessArea :
         * spaceCnName : 梦想加-望京
         * spaceDistrict : 朝阳
         * spaceFloor :
         * spaceHeight : 0
         * spaceNamePinyin : bangongmengxiang
         * spacePriceType : 0
         * spaceProfile :
         * spaceProportion : 0
         * spaceType : 0
         * status : 0
         * type : 2
         * ubanPrice : 0
         */

        private List<DatasBean> datas;

        public int getTotals() {
            return totals;
        }

        public void setTotals(int totals) {
            this.totals = totals;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Serializable{
            private String activityForecast;
            private String address;
            private int allRentNum;
            private String artDesc;
            private int businessAreaId;
            private int cityId;
            private int commissionRate;
            private String director;
            private String directorPhone;
            private int distance;
            private int districtId;
            private String equipment;
            private int intentPrice;
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
            private int shortestFlag;
            private int spaceArea;
            private String spaceBrand;
            private String spaceBusinessArea;
            private String spaceCnName;
            private String spaceDistrict;
            private String spaceFloor;
            private int spaceHeight;
            private String spaceNamePinyin;
            private int spacePriceType;
            private String spaceProfile;
            private int spaceProportion;
            private int spaceType;
            private int status;
            private int type;
            private int ubanPrice;

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

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
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

            public int getShortestFlag() {
                return shortestFlag;
            }

            public void setShortestFlag(int shortestFlag) {
                this.shortestFlag = shortestFlag;
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

            public int getSpaceHeight() {
                return spaceHeight;
            }

            public void setSpaceHeight(int spaceHeight) {
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
        }
    }
}
