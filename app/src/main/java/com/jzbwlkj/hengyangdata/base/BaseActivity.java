package com.jzbwlkj.hengyangdata.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.utils.AppManager;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(getActivity(), R.color.global),0);
        AppManager.getAppManager().addActivity(this);
        progressDialog = new ProgressDialog(this);
        initView();
        initDatas();
        configViews();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initDatas();
    public abstract void configViews();


    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 土司
     */
    protected void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    protected BaseActivity getActivity() {
        return BaseActivity.this;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void back(View view) {
        finish();
    }


    /**
     * 设置左边title
     */
    protected void setLeftTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_left_title);
        tvTitle.setText(title);
    }

    /**
     * 设置中间title
     */
    protected void setCenterTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.center_title_tv);
        tvTitle.setText(title);

        goBack();
    }

    /**
     * 设置右边title
     */
    protected TextView setRightTitle(String title) {
        if (title == null) {
            return null;
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_right_text);
        tvTitle.setText(title);
        return tvTitle;
    }

    /**
     * 设置右图片
     */
    protected void setImg_right2(int id) {
        if (id == 0) {
            return;
        }
        ImageView ImageView = (android.widget.ImageView) findViewById(R.id.iv_right2);
        ImageView.setVisibility(View.VISIBLE);
        ImageView.setImageResource(id);
    }

    /**
     * 设置最右图片
     */
    protected ImageView setImg_right(int id) {
        if (id == 0) {
            return null;
        }
        ImageView ImageView = (android.widget.ImageView) findViewById(R.id.img_right);
        ImageView.setVisibility(View.VISIBLE);
        ImageView.setImageResource(id);
        return ImageView;
    }

    /**
     * 设置布局
     */
    protected void goBack() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void toActivity(Class<?> activity){
        startActivity(new Intent(getActivity(), activity));
    }

    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public boolean noLogin() {
        if (TextUtils.isEmpty(BaseApp.token))
            return true;
        else return false;
    }



}
