package com.jzbwlkj.hengyangdata.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.jzbwlkj.hengyangdata.AppConfig;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.alipay.AliPay;
import com.jzbwlkj.hengyangdata.base.Constants;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.OrderBean;
import com.jzbwlkj.hengyangdata.wxapi.WxPay;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by gaoyuan on 2017/11/24.
 */

public class PayUtils {
    private static String des;

    /**
     * 支付
     *
     * @param item  支付项目 取值：recharge 充值 member 购买会员
     * @param money 支付金额
     * @param id    套餐id
     * @param type  支付方式 alipay 支付宝 wxpay 微信支付
     */
    public static void pay(String no, final Activity activity, final String item, final String money, String id, final String type) {
        des = item;
        if (!TextUtils.isEmpty(no)) {
            if (type.equals(AppConfig.ALI)) {
                aliPay(activity, money, no);
            } else if (type.equals(AppConfig.WX)) {
                wxPay(activity, money, no);
            }
        } else {
            RetrofitClient.getInstance().createApi().createOrder(BaseApp.token, id, type)
                    .compose(RxUtils.<HttpResult<OrderBean>>io_main())
                    .subscribe(new BaseObjObserver<OrderBean>(activity, "创建订单中") {
                        @Override
                        protected void onHandleSuccess(OrderBean orderBean) {
                            if (TextUtils.isEmpty(orderBean.getOrder_no())) {
                                ToastUtils.showToast("创建订单失败");
                                return;
                            }

                            if (type.equals(AppConfig.ALI)) {
                                aliPay(activity, money, orderBean.getOrder_no());
                            } else if (type.equals(AppConfig.WX)) {
                                wxPay(activity, money, orderBean.getOrder_no());
                            }

                        }
                    });
        }

    }

    public static void wxPay(Activity activity, String money, String orderNo) {
        WxPay.getWxPay().pay(activity, orderNo, des, money, Constants.WxPay.NOTIFY_URL, new WxPay.WxCallBack() {
            @Override
            public void payResponse(int code) {
                EventBus.getDefault().postSticky(AppConfig.WX);
            }
        });
    }

    /**
     * 支付宝支付
     *
     * @param activity
     * @param money
     * @param orderNo
     */
    public static void aliPay(Activity activity, String money, String orderNo) {

        new AliPay(activity).payV2(money, des, orderNo, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                EventBus.getDefault().postSticky(AppConfig.ALI);
            }

            @Override
            public void onDeeling() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


}
