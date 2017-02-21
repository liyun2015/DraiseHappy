package com.or.goodlive.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.goodlive.R;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.util.ImageLoadUtils;

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
        helper.setText(R.id.yaming_news_title,item.getTitle()+item.getSub());
        ImageView imageView= (ImageView) helper.getConvertView().findViewById(R.id.yaming_pic_image);
        ImageLoadUtils.displayImageWithLoadingPicture(item.getTitle_pic(),imageView,R.drawable.sample_footer_loading);
    }
}
