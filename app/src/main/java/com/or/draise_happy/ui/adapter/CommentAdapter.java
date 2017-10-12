package com.or.draise_happy.ui.adapter;

import android.media.ExifInterface;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.draise_happy.R;
import com.or.draise_happy.module.CommentBean;
import com.or.draise_happy.util.ImageLoadUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Wang on 2017/2/26.
 */

public class CommentAdapter extends BaseQuickAdapter<CommentBean.RstBean.ListBean,BaseViewHolder> {

    public static final String BASE_URL = "http://weixin.xiaozhubanjia.com";
    public CommentAdapter(int layoutResId, List<CommentBean.RstBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean.RstBean.ListBean item) {
        helper.setText(R.id.message_title,item.getUser_name())
                .setText(R.id.message_time, item.getUpdate_time())
                .setText(R.id.message_infor, item.getContent());
        String imageUrl = BASE_URL+item.getUser_icon();
        ImageView imageView= (ImageView) helper.getConvertView().findViewById(R.id.message_image);
        ImageLoadUtils.displayHeadIcon(imageUrl,imageView);
    }
    /**
     * 将图片的旋转角度置为0
     * @Title: setPictureDegreeZero
     * @param path
     * @return void
     * @date 2012-12-10 上午10:54:46
     */
    private void setPictureDegreeZero(String path){
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            //修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            //例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
            exifInterface.saveAttributes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }
}
