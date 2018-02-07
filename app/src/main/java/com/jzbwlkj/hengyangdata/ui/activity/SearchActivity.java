package com.jzbwlkj.hengyangdata.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.jzbwlkj.hengyangdata.ui.adapter.SearchHistoryAdapter;
import com.jzbwlkj.hengyangdata.ui.bean.History;
import com.jzbwlkj.hengyangdata.ui.bean.HistoryDao;
import com.jzbwlkj.hengyangdata.ui.bean.MessageListBean;
import com.jzbwlkj.hengyangdata.utils.CommonApi;
import com.jzbwlkj.hengyangdata.utils.IMEUtils;
import com.jzbwlkj.hengyangdata.utils.LogUtils;
import com.jzbwlkj.hengyangdata.utils.ToastUtils;
import com.jzbwlkj.hengyangdata.view.MyGridView;
import com.jzbwlkj.hengyangdata.view.TagColor;
import com.jzbwlkj.hengyangdata.view.TagGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements TagGroup.OnTagClickListener {

    @BindView(R.id.img_home_back)
    ImageView imgHomeBack;
    @BindView(R.id.ll_home_search)
    LinearLayout llHomeSearch;
    @BindView(R.id.img_home_chat)
    TextView imgHomeSouSuo;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tag_group)
    TagGroup mTagGroup;
    @BindView(R.id.et_key_word)
    EditText etKeyWord;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;

    private List<String> mList = new ArrayList<>();
    private HistoryDao historyDao;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        historyDao = BaseApp.getDaoInstant().getHistoryDao();
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mTagGroup.setOnTagClickListener(this);
        etKeyWord.setFocusable(true);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                IMEUtils.toggleSoftInput(SearchActivity.this);
            }
        }, 100);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistory();
    }

    private void getHistory() {
        mList.clear();
        List<History> list = historyDao.queryBuilder().list();
        if (CommonApi.isEmpty(list)) {
            llDelete.setVisibility(View.GONE);
        } else {
            llDelete.setVisibility(View.VISIBLE);
            for (int i = 0; i < list.size(); i++) {
                mList.add(list.get(i).getKey());
            }
        }
        List<TagColor> colors = TagColor.getGrayColors(mList.size());
        mTagGroup.setTags(colors, (String[]) mList.toArray(new String[mList.size()]));
    }

    @OnClick({R.id.img_home_back, R.id.img_home_chat, R.id.img_search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_home_back:
                finish();
                break;
            case R.id.img_home_chat:
                String s = etKeyWord.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showToast("请输入关键字");
                    return;
                }

                History history = new History();
                history.setKey(s);
                historyDao.insertOrReplace(history);
                getHistory();
                search(s);
                break;
            case R.id.img_search_delete:
                deleteHistoryDialog();
                break;
        }
    }

    @Override
    public void onTagClick(String tag) {
        LogUtils.e("tag：" + tag);
        search(tag);
    }

    private void deleteHistoryDialog() {

        View view = View.inflate(this,R.layout.dialog_common,null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvMessage.setText("是否清空搜索历史?");
        final Dialog dialog= new Dialog(this,R.style.wx_dialog);
        dialog.setContentView(view);
        dialog.show();

        view.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyDao.deleteAll();
                getHistory();
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
     * 搜索
     *
     * @param key
     */
    private void search(String key) {
        RetrofitClient.getInstance().createApi().search(key)
                .compose(RxUtils.<HttpResult<List<MessageListBean>>>io_main())
                .subscribe(new BaseObjObserver<List<MessageListBean>>(this) {
                    @Override
                    protected void onHandleSuccess(List<MessageListBean> listBeen) {
                        if (CommonApi.isEmpty(listBeen)) {
                            ToastUtils.showToast("没有搜索到相关信息哦");
                            return;
                        }
                        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                        intent.putExtra("list", (ArrayList) listBeen);
                        startActivity(intent);
                    }
                });
    }
}
