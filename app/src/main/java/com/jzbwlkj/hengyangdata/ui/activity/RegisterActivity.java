package com.jzbwlkj.hengyangdata.ui.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.CommonBean;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.LoginBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.CountDownUtils;
import com.jzbwlkj.hengyangdata.utils.SharedPreferencesUtil;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class RegisterActivity extends BaseActivity implements CountDownUtils.CountdownListener {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.et_alone_pwd)
    EditText etAlonePwd;
    @BindView(R.id.tv_yzm)
    TextView tvYzm;
    @BindView(R.id.tv_certain)
    TextView tvCertain;

    private boolean isOpen;
    private CountDownUtils countDown;
    private String phone;
    private String password;
    private String yzm;
    private String alonePwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        setCenterTitle("注册");
        setRightTitle("登录");
        countDown = new CountDownUtils(tvYzm, "%s秒", 60);
        countDown.setCountdownListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.img_eye, R.id.tv_yzm, R.id.tv_certain, R.id.img_wen, R.id.tv_right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_eye:
                isOpen = !isOpen;
                if (isOpen) {
                    imgEye.setImageResource(R.mipmap.yanjingkai);
                    etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    imgEye.setImageResource(R.mipmap.yanjingbi);
                    etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.tv_yzm:
                phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast(getResources().getString(R.string.please_input_phone));
                    return;
                }
                CommonApi.sendsms(this, "register", phone);
                countDown.start();
                break;
            case R.id.tv_certain:
                register();
                break;
            case R.id.img_wen:
                wenDialog();
                break;
            case R.id.tv_right_text:
                toActivity(LoginActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onStartCount() {

    }

    @Override
    public void onFinishCount() {
        if (tvYzm == null) {
            return;
        }
        tvYzm.setEnabled(true);
        tvYzm.setText("重新获取");
    }

    @Override
    public void onUpdateCount(int currentRemainingSeconds) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDown.isRunning()) {
            countDown.stop();
        }
    }

    /**
     * 注册
     */
    private void register() {
        phone = etPhone.getText().toString();
        password = etNewPwd.getText().toString();
        yzm = etYzm.getText().toString();
        alonePwd = etAlonePwd.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_phone));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_pwd));
            return;
        }
        if (TextUtils.isEmpty(yzm)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_yzm));
            return;
        }
        if (TextUtils.isEmpty(alonePwd)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_alone));
            return;
        }

        RetrofitClient.getInstance().createApi().register(phone, password, yzm, alonePwd)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "注册中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        ToastUtils.showToast("注册成功");
                        login();
                    }
                });
    }

    private void wenDialog() {
        View view = View.inflate(this, R.layout.dialog_wen, null);
        final Dialog dialog = new Dialog(this, R.style.wx_dialog);
        dialog.setContentView(view);
        dialog.show();
        view.findViewById(R.id.tv_queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        RetrofitClient.getInstance().createApi().login(phone, password)
                .compose(RxUtils.<HttpResult<LoginBean>>io_main())
                .subscribe(new BaseObjObserver<LoginBean>(this, "登录中") {
                    @Override
                    protected void onHandleSuccess(LoginBean loginBean) {
                        SharedPreferencesUtil.getInstance().putString("token", loginBean.getToken());
                        SharedPreferencesUtil.getInstance().putString("phone", loginBean.getNickname());
                        BaseApp.phone = loginBean.getNickname();
                        BaseApp.token = loginBean.getToken();

                        if (!TextUtils.isEmpty(phone)) {
                            Set<String> strings = new HashSet<>();
                            strings.add(phone);
                            JPushInterface.setAliasAndTags(RegisterActivity.this, phone, strings, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });
                        }

                        toActivity(MainActivity.class);

                        finish();
                    }
                });
    }
}
