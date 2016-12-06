package com.uban.rent.ui.activity.member;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.VerifyMemberBean;
import com.uban.rent.module.request.RequestVerifyMember;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;
import com.uban.rent.util.TimeUtils;
import com.uban.rent.wxapi.WXPayEntryActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.uban.rent.util.TimeUtils.formatTime;

public class MemberStatusActivity extends BaseActivity {
    public static final int memberType = 1;

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.top_view)
    AppBarLayout topView;
    @Bind(R.id.member_status_call_phone)
    LinearLayout memberStatusCallPhone;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.activity_member_status)
    RelativeLayout activityMemberStatus;
    @Bind(R.id.member_order_number)
    TextView memberOrderNumber;
    @Bind(R.id.member_order_date)
    TextView memberOrderDate;
    @Bind(R.id.member_order_status)
    TextView memberOrderStatus;
    @Bind(R.id.member_status_username)
    TextView memberStatusUsername;
    @Bind(R.id.member_status_phone)
    TextView memberStatusPhone;
    @Bind(R.id.member_date_expiry)
    TextView memberDateExpiry;

    private static final String[] STATUS_CODE = new String[]{"取消", "待支付", "申请成功"};
    @Bind(R.id.payment_submit_btn)
    TextView paymentSubmitBtn;
    private String orderNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_status;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
    }


    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("优办会员");
    }

    private void initData() {

        RequestVerifyMember requestVerifyMember = new RequestVerifyMember();
        requestVerifyMember.setType(memberType);
        ServiceFactory.getProvideHttpService().getVerifyMember(requestVerifyMember)
                .compose(this.<VerifyMemberBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<VerifyMemberBean>io_main())
                .filter(new Func1<VerifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(VerifyMemberBean verifyMemberBean) {
                        return verifyMemberBean != null;
                    }
                })
                .filter(new Func1<VerifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(VerifyMemberBean verifyMemberBean) {
                        return verifyMemberBean.getResults() != null;
                    }
                })
                .filter(new Func1<VerifyMemberBean, Boolean>() {
                    @Override
                    public Boolean call(VerifyMemberBean verifyMemberBean) {
                        return verifyMemberBean.getResults().size() > 0;
                    }
                })
                .subscribe(new Action1<VerifyMemberBean>() {
                    @Override
                    public void call(VerifyMemberBean verifyMemberBean) {
                        VerifyMemberBean.ResultsBean resultsBean = verifyMemberBean.getResults().get(0);
                        initDataView(resultsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initDataView(VerifyMemberBean.ResultsBean resultsBean) {
        orderNum = resultsBean.getMemberNo();
        memberOrderNumber.setText("订单编号" + orderNum);
        String createAt = formatTime(String.valueOf(resultsBean.getStartAt()), "yyyy.MM.dd");
        String endAt = TimeUtils.formatTime(String.valueOf(resultsBean.getEndAt()), "yyyy.MM.dd");
        memberDateExpiry.setText("有效期:" + createAt + "-" + endAt);
        memberStatusPhone.setText(resultsBean.getPhone());
        memberStatusUsername.setText(resultsBean.getName());
        memberOrderStatus.setText(STATUS_CODE[resultsBean.getStatus()]);
        String createAtstr = TimeUtils.formatTime(String.valueOf(resultsBean.getCreateAt()), "yyyy.MM.dd HH:mm:ss");
        memberOrderDate.setText(createAtstr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callPhone() {
        RxPermissions.getInstance(mContext).request(Manifest.permission.CALL_PHONE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            PhoneUtils.call(mContext, Constants.PHONE_NUMBER);
                        } else {
                            ToastUtil.makeText(mContext, "未授权");
                        }
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.payment_submit_btn,R.id.member_status_call_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.member_status_call_phone:
                callPhone();
                break;
            case R.id.payment_submit_btn://立即支付
                goActivity(WXPayEntryActivity.class);
                break;
            default:
                break;
        }
    }
    private void goActivity(Class<?> cls) {
        Intent orderIntent = new Intent();
        orderIntent.setClass(mContext, cls);
        orderIntent.putExtra(WXPayEntryActivity.KEY_ORDER_NUMBER,orderNum);
        orderIntent.putExtra(WXPayEntryActivity.KEY_SOURCE_ACTIVITY,"MemberCreateActivity");
        startActivity(orderIntent);
    }
}
