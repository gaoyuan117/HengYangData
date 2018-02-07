package com.jzbwlkj.hengyangdata.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.base.ViewPagerAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.EventBean;
import com.jzbwlkj.hengyangdata.ui.bean.EventBean2;
import com.jzbwlkj.hengyangdata.ui.fragment.HomeFragment;
import com.jzbwlkj.hengyangdata.ui.fragment.MyFragment;
import com.jzbwlkj.hengyangdata.ui.fragment.OrderFragment;
import com.jzbwlkj.hengyangdata.view.AlphaTabsIndicator;
import com.jzbwlkj.hengyangdata.view.OnTabChangedListner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabChangedListner {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator mAlphaTabsIndicator;

    private List<Fragment> mList;
    private ViewPagerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        EventBus.getDefault().register(this);
    }

    @Override
    public void initDatas() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        mAlphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        viewPager.setOffscreenPageLimit(4);
        mList = new ArrayList<>();

        mList.add(new HomeFragment());
        mList.add(new OrderFragment());
        mList.add(new MyFragment());
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, mList);
        viewPager.setAdapter(adapter);

        mAlphaTabsIndicator.setViewPager(viewPager);
        mAlphaTabsIndicator.setOnTabChangedListner(this);
        mAlphaTabsIndicator.removeAllBadge();

        String type = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals("message")) {
            if (viewPager == null || mAlphaTabsIndicator == null) {
                return;
            }
            viewPager.setCurrentItem(1);
            mAlphaTabsIndicator.setTabCurrenItem(1);
            EventBean2 bean2 = new EventBean2();
            bean2.type = "1";
            EventBus.getDefault().postSticky(bean2);
        }
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onTabSelected(int tabNum) {
        viewPager.setCurrentItem(tabNum);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra("exit", false);
            if (isExit) {
                this.finish();
            }
        }
    }

    @Subscribe
    public void tabSelect(EventBean bean) {
        if (bean.type.equals("1")) {
            viewPager.setCurrentItem(1);
            mAlphaTabsIndicator.setTabCurrenItem(1);
            EventBean2 bean2 = new EventBean2();
            bean2.type = "1";
            EventBus.getDefault().postSticky(bean2);
        }
    }
}
