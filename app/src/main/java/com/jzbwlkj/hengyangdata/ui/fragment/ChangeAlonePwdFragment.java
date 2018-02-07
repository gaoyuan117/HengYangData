package com.jzbwlkj.hengyangdata.ui.fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseFragment;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.CommonBean;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyuan on 2018/1/11.
 */

public class ChangeAlonePwdFragment extends BaseFragment {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.tv_yzm)
    TextView tvYzm;
    private String yzm;
    private boolean isOpen;
    private String phone;
    private String oldPwd;
    private String newPwd;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_change_alone_pwd;
    }

    @Override
    public void initView() {
        yzm = CommonApi.getNum();
        tvYzm.setText(yzm);
    }

    @Override
    public void initDatas() {
        etPhone.setText(BaseApp.phone);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.img_eye, R.id.et_yzm, R.id.tv_yzm, R.id.tv_certain})
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
            case R.id.et_yzm:

                break;
            case R.id.tv_yzm:
                yzm = CommonApi.getNum();
                tvYzm.setText(yzm);
                break;
            case R.id.tv_certain:
                modifypwd();
                break;
        }
    }

    /**
     * 修改独立密码
     */
    private void modifypwd() {
        phone = etPhone.getText().toString();
        oldPwd = etOldPwd.getText().toString();
        newPwd = etNewPwd.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_phone));
            return;
        }
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_old_pwd));
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_new_pwd));
            return;
        }
        if (TextUtils.isEmpty(etYzm.getText().toString())) {
            ToastUtils.showToast(getResources().getString(R.string.please_input_yzm));
            return;
        }
        if (newPwd.length()<6){
            ToastUtils.showToast("独立密码长度为6位");
            return;
        }
        if (!etYzm.getText().toString().equals(yzm.replaceAll(" ", ""))) {
            ToastUtils.showToast("验证码错误");
            return;
        }

        RetrofitClient.getInstance().createApi().modifydulipwd(BaseApp.token,phone,oldPwd, newPwd)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(activity, "修改中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        EventBus.getDefault().post("change_alone");
                    }
                });
    }
}
