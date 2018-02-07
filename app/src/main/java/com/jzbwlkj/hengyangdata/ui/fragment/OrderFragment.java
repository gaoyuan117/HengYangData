package com.jzbwlkj.hengyangdata.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseFragment;
import com.jzbwlkj.hengyangdata.base.ViewPagerAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.EventBean;
import com.jzbwlkj.hengyangdata.ui.bean.EventBean2;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gaoyuan on 2018/1/7.
 */

public class OrderFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mList = new ArrayList<>();


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mTitles.add("已付款");
        mTitles.add("待付款");

        mList.add(new BuyOrderFragment());
        mList.add(new WaitOrderFragment());

        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), getActivity(), mList, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.black), ContextCompat.getColor(getActivity(), R.color.global));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.global));
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                CommonApi.setIndicator(mTabLayout, 50, 50);
            }
        });
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            if (mTabLayout.getTabAt(i) == tab) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Subscribe
    public void tabSelect(EventBean bean) {
        if (bean.type.equals("1")) {
            mViewPager.setCurrentItem(0);
            mTabLayout.getTabAt(0).select();
        }
    }

}
