package com.uban.rent.module;

import java.util.List;

/**
 * WorkplaceDetailBean工位详情
 * Created by dawabos on 2016/11/18.
 * Email dawabo@163.com
 */

public class WorkplaceDetailBean {


    /**
     * statusCode : 0
     * msg : 成功
     * results : {"cityId":12,"dayPrice":1,"workDeskPrice":560,"workDeskType":2,"spaceCnName":"SOHO 3Q望京店","workTime":"","payment":0,"rentDesc":"租金包括物业、水电、网络、咖啡茶水","spaceId":27,"workDeskDesc":"","isDeleted":1,"workHoursBegin":"","workDeskPriceType":2,"officespaceWorkdeskinfoId":9,"commissionRate":0,"rentTotalArea":4550,"hourPrice":10,"intentPrice":0,"serviceList":[{"category":3,"cityId":12,"fieldImg":"workdeskinfo_equip_services3460ffc6cb3d40128e5f60094ba3b3e6.png","officespaceEquipmentserviceinfoId":24,"fieldName":"测试1"},{"category":3,"cityId":12,"fieldImg":"workdeskinfo_equip_services11e5f52434024c2d976add59fa2b1aa6.png","officespaceEquipmentserviceinfoId":25,"fieldName":"测试2"}],"rentNum":69,"address":"北京市朝阳区望京SOHOT3","workDeskNo":"","workDeskUbanPrice":0,"equipServices":"24,25","buyDesc":"","workHoursEnd":"","picList":[{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_b82ca4e391984330bbfa921fe6124d8b.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":10,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_009fad040ac5421cadb9ba44a4a0812c.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":11,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_409d45d68cc44d5ba278d48d4187823a.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":12,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_32b8c450049f49bb8d49247065a2f885.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":13,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_8145aee9ab5c42d78c3f3060f3d8020f.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":14,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_2bb11a8732a4493b8cecff2dadd17586.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":15,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_c9a32da24d6a49448226952d35230daa.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":16,"imgTitle":"望京SOHO3Q","imgDesc":""}],"handler":{}}
     */

    private int statusCode;
    private String msg;
    /**
     * cityId : 12
     * dayPrice : 1
     * workDeskPrice : 560
     * workDeskType : 2
     * spaceCnName : SOHO 3Q望京店
     * workTime :
     * payment : 0
     * rentDesc : 租金包括物业、水电、网络、咖啡茶水
     * spaceId : 27
     * workDeskDesc :
     * isDeleted : 1
     * workHoursBegin :
     * workDeskPriceType : 2
     * officespaceWorkdeskinfoId : 9
     * commissionRate : 0
     * rentTotalArea : 4550
     * hourPrice : 10
     * intentPrice : 0
     * serviceList : [{"category":3,"cityId":12,"fieldImg":"workdeskinfo_equip_services3460ffc6cb3d40128e5f60094ba3b3e6.png","officespaceEquipmentserviceinfoId":24,"fieldName":"测试1"},{"category":3,"cityId":12,"fieldImg":"workdeskinfo_equip_services11e5f52434024c2d976add59fa2b1aa6.png","officespaceEquipmentserviceinfoId":25,"fieldName":"测试2"}]
     * rentNum : 69
     * address : 北京市朝阳区望京SOHOT3
     * workDeskNo :
     * workDeskUbanPrice : 0
     * equipServices : 24,25
     * buyDesc :
     * workHoursEnd :
     * picList : [{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_b82ca4e391984330bbfa921fe6124d8b.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":10,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_009fad040ac5421cadb9ba44a4a0812c.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":11,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_409d45d68cc44d5ba278d48d4187823a.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":12,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_32b8c450049f49bb8d49247065a2f885.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":13,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_8145aee9ab5c42d78c3f3060f3d8020f.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":14,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_2bb11a8732a4493b8cecff2dadd17586.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":15,"imgTitle":"望京SOHO3Q","imgDesc":""},{"imgFormat":".jpg","imgType":1,"cityId":12,"imgPath":"bjwjsoho_duli_c9a32da24d6a49448226952d35230daa.jpg","spaceId":27,"workDeskId":9,"officespaceWorkdeskimgsId":16,"imgTitle":"望京SOHO3Q","imgDesc":""}]
     * handler : {}
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
        private int cityId;
        private int dayPrice;
        private int workDeskPrice;
        private int workDeskType;
        private String spaceCnName;
        private String workTime;
        private int payment;
        private String rentDesc;
        private int spaceId;
        private String workDeskDesc;
        private int isDeleted;
        private String workHoursBegin;
        private int workDeskPriceType;
        private int officespaceWorkdeskinfoId;
        private double commissionRate;
        private int rentTotalArea;
        private int hourPrice;
        private int intentPrice;
        private int rentNum;
        private String address;
        private String workDeskNo;
        private int workDeskUbanPrice;
        private String equipServices;
        private String buyDesc;
        private String workHoursEnd;
        private HandlerBean handler;
        /**
         * category : 3
         * cityId : 12
         * fieldImg : workdeskinfo_equip_services3460ffc6cb3d40128e5f60094ba3b3e6.png
         * officespaceEquipmentserviceinfoId : 24
         * fieldName : 测试1
         */

