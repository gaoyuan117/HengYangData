package com.jzbwlkj.hengyangdata.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.CommonBean;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.adapter.ChatListAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.NewsListBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<NewsListBean.ListBean> mList = new ArrayList<>();
    private ChatListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_list;
    }

    @Override
    public void initView() {
        setCenterTitle("消息列表");
        setRightTitle("清空");
        mAdapter = new ChatListAdapter(R.layout.item_chat, mList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void initDatas() {


    }

    @Override
    public void configViews() {
        mAdapter.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);
    }

    @OnClick(R.id.tv_right_text)
    public void onViewClicked() {
        clearDialog();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, ChatDetailActivity.class);
        intent.putExtra("id", mList.get(position).getId() + "");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        newsList();
    }

    @Override
    public void onRefresh() {
        newsList();
    }

    /**
     * 消息
     */
    private void newsList() {
        RetrofitClient.getInstance().createApi().newsIndex(BaseApp.token)
                .compose(RxUtils.<HttpResult<NewsListBean>>io_main())
                .subscribe(new BaseObjObserver<NewsListBean>(this, refresh) {
                    @Override
                    protected void onHandleSuccess(NewsListBean newsListBean) {
                        if (CommonApi.isEmpty(newsListBean.getList())) {
                            return;
                        }
                        mList.clear();
                        mList.addAll(newsListBean.getList());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 清空
     */
    private void clearDialog() {

        View view = View.inflate(this, R.layout.dialog_common, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvMessage.setText("是否清空列表?");
       final Dialog dialog = new Dialog(this, R.style.wx_dialog);
        dialog.setContentView(view);
        dialog.show();

        view.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNews();
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 清空列表
     */
    private void clearNews() {
        RetrofitClient.getInstance().createApi().clearNews(BaseApp.token)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "清空中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        ToastUtils.showToast("清空成功");
                    }
                });
    }

}
