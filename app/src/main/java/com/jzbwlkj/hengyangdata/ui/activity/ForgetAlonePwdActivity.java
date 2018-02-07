package com.jzbwlkj.hengyangdata.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.CountDownUtils;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetAlonePwdActivity extends BaseActivity implements CountDownUtils.CountdownListener {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.tv_yzm)
    TextView tvYzm;
    @BindView(R.id.tv_certain)
    TextView tvCertain;

    private boolean isOpen;
    private CountDownUtils countDown;
    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_alone_pwd;
    }

    @Override
    public void initView() {
        setCenterTitle("忘记独立密码");
        countDown = new CountDownUtils(tvYzm, "%s秒", 60);
        countDown.setCountdownListener(this);
        etPhone.setText(BaseApp.phone);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.img_eye, R.id.tv_yzm, R.id.tv_certain})
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
                CommonApi.sendsms(this, "forgotten", phone);
                countDown.start();

                break;
            case R.id.tv_certain:
                forgetAlonePwd();
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

    private void forgetAlonePwd() {
        phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_phone));
            return;
        }
        String pwd = etNewPwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_new_pwd));
            return;
        }
        String code = etYzm.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_yzm));
            return;
        }
        RetrofitClient.getInstance().createApi().forgetdulipwd(phone, code, pwd)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "修改中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        ToastUtils.showToast("修改成功");
                        finish();
                    }
                });
    }
}