        private List<ServiceListBean> serviceList;
        /**
         * imgFormat : .jpg
         * imgType : 1
         * cityId : 12
         * imgPath : bjwjsoho_duli_b82ca4e391984330bbfa921fe6124d8b.jpg
         * spaceId : 27
         * workDeskId : 9
         * officespaceWorkdeskimgsId : 10
         * imgTitle : 望京SOHO3Q
         * imgDesc :
         */

        private List<PicListBean> picList;

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getDayPrice() {
            return dayPrice;
        }

        public void setDayPrice(int dayPrice) {
            this.dayPrice = dayPrice;
        }

        public int getWorkDeskPrice() {
            return workDeskPrice;
        }

        public void setWorkDeskPrice(int workDeskPrice) {
            this.workDeskPrice = workDeskPrice;
        }

        public int getWorkDeskType() {
            return workDeskType;
        }

        public void setWorkDeskType(int workDeskType) {
            this.workDeskType = workDeskType;
        }

        public String getSpaceCnName() {
            return spaceCnName;
        }

        public void setSpaceCnName(String spaceCnName) {
            this.spaceCnName = spaceCnName;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
            this.payment = payment;
        }

        public String getRentDesc() {
            return rentDesc;
        }

        public void setRentDesc(String rentDesc) {
            this.rentDesc = rentDesc;
        }

        public int getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(int spaceId) {
            this.spaceId = spaceId;
        }

        public String getWorkDeskDesc() {
            return workDeskDesc;
        }

