package com.jzbwlkj.hengyangdata.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.AppConfig;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.EventBean;
import com.jzbwlkj.hengyangdata.ui.bean.MessageDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.OrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.LogUtils;
import com.jzbwlkj.hengyangdata.utils.PayUtils;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.tv_home_title)
    TextView tvHomeTitle;
    @BindView(R.id.tv_hour)
    TextView tv_hour;
    @BindView(R.id.tv_minute)
    TextView tv_minute;
    @BindView(R.id.tv_s)
    TextView tv_s;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_home_stop)
    TextView tvHomeStop;
    @BindView(R.id.tv_home_price)
    TextView tvHomePrice;
    @BindView(R.id.cb_wx_pay)
    CheckBox cbWxPay;
    @BindView(R.id.ll_wx)
    LinearLayout llWx;
    @BindView(R.id.cb_ali_pay)
    CheckBox cbAliPay;
    @BindView(R.id.ll_ali)
    LinearLayout llAli;
    @BindView(R.id.tv_detail_price2)
    TextView tvDetailPrice2;
    @BindView(R.id.tv_detail_pay)
    TextView tvDetailPay;

    private String type = AppConfig.WX;
    private MessageDetailBean bean;
    private String no, id;
    private String orderType;


    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        setCenterTitle("确认订单");
    }

    @Override
    public void initDatas() {

        bean = (MessageDetailBean) getIntent().getSerializableExtra("data");
        no = getIntent().getStringExtra("no");
        id = getIntent().getStringExtra("id");

        if (bean != null) {
            setData();
        } else {
            orderType = "wait";
            messageDetail();
        }

    }

    /**
     * 获取信息详情
     */
    private void messageDetail() {
        if (TextUtils.isEmpty(id)) {
            finish();
        }
        RetrofitClient.getInstance().createApi().messageDetail(BaseApp.token, id)
                .compose(RxUtils.<HttpResult<MessageDetailBean>>io_main())
                .subscribe(new BaseObjObserver<MessageDetailBean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(MessageDetailBean detailBean) {
                        bean = detailBean;
                        setData();
                    }
                });
    }

    private void setData() {
        if (bean == null) return;
        CommonApi.glideUtils(imgHome, bean.getImg());
        tvHomePrice.setText("¥ " + bean.getMoney());
        tvDetailPrice2.setText("¥ " + bean.getMoney());
        tvHomePrice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvDetailPrice2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        tvHomeTitle.setText(bean.getTitle());
        CommonApi.glideUtils(imgHome, bean.getImg());

        LogUtils.e("剩余时间：" + (bean.getDeadline() + "\n" + System.currentTimeMillis()));
        long time = 0;
        if (!TextUtils.isEmpty(orderType) && orderType.equals("wait")) {
            time = bean.getDeadline() * 1000 - System.currentTimeMillis();
        } else {
            time = bean.getDeadline();
        }

        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {

                String time = CommonApi.getCountTimeByLong(millisUntilFinished);

                String[] split = time.split("：");

                tv_hour.setText(split[0]);
                tv_minute.setText(split[1]);
                tv_s.setText(split[2]);
            }

            public void onFinish() {
                llTime.setVisibility(View.GONE);
                tvHomeStop.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.cb_wx_pay, R.id.ll_wx, R.id.cb_ali_pay, R.id.ll_ali, R.id.tv_detail_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_wx_pay:
                setSelectPayType(1);
                break;
            case R.id.ll_wx:
                setSelectPayType(1);
                break;
            case R.id.cb_ali_pay:
                setSelectPayType(2);
                break;
            case R.id.ll_ali:
                setSelectPayType(2);
                break;
            case R.id.tv_detail_pay:

                if (isVisible(tvHomeStop)) {
                    ToastUtils.showToast("时间截止，不能购买了");
                    return;
                }

                if (TextUtils.isEmpty(type)) {
                    ToastUtils.showToast("请选择支付方式");
                    return;
                }

                if (TextUtils.isEmpty(no)) {

                } else {

                }

                LogUtils.e("支付类型：" + type);
                PayUtils.pay(no, this, bean.getTitle(), bean.getMoney(), bean.getId() + "", type);
                break;
        }
    }

    private void setSelectPayType(int n) {
        if (n == 1) {//选中了微信
            type = AppConfig.WX;
            cbAliPay.setChecked(false);
            cbWxPay.setChecked(true);
        } else {
            type = AppConfig.ALI;
            cbAliPay.setChecked(true);
            cbWxPay.setChecked(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payCallBack(String type) {

        LogUtils.e("type：" + type);
        if (type.equals(AppConfig.ALI)) {//支付宝支付成功
            showToastMsg("支付宝支付成功");

        } else if (type.equals(AppConfig.WX)) {//微信支付成功

            showToastMsg("微信支付成功");
        }
        EventBean bean = new EventBean();
        bean.type = "1";
        EventBus.getDefault().postSticky(bean);
        DetailActivity.activity.finish();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
