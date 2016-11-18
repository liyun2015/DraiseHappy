package com.uban.rent.network;


import com.uban.rent.module.HomeDatasBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.module.request.RequestHomeData;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.module.request.RequestWorkplaceDetail;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * ApiClient
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public interface ApiClient {
    //首页数据
    @POST("/mainapi/officespaceBasicProvider/findShortRentOfficesapces")
    Observable<HomeDatasBean> getFindShortRentOfficeSpaces(@Body RequestHomeData requestHomeData);

    //空间详情
    @POST("/mainapi/officespaceBasicProvider/officeSpaceInfo")
    Observable<SpaceDetailBean> getOfficeSpaceInfo(@Body RequestSpaceDetail requestSpaceDetail);

    //工位详情
    @POST("/mainapi/officespaceDeskBasicProvider/officespaceWorkdeskInfo")
    Observable<WorkplaceDetailBean> getOfficespaceWorkdeskInfo(@Body RequestWorkplaceDetail requestWorkplaceDetail);
}