        public void setWorkDeskDesc(String workDeskDesc) {
            this.workDeskDesc = workDeskDesc;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getWorkHoursBegin() {
            return workHoursBegin;
        }

        public void setWorkHoursBegin(String workHoursBegin) {
            this.workHoursBegin = workHoursBegin;
        }

        public int getWorkDeskPriceType() {
            return workDeskPriceType;
        }

        public void setWorkDeskPriceType(int workDeskPriceType) {
            this.workDeskPriceType = workDeskPriceType;
        }

        public int getOfficespaceWorkdeskinfoId() {
            return officespaceWorkdeskinfoId;
        }

        public void setOfficespaceWorkdeskinfoId(int officespaceWorkdeskinfoId) {
            this.officespaceWorkdeskinfoId = officespaceWorkdeskinfoId;
        }

        public double getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(double commissionRate) {
            this.commissionRate = commissionRate;
        }

        public int getRentTotalArea() {
            return rentTotalArea;
        }

        public void setRentTotalArea(int rentTotalArea) {
            this.rentTotalArea = rentTotalArea;
        }

        public int getHourPrice() {
            return hourPrice;
        }

        public void setHourPrice(int hourPrice) {
            this.hourPrice = hourPrice;
        }

        public int getIntentPrice() {
            return intentPrice;
        }

        public void setIntentPrice(int intentPrice) {
            this.intentPrice = intentPrice;
        }

        public int getRentNum() {
            return rentNum;
        }

        public void setRentNum(int rentNum) {
            this.rentNum = rentNum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWorkDeskNo() {
            return workDeskNo;
        }

        public void setWorkDeskNo(String workDeskNo) {
            this.workDeskNo = workDeskNo;
        }

        public int getWorkDeskUbanPrice() {
            return workDeskUbanPrice;
        }

        public void setWorkDeskUbanPrice(int workDeskUbanPrice) {
            this.workDeskUbanPrice = workDeskUbanPrice;
        }

        public String getEquipServices() {
            return equipServices;
        }

        public void setEquipServices(String equipServices) {
            this.equipServices = equipServices;
        }

        public String getBuyDesc() {
            return buyDesc;
        }

        public void setBuyDesc(String buyDesc) {
            this.buyDesc = buyDesc;
        }

        public String getWorkHoursEnd() {
            return workHoursEnd;
        }

        public void setWorkHoursEnd(String workHoursEnd) {
            this.workHoursEnd = workHoursEnd;
        }

        public HandlerBean getHandler() {
            return handler;
        }

        public void setHandler(HandlerBean handler) {
            this.handler = handler;
        }

        public List<ServiceListBean> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<ServiceListBean> serviceList) {
            this.serviceList = serviceList;
        }

        public List<PicListBean> getPicList() {
            return picList;
        }

        public void setPicList(List<PicListBean> picList) {
            this.picList = picList;
        }

        public static class HandlerBean {
        }

        public static class ServiceListBean {
            private int category;
            private int cityId;
            private String fieldImg;
            private int officespaceEquipmentserviceinfoId;
            private String fieldName;

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getFieldImg() {
                return fieldImg;
            }

            public void setFieldImg(String fieldImg) {
                this.fieldImg = fieldImg;
            }

            public int getOfficespaceEquipmentserviceinfoId() {
                return officespaceEquipmentserviceinfoId;
            }

            public void setOfficespaceEquipmentserviceinfoId(int officespaceEquipmentserviceinfoId) {
                this.officespaceEquipmentserviceinfoId = officespaceEquipmentserviceinfoId;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }
        }

        public static class PicListBean {
            private String imgFormat;
            private int imgType;
            private int cityId;
            private String imgPath;
            private int spaceId;
            private int workDeskId;
            private int officespaceWorkdeskimgsId;
            private String imgTitle;
            private String imgDesc;

            public String getImgFormat() {
                return imgFormat;
            }

            public void setImgFormat(String imgFormat) {
                this.imgFormat = imgFormat;
            }

            public int getImgType() {
                return imgType;
            }

            public void setImgType(int imgType) {
                this.imgType = imgType;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getImgPath() {
                return imgPath;
            }

            public void setImgPath(String imgPath) {
                this.imgPath = imgPath;
            }

            public int getSpaceId() {
                return spaceId;
            }

            public void setSpaceId(int spaceId) {
                this.spaceId = spaceId;
            }

            public int getWorkDeskId() {
                return workDeskId;
            }

            public void setWorkDeskId(int workDeskId) {
                this.workDeskId = workDeskId;
            }

            public int getOfficespaceWorkdeskimgsId() {
                return officespaceWorkdeskimgsId;
            }

            public void setOfficespaceWorkdeskimgsId(int officespaceWorkdeskimgsId) {
                this.officespaceWorkdeskimgsId = officespaceWorkdeskimgsId;
            }

            public String getImgTitle() {
                return imgTitle;
            }

            public void setImgTitle(String imgTitle) {
                this.imgTitle = imgTitle;
            }

            public String getImgDesc() {
                return imgDesc;
            }

            public void setImgDesc(String imgDesc) {
                this.imgDesc = imgDesc;
            }
        }
    }
}
