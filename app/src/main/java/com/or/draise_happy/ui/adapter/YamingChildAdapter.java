package com.or.draise_happy.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.draise_happy.R;
import com.or.draise_happy.module.CoverDataBean;
import com.or.draise_happy.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class YamingChildAdapter extends BaseQuickAdapter<CoverDataBean.RstBean.ListBean,BaseViewHolder> {



    public YamingChildAdapter(int layoutResId, List<CoverDataBean.RstBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoverDataBean.RstBean.ListBean item) {
        helper.setText(R.id.yaming_news_title,item.getTitle())
        .setText(R.id.yaming_news_source, item.getCategory_name());
        ImageView imageView= (ImageView) helper.getConvertView().findViewById(R.id.yaming_pic_image);
        ImageLoadUtils.displayImageWithLoadingPicture(item.getTitle_pic(),imageView,R.drawable.default_photoalbum_img);
    }
}
