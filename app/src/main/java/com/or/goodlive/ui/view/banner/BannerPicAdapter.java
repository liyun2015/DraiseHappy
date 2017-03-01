package com.or.goodlive.ui.view.banner;

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
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alipay.share.sdk.Constant;
import com.or.goodlive.R;
import com.or.goodlive.control.RxBus;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.activity.mycenter.ModifyUserInforActivity;
import com.or.goodlive.ui.activity.other.WebViewActivity;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.ImageLoadUtils;
import com.or.goodlive.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import static com.or.goodlive.ui.activity.mycenter.MyCenterActivity.HEADERURL;
import static com.or.goodlive.ui.activity.mycenter.MyCenterActivity.USERNAME;
import static com.or.goodlive.util.Constants.WEB_VIEW_HOSTURL;

/**
 * Created by Administrator on 2017/2/20.
 */

public class BannerPicAdapter extends PagerAdapter {

    private Context mContext;
    private List<CoverDataBean.RstBean.HomeactBean> images ;
    private String category;
    private String title="益直播";

    public BannerPicAdapter(Context mContext) {
        this.mContext = mContext;
        images = new ArrayList<>();
    }

    public void setData(List<CoverDataBean.RstBean.HomeactBean> images,String category){
        this.images = images;
        this.category = category;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner_images, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_banner);
        ImageLoadUtils.displayImage(images.get(position).getPhoto(),imageView);

        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newimage = getRoundCornerImage(image,50);
        ImageView imageView2 = new ImageView(view.getContext());
        imageView2.setImageBitmap(newimage);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Constants.WEB_VIEW_HOSTURL+"type="+category+"&id="+images.get(position).getId();
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_VIEW_URL, url);
                intent.putExtra(WebViewActivity.WEB_VIEW_DESC,title);
                intent.putExtra(WebViewActivity.WEB_VIEW_TABLE_NAME, category);
//                intent.putExtra(WebViewActivity.WEB_VIEW_TITLE_NAME, images.get(position).getTitle());
//                intent.putExtra(WebViewActivity.WEB_VIEW_CONTENT_NAME, images.get(position).getContent());
                intent.putExtra(WebViewActivity.WEB_VIEW_DESC,images.get(position).getId());
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

