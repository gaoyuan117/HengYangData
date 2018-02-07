package com.jzbwlkj.hengyangdata.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.NewsDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatDetailActivity extends BaseActivity {


    @BindView(R.id.tv_chat_detail_content)
    TextView tvChatDetailContent;
    @BindView(R.id.tv_chat_detail_time)
    TextView tvChatDetailTime;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_detail;
    }

    @Override
    public void initView() {
        setCenterTitle("消息详情");
    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra("id");

        newsDetail();
    }

    @Override
    public void configViews() {

    }

    private void newsDetail(){
        RetrofitClient.getInstance().createApi().newsDetail(BaseApp.token,id)
               .compose(RxUtils.<HttpResult<NewsDetailBean>>io_main())
                .subscribe(new BaseObjObserver<NewsDetailBean>(this,"获取中") {
                    @Override
                    protected void onHandleSuccess(NewsDetailBean newsDetailBean) {
                        tvChatDetailTime.setText(newsDetailBean.getUptime());
                        tvChatDetailContent.setText(newsDetailBean.getContent());
                    }
                });
    }


}
