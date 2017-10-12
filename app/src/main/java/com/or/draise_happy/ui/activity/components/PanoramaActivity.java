package com.or.draise_happy.ui.activity.components;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.or.draise_happy.R;
import com.or.draise_happy.ui.view.LoadingProgress;
import com.or.draise_happy.ui.view.ToastUtil;

import java.util.ArrayList;


/**
 * 全景图
 * Created by dawabos on 2015/7/4.
 * Email dawabo@163.com
 */
public class PanoramaActivity extends PLView {
    public static final String PANORAMA_IMAGE_URL = "panorama_image_url";
    public static final String PANORAMA_IMAGE_DESC = "desc_list";
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ArrayList<String> desc_list = new ArrayList<>();
    private GridView gv_panorama_image;
    private PanoramaAdapter adapter;
    private LoadingProgress loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setGridView();
    }

    private void setGridView() {
        int size = desc_list.size();
        int length = 80;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gv_panorama_image.setLayoutParams(params);
        gv_panorama_image.setColumnWidth(itemWidth);
        gv_panorama_image.setHorizontalSpacing(0); // 间距
        gv_panorama_image.setStretchMode(GridView.NO_STRETCH);
        gv_panorama_image.setNumColumns(size);
    }
    @Override
    protected View onContentViewCreated(View contentView) {
        ViewGroup mainView = (ViewGroup) this.getLayoutInflater().inflate(
                R.layout.activity_panorama, null);
        showLoadingView();
        mainView.addView(contentView, 0);
        imageUrl = getIntent().getStringArrayListExtra(PANORAMA_IMAGE_URL);
        desc_list = getIntent().getStringArrayListExtra(PANORAMA_IMAGE_DESC);
        loadUrl(imageUrl.get(0));
        adapter = new PanoramaAdapter();
        gv_panorama_image = (GridView) mainView
                .findViewById(R.id.gv_panorama_image);
        gv_panorama_image.setAdapter(adapter);

        ImageView iv_panorama_back = (ImageView) mainView
                .findViewById(R.id.iv_panorama_back);
        iv_panorama_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        return super.onContentViewCreated(mainView);
    }



    int mFlag = 0;
    private class PanoramaAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return desc_list.size();
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = View.inflate(PanoramaActivity.this,
                    R.layout.activity_panorama_button, null);
            RadioGroup rdg_panorama_group = (RadioGroup) view.findViewById(R.id.rdg_panorama_group);
            final RadioButton btn_panorama_item = (RadioButton) view.findViewById(R.id.btn_panorama_item);

            btn_panorama_item.setText(desc_list.get(position));
            btn_panorama_item.setId(position);
            if (btn_panorama_item.getId()==mFlag) {
                btn_panorama_item.setBackgroundResource(R.drawable.ic_panorama_checked);
            }else {
                btn_panorama_item.setBackgroundResource(R.drawable.ic_panorama_black);
            }
            rdg_panorama_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    mFlag = checkedId;
                    notifyDataSetChanged();
                }
            });

            btn_panorama_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingView();
                    loadUrl(imageUrl.get(position));
                }
            });
            return view;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    public void loadUrl(String url) {
        RequestQueue mQueuess = Volley.newRequestQueue(this);
        ImageRequest imageRequests = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        hideLoadingView();
                        PLSpherical2Panorama panorama = new PLSpherical2Panorama();
                        if (panorama!=null) {
                            //panorama.getCamera().lookAt(0.0f, 170.0f);
                            panorama.getCamera().setPitchRange(0.0f, 0.0f);
                            //panorama.getCamera().setRollRange(111.0f, 233.0f);
                            panorama.getCamera().setRotationSensitivity(200f);
                            panorama.getCamera().setFovSensitivity(5000.0f);
                            //PanoramaActivity.this.reset();
                            //PanoramaActivity.this.startTransition(new PLTransitionBlend(2.0f), panorama);
                            PanoramaActivity.this.setScrollingEnabled(true);
                            //PanoramaActivity.this.setInertiaEnabled(true);
                            //PanoramaActivity.this.setInertiaInterval(0.9f);
                        }


                        //添加bitmap
                        panorama.setImage(new PLImage(bitmap, false));
                        PanoramaActivity.this.setPanorama(panorama);
                        //回收bitmap
                        if (bitmap != null && !bitmap.isRecycled())
                            bitmap.recycle();
                    }

                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoadingView();
                ToastUtil.makeText(PanoramaActivity.this, "加载失败！");
            }
        });

        mQueuess.add(imageRequests);
    }


    public void showLoadingView(){
        if (loadingProgress==null){
            loadingProgress = new LoadingProgress(this);
        }
        loadingProgress.show();
    }
    public void hideLoadingView() {
        if (loadingProgress!=null&&loadingProgress.isShowing()){
            loadingProgress.dismiss();
        }
    }
}
