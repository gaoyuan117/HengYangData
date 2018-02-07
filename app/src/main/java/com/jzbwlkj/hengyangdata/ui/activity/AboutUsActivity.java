package com.jzbwlkj.hengyangdata.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.ui.bean.AboutBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.tv_vertion)
    TextView tvVertion;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.img_about_logo)
    ImageView imgAboutLogo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        setCenterTitle("关于我们");
    }

    @Override
    public void initDatas() {
        AboutBean bean = (AboutBean) getIntent().getSerializableExtra("content");
        tvContent.setText(bean.getContent());
        tvVertion.setText(bean.getVersion());
        CommonApi.glideUtils(imgAboutLogo, bean.getLogo());
    }

    @Override
    public void configViews() {

    }


}
