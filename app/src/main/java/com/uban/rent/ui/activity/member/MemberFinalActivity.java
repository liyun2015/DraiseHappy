package com.uban.rent.ui.activity.member;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
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
import com.uban.rent.util.QRCodeUtil;
import com.uban.rent.util.SPUtils;
import com.uban.rent.util.TimeUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.uban.rent.util.TimeUtils.formatTime;

public class MemberFinalActivity extends BaseActivity {

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.top_view)
    AppBarLayout topView;
    @Bind(R.id.member_final_call_phone)
    LinearLayout memberFinalCallPhone;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.activity_member_final)
    RelativeLayout activityMemberFinal;
    @Bind(R.id.tv_expiry_date)
    TextView tvExpiryDate;
    @Bind(R.id.tv_order_number)
    TextView tvOrderNumber;
    @Bind(R.id.qr_code_image)
    ImageView qrCodeImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_final;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
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
        memberStatus();
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


    @OnClick(R.id.member_final_call_phone)
    public void onClick() {
        StatService.onEvent(mContext, "MemberInfo_ZiXunPhoneEvent", "pass", 1);
        callPhone();
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

    private void memberStatus() {
        RequestVerifyMember requestVerifyMember = new RequestVerifyMember();
        requestVerifyMember.setType(1);
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
                        SPUtils.put(mContext, Constants.USER_MEMBER, verifyMemberBean.getStatusCode());
                        return verifyMemberBean.getStatusCode()==Constants.STATUS_CODE_SUCCESS;
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
                        String createAt = formatTime(String.valueOf(resultsBean.getStartAt()), "yyyy.MM.dd");
                        String endAt = TimeUtils.formatTime(String.valueOf(resultsBean.getEndAt()), "yyyy.MM.dd");
                        tvExpiryDate.setText("有效期:" + createAt + "-" + endAt);
                        tvOrderNumber.setText(resultsBean.getMemberNo());
                        String url = String.format(Constants.MEMBER_EQUIPMENT_QRCODE,resultsBean.getOfficespaceMemberId());
                        Bitmap bitmap = QRCodeUtil.createQRCodeWithLogo(url, 1000,
                                BitmapFactory.decodeResource(getResources(),R.drawable.ic_qr_code_logo));
                        qrCodeImage.setImageBitmap(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"会员信息页");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"会员信息页");
    }
}
