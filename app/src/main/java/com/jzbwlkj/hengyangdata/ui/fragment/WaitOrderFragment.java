package com.jzbwlkj.hengyangdata.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseFragment;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.activity.ConfirmOrderActivity;
import com.jzbwlkj.hengyangdata.ui.activity.DetailActivity;
import com.jzbwlkj.hengyangdata.ui.adapter.OrderAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.WaitOrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gaoyuan on 2018/1/8.
 */

public class WaitOrderFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<WaitOrderBean> mList = new ArrayList<>();
    private OrderAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mAdapter = new OrderAdapter(R.layout.item_order, mList);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void configViews() {
        mAdapter.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(BaseApp.token)) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
        } else {
            waitOrderList();

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(activity, ConfirmOrderActivity.class);
        intent.putExtra("id", mList.get(position).getMsg_id() + "");
        intent.putExtra("no", mList.get(position).getOrder_no() + "");
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        waitOrderList();
    }

    /**
     * 订单列表
     */
    private void waitOrderList() {
        RetrofitClient.getInstance().createApi().waitOrderList(BaseApp.token, "0")
                .compose(RxUtils.<HttpResult<List<WaitOrderBean>>>io_main())
                .subscribe(new BaseObjObserver<List<WaitOrderBean>>(activity, refresh) {
                    @Override
                    protected void onHandleSuccess(List<WaitOrderBean> list) {
                        mList.clear();
                        if (CommonApi.isEmpty(list)) {
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}
