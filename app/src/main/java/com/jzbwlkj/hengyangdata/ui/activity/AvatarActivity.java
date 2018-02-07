package com.jzbwlkj.hengyangdata.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jzbwlkj.hengyangdata.AppConfig;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.CommonBean;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.LoginBean;
import com.jzbwlkj.hengyangdata.utils.BitmapCompress;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.LogUtils;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class AvatarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.tv_change)
    TextView tvChange;
    private PopupWindow window;

    private ArrayList<String> picList;
    private String photoByCameraPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_avatar;
    }

    @Override
    public void initView() {
        setCenterTitle("头像更换");
//        setRightTitle("更换").setOnClickListener(this);
    }

    @Override
    public void initDatas() {

        if (BaseApp.userBean != null) {
            CommonApi.glideUtils(imgAvatar, BaseApp.userBean.getAvatar(), R.mipmap.avatar_default);
        }
    }

    @Override
    public void configViews() {
        tvChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right_text:
                selectPop();
                break;
            case R.id.tv_take_photo://拍照
                photoByCameraPath = CommonApi.getPhotoByCamera(this);
                window.dismiss();
                break;
            case R.id.tv_select_photo://选择照片
                MultiImageSelector.create()
                        .showCamera(false)
                        .count(1)
                        .multi()
                        .start(this, AppConfig.REQUEST_IMAGE);
                window.dismiss();
                break;
            case R.id.tv_cancle://取消
                window.dismiss();
                break;
            case R.id.tv_change:
                if (window != null && window.isShowing()) {
                    return;
                }
                selectPop();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == AppConfig.REQUEST_IMAGE) {
            picList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            compress(picList);
        }

        if (requestCode == AppConfig.REQUEST_CAMERA) {
            CommonApi.glideUtils(imgAvatar, photoByCameraPath);
            List<String> list = new ArrayList<>();
            list.add(photoByCameraPath);
            compress(list);
        }
    }

    private void selectPop() {
        View view = View.inflate(this, R.layout.pop_avatar, null);
        view.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        view.findViewById(R.id.tv_select_photo).setOnClickListener(this);
        view.findViewById(R.id.tv_cancle).setOnClickListener(this);

        window = new PopupWindow(view, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setOutsideTouchable(false);
        window.setBackgroundDrawable(new ColorDrawable());
        window.setFocusable(true);
        window.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 压缩图片
     *
     * @param
     */
    private void compress(List<String> list) {
        BitmapCompress.get(this)
                .load(list)
                .putGear(BitmapCompress.THIRD_GEAR)
                .setCompressListener(new BitmapCompress.OnCompressListener() {
                    @Override
                    public void onStart() {
                        showProgressDialog("修改中");
                    }

                    @Override
                    public void onSuccess(List<String> files) {
                        updateAvatar(files);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                    }
                }).launch();
    }

    /**
     * 修改头像
     *
     * @param paths
     */
    public void updateAvatar(final List<String> paths) {
        OkHttpUtils.post()
                .addFile("avatar", "avatar.jpg", new File(paths.get(0)))
                .addParams("token", BaseApp.token)
                .url(AppConfig.BASE_URL + "/api/User/updateAvatar")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HttpResult httpResult = new Gson().fromJson(response, HttpResult.class);
                        if (httpResult.code == 200) {
                            LogUtils.e("图片路径：" + paths.get(0));
                            dismissProgressDialog();
                            ToastUtils.showToast("修改成功");
                            Glide.with(BaseApp.getsInstance()).load(paths.get(0)).error(R.mipmap.cover_default).into(imgAvatar);
                        }

                    }
                });
    }

}
