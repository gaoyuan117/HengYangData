package com.jzbwlkj.hengyangdata.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.adapter.BuyOrderAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.OrderDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.WaitOrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 已付款订单
 */
public class BuyOrderActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<OrderDetailBean.OrderMessageBean> mList = new ArrayList<>();
    private BuyOrderAdapter mAdapter;
    private View footView;
    private String msgId;
    private Disposable disposable;
    private String id;


    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_order;
    }

    @Override
    public void initView() {
        setCenterTitle("订单信息");
        footView = View.inflate(this, R.layout.foot_buy_order, null);
        mAdapter = new BuyOrderAdapter(R.layout.item_buy_order, mList);
        mAdapter.addFooterView(footView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        refresh.setOnRefreshListener(this);

    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra("id");
        msgId = getIntent().getStringExtra("msg_id");

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        orderDetails(id);

                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void configViews() {
        footView.findViewById(R.id.tv_foot_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyOrderActivity.this, DetailActivity.class);
                intent.putExtra("id", msgId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * 订单详情
     *
     * @param id
     */
    private void orderDetails(final String id) {
        RetrofitClient.getInstance().createApi().orderDetails(BaseApp.token, id, "")
                .compose(RxUtils.<HttpResult<OrderDetailBean>>io_main())
                .subscribe(new BaseObjObserver<OrderDetailBean>(this, refresh) {
                    @Override
                    protected void onHandleSuccess(OrderDetailBean bean) {
                        if (CommonApi.isEmpty(bean.getOrder_message())) return;

                        mList.clear();
                        mList.addAll(bean.getOrder_message());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public void onRefresh() {
        orderDetails(id);
    }
}
