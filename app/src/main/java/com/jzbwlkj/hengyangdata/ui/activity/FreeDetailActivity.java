package com.jzbwlkj.hengyangdata.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.jzbwlkj.hengyangdata.R;
import com.jzbwlkj.hengyangdata.base.BaseActivity;
import com.jzbwlkj.hengyangdata.retrofit.BaseObjObserver;
import com.jzbwlkj.hengyangdata.retrofit.HttpResult;
import com.jzbwlkj.hengyangdata.retrofit.RetrofitClient;
import com.jzbwlkj.hengyangdata.retrofit.RxUtils;
import com.jzbwlkj.hengyangdata.ui.bean.SlideDetailBean;

import butterknife.BindView;

public class FreeDetailActivity extends BaseActivity {

    @BindView(R.id.tv_free_detail_title)
    TextView tvFreeDetailTitle;
    @BindView(R.id.tv_free_detail_time)
    TextView tvFreeDetailTime;
    @BindView(R.id.webview)
    WebView webview;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_free_detail;
    }

    @Override
    public void initView() {
        setCenterTitle("信息详情");
    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra("id");
        freeDetail();
    }

    @Override
    public void configViews() {
//        WebSettings settings = webview.getSettings();
//        // 设置WebView支持JavaScript
//        settings.setJavaScriptEnabled(true);
//        //支持自动适配
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(true);  //支持放大缩小
//        settings.setBuiltInZoomControls(true); //显示缩放按钮
//        settings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
//        settings.setAllowFileAccess(true); // 允许访问文件
//        settings.setSaveFormData(true);
//        settings.setGeolocationEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);/// 支持通过JS打开新窗口
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    private void freeDetail() {
        RetrofitClient.getInstance().createApi().slideDetail(id)
                .compose(RxUtils.<HttpResult<SlideDetailBean>>io_main())
                .subscribe(new BaseObjObserver<SlideDetailBean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(SlideDetailBean slideDetailBean) {
                        setData(slideDetailBean);
                    }
                });
    }

    private void setData(SlideDetailBean slideDetailBean) {
        tvFreeDetailTitle.setText(slideDetailBean.getName());
        tvFreeDetailTime.setText(slideDetailBean.getUptime());

        String linkCss = "<style type=\"text/css\"> " +
                "img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "</style>";
        String html = "<html><header>" + linkCss + "</header>" + slideDetailBean.getContent() + "</body></html>";
        webview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}
