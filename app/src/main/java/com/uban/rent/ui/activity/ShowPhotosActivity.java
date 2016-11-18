package com.uban.rent.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uban.rent.R;
import com.uban.rent.base.BaseActivity;

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
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);
        imageShow.setImageBitmap(bitmap);
        mAttacher = new PhotoViewAttacher(imageShow);
        mAttacher.update();
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
