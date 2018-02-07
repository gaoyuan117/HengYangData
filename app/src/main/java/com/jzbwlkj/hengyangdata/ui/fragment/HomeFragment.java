package com.jzbwlkj.hengyangdata.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseFragment;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.activity.ChatListActivity;
import com.jzbwlkj.hengyangdata.ui.activity.DetailActivity;
import com.jzbwlkj.hengyangdata.ui.activity.FreeDetailActivity;
import com.jzbwlkj.hengyangdata.ui.activity.LoginActivity;
import com.jzbwlkj.hengyangdata.ui.activity.SearchActivity;
import com.jzbwlkj.hengyangdata.ui.adapter.HomeAdapter;
import com.jzbwlkj.hengyangdata.ui.adapter.SearchHistoryAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.MessageListBean;
import com.jzbwlkj.hengyangdata.ui.bean.NewsListBean;
import com.jzbwlkj.hengyangdata.ui.bean.SlideListBean;
import com.jzbwlkj.hengyangdata.ui.bean.TimerItem;
import com.jzbwlkj.hengyangdata.ui.bean.UserBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.LogUtils;
import com.jzbwlkj.hengyangdata.utils.MyImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by gaoyuan on 2018/1/7.
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, OnBannerClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private View headView;
    private Banner mBanner;
    private LinearLayout llSearch;
    private ImageView mImgChat;

    private List<MessageListBean> mList = new ArrayList<>();
    private List<SlideListBean> bannerList = new ArrayList<>();

    private HomeAdapter mAdapter;
    private int page = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        headView = View.inflate(getActivity(), R.layout.head_home, null);
        mBanner = (Banner) headView.findViewById(R.id.banner);
        llSearch = (LinearLayout) headView.findViewById(R.id.ll_home_search);
        mImgChat = (ImageView) headView.findViewById(R.id.img_home_chat);
        mAdapter = new HomeAdapter(R.layout.item_home, mList);

    }

    @Override
    public void configViews() {
        EventBus.getDefault().register(this);
        mAdapter.addHeaderView(headView);
        recyclerview.addItemDecoration(rvDivider(1));
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(mAdapter);

        llSearch.setOnClickListener(this);
        mImgChat.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mBanner.setOnBannerClickListener(this);
        refresh.setOnRefreshListener(this);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, recyclerview);
    }

    @Override
    public void initDatas() {
        slideList();
        messageList();
        newsList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_search://搜索
                toActivity(SearchActivity.class);
                break;
            case R.id.img_home_chat://消息
                if (noLogin()) {
                    CommonApi.showLoginDialog(activity);
                    return;
                }
                toActivity(ChatListActivity.class);
                break;
        }
    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new MyImageLoader());
        mBanner.setImages(bannerList);
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setViewPagerIsScroll(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("id", mList.get(position).getId() + "");
        startActivity(intent);
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(activity, FreeDetailActivity.class);
        intent.putExtra("id", bannerList.get(position - 1).getId() + "");
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 1;
        slideList();
        messageList();
        newsList();
    }

    /**
     * 付费信息列表
     */
    private void messageList() {
        RetrofitClient.getInstance().createApi().messageList("", page)
                .compose(RxUtils.<HttpResult<List<MessageListBean>>>io_main())
                .subscribe(new BaseObjObserver<List<MessageListBean>>(activity, refresh, mAdapter) {
                    @Override
                    protected void onHandleSuccess(List<MessageListBean> list) {

                        if (page == 1) {
                            mList.clear();
                        }
                        if (CommonApi.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    /**
     * 轮播图
     */
    public void slideList() {
        RetrofitClient.getInstance().createApi().slideList("")
                .compose(RxUtils.<HttpResult<List<SlideListBean>>>io_main())
                .subscribe(new BaseObjObserver<List<SlideListBean>>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(List<SlideListBean> list) {

                        if (CommonApi.isEmpty(list)) {
                            return;
                        }
                        bannerList.clear();
                        bannerList.addAll(list);
                        setBanner();
                    }
                });
    }

    /**
     * 消息
     */
    private void newsList() {
        RetrofitClient.getInstance().createApi().newsIndex(BaseApp.token)
                .compose(RxUtils.<HttpResult<NewsListBean>>io_main())
                .subscribe(new BaseObjObserver<NewsListBean>(activity, false) {
                    @Override
                    protected void onHandleSuccess(NewsListBean newsListBean) {
                        if (newsListBean.getIs_read() > 0) {
                            mImgChat.setImageResource(R.mipmap.xiaoxidian);
                        } else {
                            mImgChat.setImageResource(R.mipmap.xiaoxi);
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hasNews(String type) {
        LogUtils.e("EventBus：" + type);
        if (type.equals("JPush")) {
            newsList();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        messageList();
    }
}
