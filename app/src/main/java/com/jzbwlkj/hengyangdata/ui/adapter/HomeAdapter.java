package com.jzbwlkj.hengyangdata.ui.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzbwlkj.hengyangdata.AppConfig;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.ui.bean.MessageListBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/8.
 */

public class HomeAdapter extends BaseQuickAdapter<MessageListBean, BaseViewHolder> {

    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap;


    public HomeAdapter(@LayoutRes int layoutResId, @Nullable List<MessageListBean> data) {
        super(layoutResId, data);
        countDownMap = new SparseArray<>();

    }

    @Override
    protected void convert(BaseViewHolder helper, final MessageListBean data) {
        final TextView tv_hour = helper.getView(R.id.tv_hour);
        final TextView tv_minute = helper.getView(R.id.tv_minute);
        final TextView tv_s = helper.getView(R.id.tv_s);
        final LinearLayout layout = helper.getView(R.id.layout);


        final TextView tvStop = helper.getView(R.id.tv_home_stop);
        ImageView imgCover = helper.getView(R.id.img_home);
        ImageView imgStatus = helper.getView(R.id.img_home_status);
        final LinearLayout llTime = helper.getView(R.id.ll_time);

        String url = "";
        if (!url.contains("upload")) {
            url = AppConfig.BASE_URL + "/upload/" + data.getImg();
        } else {
            url = AppConfig.BASE_URL + data.getImg();
        }
        Glide.with(mContext).load(url).placeholder(R.mipmap.cover_default).error(R.mipmap.cover_default).into(imgCover);

        TextView tvPrice = helper.getView(R.id.tv_home_price);
        tvPrice.setText("¥ " + data.getMoney());
        tvPrice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        helper.setText(R.id.tv_home_title, data.getTitle());

        imgStatus.setVisibility(View.GONE);
        llTime.setVisibility(View.GONE);
        tvStop.setVisibility(View.GONE);


        final int status = data.getStatus();
        if (status == 3) {//已完成
            imgStatus.setVisibility(View.VISIBLE);

        } else if (status == 2) {//锁定
            tvStop.setVisibility(View.VISIBLE);

        } else if (status == 1) {//进行中
            llTime.setVisibility(View.VISIBLE);
            CountDownTimer countDownTimer = countDownMap.get(layout.hashCode());
            long time = data.getDeadline() * 1000;
            time = time - System.currentTimeMillis();
            //将前一个缓存清除
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            if (time > 0) {
                llTime.setVisibility(View.VISIBLE);
                tvStop.setVisibility(View.GONE);
                countDownTimer = new CountDownTimer(time, 1000) {
                    public void onTick(long millisUntilFinished) {
                        String time = CommonApi.getCountTimeByLong(millisUntilFinished);

                        String[] split = time.split("：");
                        tv_hour.setText(split[0]);
                        tv_minute.setText(split[1]);
                        tv_s.setText(split[2]);
                    }

                    public void onFinish() {
                        llTime.setVisibility(View.GONE);
                        tvStop.setVisibility(View.VISIBLE);
                        data.setStatus(2);
                    }
                }.start();
                countDownMap.put(layout.hashCode(), countDownTimer);
            } else {
                llTime.setVisibility(View.GONE);
                tvStop.setVisibility(View.VISIBLE);
            }
        }

    }

}
