package com.or.draise_happy.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.draise_happy.R;
import com.or.draise_happy.module.MessResultsBean;
import com.or.draise_happy.util.SPUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */

public class MessageAdapter extends BaseQuickAdapter<MessResultsBean.RstBean.ListBean,BaseViewHolder> {
    private Context mContext;

    public MessageAdapter(Context mContext,int layoutResId, List<MessResultsBean.RstBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = mContext;
    }


    @Override
    protected void convert(BaseViewHolder helper, MessResultsBean.RstBean.ListBean item) {
        helper.setText(R.id.message_title,item.getTitle())
                .setText(R.id.message_time, item.getPush_date())
                .setText(R.id.message_infor, item.getContent());
        ImageView imageView= (ImageView) helper.getConvertView().findViewById(R.id.message_remend_icon);
        String hadLook = String.valueOf(item.getNews_id());
        String oldLook = (String) SPUtils.get(mContext, hadLook,"");
        if(oldLook.equals(hadLook)){
            imageView.setVisibility(View.GONE);
        }else{
            imageView.setVisibility(View.VISIBLE);
        }
    }
}

