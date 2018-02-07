package com.jzbwlkj.hengyangdata.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jzbwlkj.hengyangdata.R;

import java.util.List;

/**
 * Created by gaoyuan on 2018/1/12.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {


    public SearchHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_search,item);
    }
}
