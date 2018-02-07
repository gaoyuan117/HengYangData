package com.jzbwlkj.hengyangdata.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.ui.adapter.HomeAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.MessageDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.MessageListBean;
import com.jzbwlkj.hengyangdata.ui.bean.TimerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchResultActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<MessageListBean> mList;
    private HomeAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initView() {
        setCenterTitle("搜索结果");

    }

    @Override
    public void initDatas() {
        mList = (ArrayList<MessageListBean>) getIntent().getSerializableExtra("list");
        mAdapter = new HomeAdapter(R.layout.item_home, mList);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        refresh.setOnRefreshListener(this);
    }

    @Override
    public void configViews() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", mList.get(position).getId() + "");
        startActivity(intent);
    }

    @Override
    public void onRefresh() {

    }
}
