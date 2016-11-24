package com.uban.rent.module.request;

/**
 * RequestRentOrderList
 * Created by dawabos on 2016/11/21.
 * Email dawabo@163.com
 */

public class RequestRentOrderList {
    private int workDeskTypes;

    private int pageIndex;

    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getWorkDeskTypes() {
        return workDeskTypes;
    }

    public void setWorkDeskTypes(int workDeskTypes) {
        this.workDeskTypes = workDeskTypes;
    }
}
