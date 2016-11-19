package com.uban.rent.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.uban.rent.R;
import com.uban.rent.ui.activity.ShowPhotosActivity;
import com.uban.rent.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * BannerPicAdapter
 * Created by dawabos on 2016/11/18.
 * Email dawabo@163.com
 */
public class BannerPicAdapter extends PagerAdapter {

    private Context mContext;

    private List<String> images ;
    public BannerPicAdapter(Context mContext) {
        this.mContext = mContext;
        images = new ArrayList<>();
    }

    public void setData(List<String> images){
        this.images = images;
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

        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newimage = getRoundCornerImage(image,50);
        ImageView imageView2 = new ImageView(view.getContext());
        imageView2.setImageBitmap(newimage);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (images==null||images.size()<=0){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext, ShowPhotosActivity.class);
                intent.putExtra(ShowPhotosActivity.KEY_URL,images.get(position));
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    public Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);
        return roundConcerImage;
    }
}
