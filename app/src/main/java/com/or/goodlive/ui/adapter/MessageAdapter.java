package com.or.goodlive.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.goodlive.R;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.module.MessResultsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/18.
 */

public class MessageAdapter extends BaseQuickAdapter<MessResultsBean.RstBean.ListBean,BaseViewHolder> {


    public MessageAdapter(int layoutResId, List<MessResultsBean.RstBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessResultsBean.RstBean.ListBean item) {
        helper.setText(R.id.message_title,item.getTitle())
                .setText(R.id.message_time, item.getPush_date())
                .setText(R.id.message_infor, item.getContent());
    }
}

