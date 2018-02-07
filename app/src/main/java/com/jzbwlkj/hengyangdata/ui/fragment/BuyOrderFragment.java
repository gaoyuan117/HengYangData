package com.jzbwlkj.hengyangdata.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseFragment;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.activity.BuyOrderActivity;
import com.jzbwlkj.hengyangdata.ui.activity.ForgetAlonePwdActivity;
import com.jzbwlkj.hengyangdata.ui.activity.LoginActivity;
import com.jzbwlkj.hengyangdata.ui.adapter.BuyOrderFmAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.CheckAlonePwdBean;
import com.jzbwlkj.hengyangdata.ui.bean.OrderDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.WaitOrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.IMEUtils;
import com.jzbwlkj.hengyangdata.utils.LogUtils;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;
import com.jzbwlkj.hengyangdata.view.PasswordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by gaoyuan on 2018/1/8.
 * 已付款订单
 */

public class BuyOrderFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<WaitOrderBean> mList = new ArrayList<>();
    private BuyOrderFmAdapter mAdapter;
    private WaitOrderBean orderBean;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {


        mAdapter = new BuyOrderFmAdapter(R.layout.item_order, mList);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        orderBean = mList.get(position);
        passwordDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(BaseApp.token)) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }else {
            orderList();
        }
    }

    @Override
    public void onRefresh() {
        orderList();
    }

    private void passwordDialog() {
        final View view = View.inflate(getActivity(), R.layout.dialog_pass_word, null);
        final PasswordView passwordView = (PasswordView) view.findViewById(R.id.passwordView);

        final Dialog dialog = new Dialog(getActivity(), R.style.wx_dialog);
        dialog.setContentView(view);
        dialog.show();

        activity.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                IMEUtils.toggleSoftInput(activity);
            }
        }, 100);

        passwordView.setPasswordListener(new PasswordView.PasswordListener() {
            @Override
            public void passwordChange(String changeText) {

            }

            @Override
            public void passwordComplete() {
                IMEUtils.toggleSoftInput(activity);
                LogUtils.e("输入的东西：" + passwordView.getPassword());
                orderDetails(passwordView.getPassword());
                dialog.dismiss();
            }

            @Override
            public void keyEnterPress(String password, boolean isComplete) {

            }
        });

        view.findViewById(R.id.tv_dialog_forget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noLogin()) {
                    toActivity(LoginActivity.class);
                } else {
                    toActivity(ForgetAlonePwdActivity.class);
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                activity.getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        IMEUtils.hideSoftInput(activity);
                    }
                }, 100);
            }
        });
    }

    /**
     * 订单列表
     */
    private void orderList() {
        RetrofitClient.getInstance().createApi().waitOrderList(BaseApp.token, "1")
                .compose(RxUtils.<HttpResult<List<WaitOrderBean>>>io_main())
                .subscribe(new BaseObjObserver<List<WaitOrderBean>>(activity, refresh) {
                    @Override
                    protected void onHandleSuccess(List<WaitOrderBean> list) {
                        if (CommonApi.isEmpty(list)) return;
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    /**
     * 订单详情
     *
     * @param pwd
     */
    private void orderDetails(String pwd) {
        RetrofitClient.getInstance().createApi().checkAlonePwd(BaseApp.token, orderBean.getId() + "", pwd)
                .compose(RxUtils.<HttpResult<CheckAlonePwdBean>>io_main())
                .subscribe(new Consumer<HttpResult<CheckAlonePwdBean>>() {
                    @Override
                    public void accept(HttpResult<CheckAlonePwdBean> bean) throws Exception {
                        if (bean.code == 200) {
                            Intent intent = new Intent(activity, BuyOrderActivity.class);
                            intent.putExtra("id", orderBean.getId() + "");
                            intent.putExtra("msg_id", orderBean.getMsg_id() + "");
                            startActivity(intent);
                        } else {
                            ToastUtils.toastDialog(activity, R.mipmap.dialog_error, "您输入的独立密码错误");
                        }
                    }
                });
    }


}
