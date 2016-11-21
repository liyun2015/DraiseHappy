package com.uban.rent.network;


import com.uban.rent.module.HomeDatasBean;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.module.request.RequestCreatOrder;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestHomeData;
import com.uban.rent.module.request.RequestOrderDetailBean;
import com.uban.rent.module.request.RequestPaymentOrder;
import com.uban.rent.module.request.RequestRentOrderList;
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

    //创建订单
    @POST("/mainapi/officespaceSROrderProvider/creatShortRentOrder")
    Observable<RequestCreatShortRentOrderBean> creatShortRentOrder(@Body RequestCreatOrder requestCreatOrder);

    //订单支付后提交
    @POST("http://192.168.0.21:30104/mainapi/officespaceSROrderProvider/paymentShortRentOrder")
    Observable<RequestCreatShortRentOrderBean> paymentShortRentOrder(@Body RequestPaymentOrder requestPaymentOrder);

    //取消订单
    @POST("/mainapi/officespaceSROrderProvider/cancelShortRentOrder")
    Observable<RequestCreatShortRentOrderBean> cancelShortRentOrder(@Body RequestPaymentOrder requestPaymentOrder);

    //订单详情
    @POST("/mainapi/officespaceSROrderProvider/officespaceShortRentOrderInfo")
    Observable<RequestCreatShortRentOrderBean> getOrderDetail(@Body RequestOrderDetailBean requestOrderDetailBean);

    //订单列表
    @POST("/mainapi/officespaceSROrderProvider/shortRentOrder")
    Observable<String> getShortRentOrder(@Body RequestRentOrderList requestRentOrderList);
}
