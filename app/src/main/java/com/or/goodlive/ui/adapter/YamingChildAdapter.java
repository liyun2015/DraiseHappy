package com.or.goodlive.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.goodlive.module.CoverDataBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class YamingChildAdapter extends BaseQuickAdapter<CoverDataBean.RstBean.HomeactBean,BaseViewHolder> {


    public YamingChildAdapter(int layoutResId, List<CoverDataBean.RstBean.HomeactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoverDataBean.RstBean.HomeactBean item) {

    }
}
