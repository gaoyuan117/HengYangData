package com.jzbwlkj.hengyangdata.ui.adapter;

import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.ui.bean.WaitOrderBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/14.
 */

public class BuyOrderFmAdapter extends BaseQuickAdapter<WaitOrderBean, BaseViewHolder> {

    public BuyOrderFmAdapter(@LayoutRes int layoutResId, @Nullable List<WaitOrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WaitOrderBean item) {
        TextView tvNum = helper.getView(R.id.tv_order_num);
        ImageView imgStatus = helper.getView(R.id.img_order_status);
        ImageView imgCover = helper.getView(R.id.img_order);


        int status = item.getStatus();
        int nums = item.getNums();

        if (nums > 0) {
            tvNum.setVisibility(View.VISIBLE);
            tvNum.setText(nums + "");
        } else {
            tvNum.setText("0");
            tvNum.setVisibility(View.GONE);
        }

        if (status == 3) {
            imgStatus.setVisibility(View.VISIBLE);
        } else {
            imgStatus.setVisibility(View.GONE);
        }

        String createtime = item.getCreatetime();
        String[] split = createtime.split(":");
        try {
            TextView tvPrice = helper.getView(R.id.tv_order_price);
            tvPrice.setText("¥ " + item.getMoney());
            tvPrice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            CommonApi.glideUtils(imgCover, item.getImg());
            helper.setText(R.id.tv_order_title, item.getTitle())
                    .setText(R.id.tv_order_stop, "购买时间:" + split[0] + ":" + split[1]);
        } catch (Exception e) {

        }

    }
}
