package com.or.draise_happy.ui.activity.mycenter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.or.draise_happy.R;
import com.or.draise_happy.api.config.ServiceFactory;
import com.or.draise_happy.base.BaseActivity;
import com.or.draise_happy.control.Events;
import com.or.draise_happy.control.RxBus;
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.LoginInBean;
import com.or.draise_happy.module.request.RequestLogin;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.util.Constants;
import com.or.draise_happy.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2017/3/2.
 */

public class ModifyNameActivity extends BaseActivity {

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_new_name)
    EditText etNewName;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private String newName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }
    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("修改昵称");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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

    @OnClick(R.id.btn_submit)
    public void onClick() {
        newName = etNewName.getText().toString().trim();
        if (TextUtils.isEmpty(newName)) {
            ToastUtil.makeText(mContext, "昵称不能为空！");
        } else {
            modifyName();
        }
    }

    private void modifyName() {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setName(newName);
        ServiceFactory.getProvideHttpService().updateName(requestLogin)
                .compose(this.<LoginInBean>bindToLifecycle())
                .compose(RxSchedulersHelper.<LoginInBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<LoginInBean, Boolean>() {
                    @Override
                    public Boolean call(LoginInBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<LoginInBean, LoginInBean.RstBean>() {
                    @Override
                    public LoginInBean.RstBean call(LoginInBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<LoginInBean.RstBean>() {
                    @Override
                    public void call(LoginInBean.RstBean resultsBean) {
                        SPUtils.put(mContext, Constants.USER_NAME, newName);
                        ToastUtil.makeText(mContext, "修改成功！");
                        RxBus.getInstance().send(Events.EVENTS_UPDATA_HEADER, new Object());
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext, "修改失败！");
                        hideLoadingView();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }
}
