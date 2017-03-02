package com.or.goodlive.ui.activity.mycenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.control.Events;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.BaseResultsBean;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.FileUtils;
import com.or.goodlive.util.ImageLoadUtils;
import com.or.goodlive.util.SPUtils;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/18.
 */

public class ModifyUserInforActivity extends BaseActivity {
    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.user_header_image)
    ImageView userHeaderImage;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_name_layout)
    LinearLayout userNameLayout;
    private List<String> pathList;
    private static final int REQUEST_CODE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_user_infor;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        regiserEvent();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbarContentText.setText("修改资料");
        userName.setText(getIntent().getStringExtra(MyCenterActivity.USERNAME));
        ImageLoadUtils.displayHeadIcon(getIntent().getStringExtra(MyCenterActivity.HEADERURL), userHeaderImage);
    }
    private void regiserEvent() {
        RxBus.with(this)
                .setEvent(Events.EVENTS_UPDATA_HEADER)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        String name =(String) SPUtils.get(ModifyUserInforActivity.this,Constants.USER_NAME,"");
                        userName.setText(name);
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("loginEventError",throwable.getMessage());
                    }
                })
                .create();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void Multiselect() {
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                .multiSelect(true)
                .btnText("确定")
                .btnTextColor(Color.WHITE)
                // 是否记住上次选中记录
                .rememberSelected(false)
                // 使用沉浸式状态栏
                .title("相册")
                .titleColor(Color.WHITE)
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                .build();

        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoadUtils.displayImage("file://" + path, imageView);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            uploadHead();
        }
    }

    private File file = null;

    private void uploadHead() {
        file = FileUtils.getFileByPath(pathList.get(0));
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("avatar", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        RequestBody requestBody = builder.build();
        ServiceFactory.getProvideHttpService().uploadHead(requestBody)
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
                        SPUtils.put(mContext, Constants.AVATAR_URL, Constants.AVATAR_URL_BASE + resultsBean.getPic());
                        ToastUtil.makeText(mContext, "修改成功！");
                        ImageLoadUtils.displayHeadIcon("file://" + pathList.get(0), userHeaderImage);
                        RxBus.getInstance().send(Events.EVENTS_UPDATA_HEADER, new Object());
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

    @OnClick({R.id.user_header_image, R.id.user_name_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_header_image:
                Multiselect();
                break;
            case R.id.user_name_layout:
                Intent intent = new Intent();
                intent.setClass(ModifyUserInforActivity.this, ModifyNameActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }
}
