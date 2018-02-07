package com.jzbwlkj.hengyangdata.ui.activity;

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
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.LoginBean;
import com.jzbwlkj.hengyangdata.utils.SharedPreferencesUtil;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    private boolean isOpen;
    private String phone;
    private String password;
    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setCenterTitle("登录");
        setRightTitle("注册");
    }

    @Override
    public void initDatas() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void configViews() {
        String phone = SharedPreferencesUtil.getInstance().getString("phone");
        if (!TextUtils.isEmpty(phone)) {
            etPhone.setText(phone);
        }
    }

    @OnClick({R.id.img_eye, R.id.tv_login, R.id.tv_right_text, R.id.tv_forget_pwd})
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
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_forget_pwd:
                toActivity(ForgetPwdActivity.class);
                break;
            case R.id.tv_right_text:
                toActivity(RegisterActivity.class);
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        phone = etPhone.getText().toString();
        password = etNewPwd.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_phone));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_pwd));
            return;
        }

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
                            JPushInterface.setAliasAndTags(LoginActivity.this, phone, strings, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });
                        }

                        finish();
                    }
                });
    }

}
