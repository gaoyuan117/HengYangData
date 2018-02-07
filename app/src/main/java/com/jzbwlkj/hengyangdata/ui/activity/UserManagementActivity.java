package com.jzbwlkj.hengyangdata.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.base.ViewPagerAdapter;
import com.jzbwlkj.hengyangdata.ui.fragment.ChangeAlonePwdFragment;
import com.jzbwlkj.hengyangdata.ui.fragment.ChangePwdFragment;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.http.Part;

/**
 * 用户管理
 */
public class UserManagementActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_management;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        setCenterTitle("用户管理");

        mTitles.add("修改登录密码");
        mTitles.add("修改独立密码");

        mList.add(new ChangePwdFragment());
        mList.add(new ChangeAlonePwdFragment());

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getActivity(), mList, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.black), ContextCompat.getColor(getActivity(), R.color.white));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.global));
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            if (mTabLayout.getTabAt(i) == tab) {
                mViewPager.setCurrentItem(i);
//                if (i == 0) {
//                    setCenterTitle("修改密码");
//                } else {
//                    setCenterTitle(tab.getText().toString());
//                }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getResult(String what) {
        if (what.equals("change_pwd")) {
            ToastUtils.toastDialog(getActivity(), R.mipmap.dialog_success, "修改登录密码成功");
            finish();
        }else if (what.equals("change_alone")){
            ToastUtils.toastDialog(getActivity(), R.mipmap.dialog_success, "修改独立密码成功");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
