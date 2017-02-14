package com.or.goodlive.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.base.BaseActivity;
import com.or.goodlive.ui.adapter.FragmentTabAdapter;
import com.or.goodlive.ui.fragment.CoverFragment;
import com.or.goodlive.ui.fragment.LocaleFragment;
import com.or.goodlive.ui.fragment.PeerFragment;
import com.or.goodlive.ui.fragment.YaMingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/2/7.
 */

public class MainActivity extends BaseActivity {

    @Bind(R.id.tl_main)
    TabLayout tabLayout;
    @Bind(R.id.vp_main)
    ViewPager viewPager;


    private List<Fragment> fragments;


    public int []tabIcons={R.drawable.selector_main_tab_cover,R.drawable.selector_main_tab_yaming,R.drawable.selector_main_tab_locale,R.drawable.selector_main_tab_peer};
    public String[] titles={"封面","一鸣","现场","同行"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        initData();
    }

    private void initData(){
        fragments = new ArrayList<>();
        fragments.add(CoverFragment.newInstance());
        fragments.add(YaMingFragment.newInstance());
        fragments.add(LocaleFragment.newInstance());
        fragments.add(PeerFragment.newInstance());

        List<String>list_titles=new ArrayList<>();
        for(String titile:titles)
        {
            list_titles.add(titile);
        }
        FragmentTabAdapter adapter = new FragmentTabAdapter(getSupportFragmentManager(), fragments, list_titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager,true);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
        tabLayout.getTabAt(3).setCustomView(getTabView(3));
    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_main_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tv_main_tab);
        txt_title.setText(titles[position]);
        ImageView img_title = (ImageView) view.findViewById(R.id.iv_main_tab);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

}
