package com.uban.rent.ui.activity.member;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;
import com.uban.rent.ui.view.ToastUtil;
import com.uban.rent.util.Constants;
import com.uban.rent.util.PhoneUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;



public class MemberCreateActivity extends BaseActivity {


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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_create;
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
                callPhone();
                break;
            case R.id.create_member_order:
                startActivity(new Intent(mContext, MemberStatusActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
