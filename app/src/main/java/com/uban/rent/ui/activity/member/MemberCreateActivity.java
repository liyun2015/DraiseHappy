package com.uban.rent.ui.activity.member;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.R;
import com.uban.rent.api.config.ServiceFactory;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.control.RxSchedulersHelper;
import com.uban.rent.module.BaseResultsBean;
import com.uban.rent.module.request.RequestApplyMember;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;
import com.uban.rent.util.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


public class MemberCreateActivity extends BaseActivity {
    public static final int memberType = 1;
    public static final int memberStatus = 1;

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.top_view)
    AppBarLayout topView;
    @Bind(R.id.create_member_call_phone)
    LinearLayout createMemberCallPhone;
    @Bind(R.id.create_member_total)
    TextView createMemberTotal;
    @Bind(R.id.create_member_order)
    TextView createMemberOrder;
    @Bind(R.id.bottom_view)
    LinearLayout bottomView;
    @Bind(R.id.bottom_line)
    View bottomLine;
    @Bind(R.id.create_member_name)
    AppCompatEditText createMemberName;
    @Bind(R.id.create_member_phone)
    TextView createMemberPhone;
    @Bind(R.id.create_member_price)
    TextView createMemberPrice;
    @Bind(R.id.create_member_date)
    TextView createMemberDate;
    @Bind(R.id.create_member_special_price)
    TextView createMemberSpecialPrice;
    @Bind(R.id.create_member_expiry_date)
    TextView createMemberExpiryDate;
    @Bind(R.id.create_member_service_provision)
    TextView createMemberServiceProvision;
    @Bind(R.id.activity_member_create)
    RelativeLayout activityMemberCreate;

    private String mCreatePhone;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_create;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mCreatePhone = (String) SPUtils.get(mContext,Constants.PHONE,"");
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
        createMemberPhone.setText(mCreatePhone);

        SpannableString createMemberPriceSpannableString = new SpannableString("500");
        StrikethroughSpan createMemberPriceStrikethroughSpan = new StrikethroughSpan();
        createMemberPriceSpannableString.setSpan(createMemberPriceStrikethroughSpan, 0, createMemberPriceSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        createMemberPrice.setText(createMemberPriceSpannableString);

        SpannableString createMemberDateSpannableString = new SpannableString("元/月");
        StrikethroughSpan createMemberDateStrikethroughSpan = new StrikethroughSpan();
        createMemberDateSpannableString.setSpan(createMemberDateStrikethroughSpan, 0, createMemberDateSpannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        createMemberDate.setText(createMemberDateSpannableString);
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

    @OnClick({R.id.create_member_call_phone, R.id.create_member_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_member_call_phone:
                StatService.onEvent(mContext, "MemberApply_ZiXunPhoneEvent", "pass", 1);
                callPhone();
                break;
            case R.id.create_member_order:
                String name = createMemberName.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ToastUtil.makeText(mContext,"请输入姓名");
                }else {
                    StatService.onEvent(mContext, "MemberApply_CommitOrderEvent", "pass", 1);
                    initData(name,mCreatePhone);
                }
                break;
        }
    }

   private void initData(String name,String phone){
       RequestApplyMember requestApplyMember = new RequestApplyMember();
       requestApplyMember.setPhone(phone);
       requestApplyMember.setName(name);
       requestApplyMember.setStatus(memberStatus);
       requestApplyMember.setType(memberType);
       ServiceFactory.getProvideHttpService().getApplyMember(requestApplyMember)
               .compose(this.<BaseResultsBean>bindToLifecycle())
               .compose(RxSchedulersHelper.<BaseResultsBean>io_main())
               .doOnSubscribe(new Action0() {
                   @Override
                   public void call() {
                       showLoadingView();
                   }
               })
               .filter(new Func1<BaseResultsBean, Boolean>() {
                   @Override
                   public Boolean call(BaseResultsBean baseResultsBean) {
                       return baseResultsBean.getStatusCode()==Constants.STATUS_CODE_SUCCESS;
                   }
               })
               .subscribe(new Action1<BaseResultsBean>() {
                   @Override
                   public void call(BaseResultsBean baseResultsBean) {
                       if (baseResultsBean.getStatusCode()==Constants.STATUS_CODE_SUCCESS){
                           SPUtils.put(mContext,Constants.USER_MEMBER,String.valueOf(baseResultsBean.getStatusCode()));// 0 已申请会员
                       }
                       finish();
                   }
               }, new Action1<Throwable>() {
                   @Override
                   public void call(Throwable throwable) {
                       hideLoadingView();
                       ToastUtil.makeText(mContext, "申请会员失败");
                   }
               }, new Action0() {
                   @Override
                   public void call() {
                       hideLoadingView();
                   }
               });
   }
}
