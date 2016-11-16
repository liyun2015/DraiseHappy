package com.uban.rent.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uban.rent.R;
import com.uban.rent.ui.activity.ShowPhotosActivity;
import com.uban.rent.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hejunlin on 2016/8/25.
 */
public class BannerPicAdapter extends PagerAdapter {

    private Context mContext;

    private List<String> images = new ArrayList<>();
    public BannerPicAdapter(Context mContext) {
        this.mContext = mContext;
        images.add("http://upload.chinapet.com/forum/201410/29/145243zrrcz5o6nt3r7cl2.jpg");
        images.add("http://file.cbda.cn/uploadfile/2015/0330/20150330041852447.jpg");
        images.add("http://a4.att.hudong.com/38/47/19300001391844134804474917734_950.png");
        images.add("http://img6.faloo.com/Picture/0x0/0/183/183463.jpg");
        images.add("http://img3.duitang.com/uploads/item/201507/30/20150730163111_YZT5S.thumb.700_0.jpeg");

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_banner);
        ImageLoadUtils.displayImage(images.get(position),imageView);

        Bitmap newimage = ImageLoader.getInstance().loadImageSync(images.get(position));
        ImageView imageView2 = new ImageView(view.getContext());
        imageView2.setImageBitmap(newimage);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ShowPhotosActivity.class);
                intent.putExtra(ShowPhotosActivity.KEY_URL,images.get(position));
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
