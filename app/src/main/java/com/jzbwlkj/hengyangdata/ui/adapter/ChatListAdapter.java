package com.jzbwlkj.hengyangdata.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.ui.bean.NewsListBean;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/12.
 */

public class ChatListAdapter extends BaseQuickAdapter<NewsListBean.ListBean, BaseViewHolder> {

    public ChatListAdapter(@LayoutRes int layoutResId, @Nullable List<NewsListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsListBean.ListBean item) {
        helper.setText(R.id.tv_chat_time, item.getUptime())
                .setText(R.id.tv_chat_content, item.getContent());

        ImageView imgRed = helper.getView(R.id.img_chat_red);
        if (item.getIs_read() > 0) {
            imgRed.setVisibility(View.VISIBLE);
        } else {
            imgRed.setVisibility(View.GONE);
        }


    }
}
