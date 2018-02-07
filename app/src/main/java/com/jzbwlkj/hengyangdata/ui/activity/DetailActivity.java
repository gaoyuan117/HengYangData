package com.jzbwlkj.hengyangdata.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.BaseApp;
import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.MessageDetailBean;
import com.jzbwlkj.hengyangdata.ui.bean.MessageListBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.LogUtils;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tv_home_title)
    TextView tvHomeTitle;
    @BindView(R.id.img_detail)
    ImageView imgDetail;
    @BindView(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @BindView(R.id.tv_hour)
    TextView tv_hour;
    @BindView(R.id.tv_minute)
    TextView tv_minute;
    @BindView(R.id.tv_s)
    TextView tv_s;
    @BindView(R.id.ll_detail_price)
    LinearLayout llDetailPrice;
    @BindView(R.id.tv_detail_content)
    TextView tvDetailContent;
    @BindView(R.id.tv_detail_price2)
    TextView tvDetailPrice2;
    @BindView(R.id.tv_detail_pay)
    TextView tvDetailPay;
    @BindView(R.id.tv_detail_stop)
    TextView tvDetailStop;
    @BindView(R.id.tv_detail_add_time)
    TextView tvDetailAddTime;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.ll_detail_bottom)
    LinearLayout llDetailBottom;

    private String id;
    private CountDownTimer countDownTimer;
    private long finishedTime;
    private MessageDetailBean bean;
    private String no;
    public static DetailActivity activity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        activity = this;
        setCenterTitle("信息详情");
    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra("id");
        no = getIntent().getStringExtra("no");//订单号
        messageDetail();
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.img_detail, R.id.tv_detail_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_detail:
                break;
            case R.id.tv_detail_pay:
                if (noLogin()) {
                    CommonApi.showLoginDialog(this);
                    return;
                }
                Intent intent = new Intent(this, ConfirmOrderActivity.class);
                intent.putExtra("data", bean);
                if (!TextUtils.isEmpty(no)) {
                    intent.putExtra("no", no);
                }
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取信息详情
     */
    private void messageDetail() {
        RetrofitClient.getInstance().createApi().messageDetail(BaseApp.token, id)
                .compose(RxUtils.<HttpResult<MessageDetailBean>>io_main())
                .subscribe(new BaseObjObserver<MessageDetailBean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(MessageDetailBean detailBean) {
                        bean = detailBean;
                        setData();
                    }
                });
    }

    private void setData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        tvDetailAddTime.setText(format.format(bean.getAddtime()*1000));
        tvDetailPrice.setText("¥ " + bean.getMoney());
        tvDetailPrice2.setText("¥ " + bean.getMoney());

        tvDetailPrice.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvDetailPrice2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        tvHomeTitle.setText(bean.getTitle());
        CommonApi.glideUtils(imgDetail, bean.getImg());
        String linkCss = "<style type=\"text/css\"> " +
                "img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "</style>";
        String html = "<html><header>" + linkCss + "</header>" + bean.getDescribe() + "</body></html>";
        webview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

        int status = bean.getStatus();

        if (status == 3) {//已完成

        } else if (status == 2) {//锁定
            tvDetailStop.setVisibility(View.VISIBLE);

        } else if (status == 1) {//进行中
            long time = bean.getDeadline() * 1000;
            time = time - System.currentTimeMillis();

            if (time > 0) {
                llDetailBottom.setVisibility(View.VISIBLE);
                llDetailPrice.setVisibility(View.VISIBLE);
                tvDetailStop.setVisibility(View.GONE);
                countDownTimer = new CountDownTimer(time, 1000) {
                    public void onTick(long millisUntilFinished) {
                        bean.setDeadline(millisUntilFinished);

                        String time = CommonApi.getCountTimeByLong(millisUntilFinished);

                        String[] split = time.split("：");

                        tv_hour.setText(split[0]);
                        tv_minute.setText(split[1]);
                        tv_s.setText(split[2]);
                    }

                    public void onFinish() {
                        llDetailPrice.setVisibility(View.GONE);
                        tvDetailStop.setVisibility(View.VISIBLE);
                        llDetailBottom.setVisibility(View.GONE);
                    }
                }.start();
            } else {
                llDetailPrice.setVisibility(View.GONE);
                tvDetailStop.setVisibility(View.VISIBLE);
                llDetailBottom.setVisibility(View.GONE);
            }

            if (bean.getIs_pur() == 1) {
                tvDetailPay.setText("已购买");
                tvDetailPay.setEnabled(false);
            } else {

            }
        }


    }
}
