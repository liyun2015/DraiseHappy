package com.or.goodlive.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.or.goodlive.R;
import com.or.goodlive.api.config.ServiceFactory;
import com.or.goodlive.base.BaseFragment;
import com.or.goodlive.control.RxSchedulersHelper;
import com.or.goodlive.module.CoverDataBean;
import com.or.goodlive.ui.adapter.CoverAdapter;
import com.or.goodlive.ui.view.ToastUtil;
import com.or.goodlive.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2017/2/13.
 */

public class CoverFragment extends BaseFragment {

    @Bind(R.id.rcv_cover_list)
    RecyclerView rcvCoverList;
    private Integer category_id=1;
    private Integer pageId=1;
    private Integer count=10;
    private List<CoverDataBean.RstBean.HomeactBean> coverDataList=new ArrayList<>();
    private CoverAdapter adapter;

    public static CoverFragment newInstance() {
        Bundle args = new Bundle();
        CoverFragment fragment = new CoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cover_view;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        Map<String, Integer> params = new HashMap<>();
        params.put("category_id", category_id);
        params.put("pageId", pageId);
        params.put("count", count);
        ServiceFactory.getProvideHttpService().getCoverList(params)
                .compose(RxSchedulersHelper.<CoverDataBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingView();
                    }
                })
                .filter(new Func1<CoverDataBean, Boolean>() {
                    @Override
                    public Boolean call(CoverDataBean coverDataBean) {
                        if (!Constants.RESULT_CODE_SUCCESS.equals(coverDataBean.getErrno())) {
                            ToastUtil.makeText(mContext, coverDataBean.getErr());
                        }
                        return Constants.RESULT_CODE_SUCCESS.equals(coverDataBean.getErrno());
                    }
                })
                .subscribe(new Action1<CoverDataBean>() {
                    @Override
                    public void call(CoverDataBean coverDataBean) {
                        if(null!=coverDataBean.getRst().getHomeact()){
                            initListData(coverDataBean.getRst().getHomeact());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.makeText(mContext,throwable.getMessage());
                        hideLoadingView();}
                }, new Action0() {
                    @Override
                    public void call() {
                        hideLoadingView();
                    }
                });
    }

    private void initListData(List<CoverDataBean.RstBean.HomeactBean> homeact) {
        coverDataList.addAll(homeact);
        for (int i = 0; i < 2; i++) {
            CoverDataBean.RstBean.HomeactBean b = new CoverDataBean.RstBean.HomeactBean();
            coverDataList.add(b);
        }
        adapter=new CoverAdapter(R.layout.item_coverdata_list,coverDataList);
        rcvCoverList.setAdapter(adapter);
    }


    private void initView() {
        rcvCoverList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_right_image, menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

