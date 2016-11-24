package com.uban.rent.api;


import com.uban.rent.module.BaseResultsBean;
import com.uban.rent.module.HomeDatasBean;
import com.uban.rent.module.LoginInBean;
import com.uban.rent.module.OrderListBean;
import com.uban.rent.module.SearchKeyWord;
import com.uban.rent.module.SpaceDetailBean;
import com.uban.rent.module.VerifyMemberBean;
import com.uban.rent.module.WXPayProviderBean;
import com.uban.rent.module.WorkplaceDetailBean;
import com.uban.rent.module.request.RequestApplyMember;
import com.uban.rent.module.request.RequestCreatOrder;
import com.uban.rent.module.request.RequestCreatShortRentOrderBean;
import com.uban.rent.module.request.RequestHomeData;
import com.uban.rent.module.request.RequestLogin;
import com.uban.rent.module.request.RequestOrderDetailBean;
import com.uban.rent.module.request.RequestPaymentOrder;
import com.uban.rent.module.request.RequestRentOrderList;
import com.uban.rent.module.request.RequestSearchKeyWord;
import com.uban.rent.module.request.RequestSendValid;
import com.uban.rent.module.request.RequestSpaceDetail;
import com.uban.rent.module.request.RequestVerifyMember;
import com.uban.rent.module.request.RequestWorkplaceDetail;
import com.uban.rent.module.request.UnifieOrderBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * ApiClient
 * Created by dawabos on 2016/10/25.
 * Email dawabo@163.com
 */

public interface ApiClient {
    //登录
    @POST("/mainapi/userProvider/loginNew")
    Observable<LoginInBean> getLogin(@Body RequestLogin requestLogin);

    //发送验证码
    @POST("/mainapi/userProvider/sendValidSms")
    Observable<BaseResultsBean> getSendValidSms(@Body RequestSendValid requestSendValid);

    //首页数据
    @POST("/mainapi/dzofficespaceBasicProvider/findShortRentOfficesapces")
    Observable<HomeDatasBean> getFindShortRentOfficeSpaces(@Body RequestHomeData requestHomeData);

    //空间详情
    @POST("/mainapi/dzofficespaceBasicProvider/officeSpaceInfo")
    Observable<SpaceDetailBean> getOfficeSpaceInfo(@Body RequestSpaceDetail requestSpaceDetail);

    //工位详情
    @POST("/mainapi/dzofficespaceWorkdeskProvider/officespaceWorkdeskInfo")
    Observable<WorkplaceDetailBean> getOfficespaceWorkdeskInfo(@Body RequestWorkplaceDetail requestWorkplaceDetail);

    //创建订单
    @POST("/mainapi/dzofficespaceSROrderProvider/creatShortRentOrder")
    Observable<RequestCreatShortRentOrderBean> creatShortRentOrder(@Body RequestCreatOrder requestCreatOrder);

    //订单支付后提交
    @POST("/mainapi/dzofficespaceSROrderProvider/paymentShortRentOrder")
    Observable<RequestCreatShortRentOrderBean> paymentShortRentOrder(@Body RequestPaymentOrder requestPaymentOrder);

    //取消订单
    @POST("/mainapi/dzofficespaceSROrderProvider/cancelShortRentOrder")
    Observable<RequestCreatShortRentOrderBean> cancelShortRentOrder(@Body RequestPaymentOrder requestPaymentOrder);

    //订单详情
    @POST("/mainapi/dzofficespaceSROrderProvider/officespaceShortRentOrderInfo")
    Observable<RequestCreatShortRentOrderBean> getOrderDetail(@Body RequestOrderDetailBean requestOrderDetailBean);

    //订单列表
    @POST("/mainapi/dzofficespaceSROrderProvider/shortRentOrder")
    Observable<OrderListBean> getShortRentOrder(@Body RequestRentOrderList requestRentOrderList);

    //申请会员
    @POST("/mainapi/officespaceMemberProvider/applyMember")
    Observable<BaseResultsBean> getApplyMember(@Body RequestApplyMember requestApplyMember);

    //验证会员
    @POST("/mainapi/officespaceMemberProvider/verifyMember")
    Observable<VerifyMemberBean> getVerifyMember(@Body RequestVerifyMember requestVerifyMember);

    //获取联想词
    @POST("/mainapi/dzofficespaceBasicProvider/searchKeyWords")
    Observable<SearchKeyWord> getSearchKeyWords(@Body RequestSearchKeyWord requestSearchKeyWord);

    //微信支付接口
    @POST("/mainapi/wXPayProvider/unifiedorder")
    Observable<WXPayProviderBean> getUnifiedorder(@Body UnifieOrderBean requestUnifieDorder);
}
