package com.or.draise_happy.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.or.draise_happy.module.CoverDataBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CoverAdapter extends BaseQuickAdapter<CoverDataBean.RstBean.HomeactBean,BaseViewHolder> {


    public CoverAdapter(int layoutResId, List<CoverDataBean.RstBean.HomeactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoverDataBean.RstBean.HomeactBean item) {

    }
}
