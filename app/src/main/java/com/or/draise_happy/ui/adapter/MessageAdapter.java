package com.or.goodlive.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.Text;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.goodlive.R;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.module.MessResultsBean;
import com.or.goodlive.util.Constants;
import com.or.goodlive.util.SPUtils;

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

