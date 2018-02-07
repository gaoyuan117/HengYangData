package com.jzbwlkj.hengyangdata.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzbwlkj.hengyangdata.AppConfig;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseFragment;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.activity.AboutUsActivity;
import com.jzbwlkj.hengyangdata.ui.activity.AvatarActivity;
import com.jzbwlkj.hengyangdata.ui.activity.LoginActivity;
import com.jzbwlkj.hengyangdata.ui.activity.MainActivity;
import com.jzbwlkj.hengyangdata.ui.activity.UserManagementActivity;
import com.jzbwlkj.hengyangdata.ui.bean.AboutBean;
import com.jzbwlkj.hengyangdata.ui.bean.UserBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.GlideCircleTransform;
import com.jzbwlkj.hengyangdata.utils.SharedPreferencesUtil;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by gaoyuan on 2018/1/7.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_my_manager)
    TextView tvMyManager;
    @BindView(R.id.tv_my_kefu)
    TextView tvMyKefu;
    @BindView(R.id.tv_my_about)
    TextView tvMyAbout;
    @BindView(R.id.tv_my_exit)
    TextView tvMyExit;

    private AboutBean aboutBean;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDatas() {
        getAboutUs();
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.img_avatar, R.id.tv_my_manager, R.id.tv_my_kefu, R.id.tv_my_about, R.id.tv_my_exit, R.id.tv_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar://头像
                if (noLogin()) {
                    CommonApi.showLoginDialog(activity);
                    return;
                }
                toActivity(AvatarActivity.class);
                break;
            case R.id.tv_my_manager://用户管理
                if (noLogin()) {
                    CommonApi.showLoginDialog(activity);
                    return;
                }
                toActivity(UserManagementActivity.class);
                break;
            case R.id.tv_my_kefu://客服
                if (aboutBean.getSwitchX() == 1) {
                    phoneDialog();
                } else {
                    ToastUtils.showToast("客服暂未开放");
                }
                break;
            case R.id.tv_my_about://关于我们
                Intent intent = new Intent(activity, AboutUsActivity.class);
                intent.putExtra("content", aboutBean);
                startActivity(intent);
                break;
            case R.id.tv_my_exit://退出
                exitDialog();
                break;
            case R.id.tv_name://登录
                if (noLogin()) {
                    toActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
        if (noLogin()) {
            tvMyExit.setVisibility(View.GONE);
        } else {
            tvMyExit.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 客服电话
     */
    private void phoneDialog() {
        View view = View.inflate(getActivity(), R.layout.dialog_kefu, null);
        TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        tvPhone.setText(aboutBean.getPhone());
        Dialog dialog = new Dialog(getActivity(), R.style.wx_dialog);
        dialog.setContentView(view);
        dialog.show();
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonApi.takePhone(getActivity(), aboutBean.getPhone());
            }
        });
    }

    /**
     * 退出对话框
     */
    private void exitDialog() {

        View view = View.inflate(activity, R.layout.dialog_common, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvMessage.setText("是否退出登录?");
        final Dialog dialog = new Dialog(activity, R.style.wx_dialog);
        dialog.setContentView(view);
        dialog.show();

        view.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 退出登录
     */
    private void exit() {
        BaseApp.token = "";
        SharedPreferencesUtil.getInstance().putString("token", "");
        imgAvatar.setImageResource(R.mipmap.avatar_default);
        tvName.setText("登录/注册");
        toActivity(LoginActivity.class);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RetrofitClient.getInstance().createApi().getUserInfo(BaseApp.token)
                .compose(RxUtils.<HttpResult<UserBean>>io_main())
                .subscribe(new BaseObjObserver<UserBean>(activity) {
                    @Override
                    protected void onHandleSuccess(UserBean userBean) {
                        BaseApp.userBean = userBean;
                        tvName.setText(userBean.getUser_nickname());
                        CommonApi.glideUtils(imgAvatar, userBean.getAvatar(), R.mipmap.avatar_default);

                        Glide.with(activity).load(AppConfig.BASE_URL + userBean.getAvatar())
                                .error(R.mipmap.avatar_default)
                                .transform(new GlideCircleTransform(activity))
                                .into(imgAvatar);
                    }
                });
    }

    /**
     * 关于我们
     */
    private void getAboutUs() {
        RetrofitClient.getInstance().createApi().getAbout("")
                .compose(RxUtils.<HttpResult<AboutBean>>io_main())
                .subscribe(new BaseObjObserver<AboutBean>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(AboutBean bean) {
                        aboutBean = bean;
                    }
                });
    }
}
