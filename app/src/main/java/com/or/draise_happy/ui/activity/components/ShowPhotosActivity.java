package com.or.goodlive.ui.activity.components;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.or.goodlive.R;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.ui.view.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowPhotosActivity extends BaseActivity {
    public static final String KEY_URL = "URL";

    @Bind(R.id.toolbar_content_text)
    TextView toolbarContentText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.image_show)
    ImageView imageShow;
    @Bind(R.id.activity_show_photos)
    LinearLayout activityShowPhotos;

    PhotoViewAttacher mAttacher;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_photos;
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
            actionBar.setTitle("");
        }
        toolbarContentText.setText("查看图片");
    }

    private void initData() {
        String url =  getIntent().getStringExtra(KEY_URL);
        showLoadingView();
        RequestQueue mQueuess = Volley.newRequestQueue(this);
        ImageRequest imageRequests = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        hideLoadingView();
                        imageShow.setImageBitmap(bitmap);
                        mAttacher = new PhotoViewAttacher(imageShow);
                        mAttacher.update();
                    }

                }, 0, 0,ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoadingView();
                ToastUtil.makeText(mContext, "加载失败！");
            }
        });
        mQueuess.add(imageRequests);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
}
