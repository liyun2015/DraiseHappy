package com.or.goodlive.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.goodlive.R;
import com.or.goodlive.module.CommentBean;
import com.or.goodlive.module.MessResultsBean;
import com.or.goodlive.util.ImageLoadUtils;

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
        ImageView imageView= (ImageView) helper.getConvertView().findViewById(R.id.message_image);
        ImageLoadUtils.displayHeadIcon(BASE_URL+item.getUser_icon(),imageView);
    }
}
