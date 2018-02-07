package com.jzbwlkj.hengyangdata.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.utils.SharedPreferencesUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {


    private String token;

    @Override
    public int getLayoutId() {
        return R.layout.activity_slpash;
    }

    @Override
    public void initView() {
        token = SharedPreferencesUtil.getInstance().getString("token");
        BaseApp.phone = SharedPreferencesUtil.getInstance().getString("phone");
        BaseApp.token = token;

        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        toActivity(MainActivity.class);
                        finish();
                    }
                });
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }
}
