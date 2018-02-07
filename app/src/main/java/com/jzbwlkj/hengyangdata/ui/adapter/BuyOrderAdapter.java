package com.jzbwlkj.hengyangdata.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.ui.bean.OrderDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.WaitOrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/14.
 */

public class BuyOrderAdapter extends BaseQuickAdapter<OrderDetailBean.OrderMessageBean, BaseViewHolder> {

    public BuyOrderAdapter(@LayoutRes int layoutResId, @Nullable List<OrderDetailBean.OrderMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.OrderMessageBean item) {
        helper.setText(R.id.tv_buy_time, item.getUptime())
                .setText(R.id.tv_buy_des, item.getContent());

    }
}
