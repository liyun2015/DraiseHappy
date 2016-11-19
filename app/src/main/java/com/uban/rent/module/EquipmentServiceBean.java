package com.uban.rent.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * EquipmentServiceBean 设备服务
 * Created by dawabos on 2016/11/19.
 * Email dawabo@163.com
 */

public class EquipmentServiceBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.category);
        dest.writeInt(this.cityId);
        dest.writeString(this.fieldImg);
        dest.writeInt(this.officespaceEquipmentserviceinfoId);
        dest.writeString(this.fieldName);
    }

    public EquipmentServiceBean() {
    }

    protected EquipmentServiceBean(Parcel in) {
        this.category = in.readInt();
        this.cityId = in.readInt();
        this.fieldImg = in.readString();
        this.officespaceEquipmentserviceinfoId = in.readInt();
        this.fieldName = in.readString();
    }

    public static final Parcelable.Creator<EquipmentServiceBean> CREATOR = new Parcelable.Creator<EquipmentServiceBean>() {
        @Override
        public EquipmentServiceBean createFromParcel(Parcel source) {
            return new EquipmentServiceBean(source);
        }

        @Override
        public EquipmentServiceBean[] newArray(int size) {
            return new EquipmentServiceBean[size];
        }
    };
}
