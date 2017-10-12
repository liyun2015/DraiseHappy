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
import com.or.draise_happy.control.RxSchedulersHelper;
import com.or.draise_happy.module.BaseResultsBean;
import com.or.draise_happy.module.request.RequestLogin;
import com.or.draise_happy.ui.view.ToastUtil;
import com.or.draise_happy.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/18.
 */

public class ModifyPassWordActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_new_pass_word)
    EditText etNewPassWord;
    @Bind(R.id.et_affirm_new_pass_word)
    EditText etAffirmNewPassWord;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.et_old_pass_word)
    EditText etOldPassWord;
    private String newPswAff;
    private String oldPsw;
    private String newPsw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
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
        toolbarContentText.setText("修改密码");
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
        oldPsw = etOldPassWord.getText().toString().trim();
        newPsw = etNewPassWord.getText().toString().trim();
        newPswAff = etAffirmNewPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(oldPsw)) {
            ToastUtil.makeText(mContext, "旧密码不能为空！");
        }  else if (TextUtils.isEmpty(newPsw)) {
            ToastUtil.makeText(this, "新密码不能为空！");
        } else if (TextUtils.isEmpty(newPswAff)||!newPswAff.equals(newPsw)) {
            ToastUtil.makeText(this, "二次新密码输入有误！");
        } else {
            modifyPassWord();
        }
    }

    private void modifyPassWord() {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setPassword(oldPsw);
        requestLogin.setNew_password(newPswAff);
        ServiceFactory.getProvideHttpService().updatePassword(requestLogin)
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
                    public Boolean call(BaseResultsBean loginInBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno())) {
                            ToastUtil.makeText(mContext, loginInBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(loginInBean.getErrno());
                    }
                })
                .map(new Func1<BaseResultsBean, BaseResultsBean.RstBean>() {
                    @Override
                    public BaseResultsBean.RstBean call(BaseResultsBean loginInBean) {
                        return loginInBean.getRst();
                    }
                })
                .subscribe(new Action1<BaseResultsBean.RstBean>() {
                    @Override
                    public void call(BaseResultsBean.RstBean resultsBean) {
                        ToastUtil.makeText(mContext, "修改成功！");
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
