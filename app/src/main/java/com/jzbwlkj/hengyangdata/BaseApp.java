package com.jzbwlkj.hengyangdata;

import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.jzbwlkj.hengyangdata.ui.bean.DaoMaster;
import com.jzbwlkj.hengyangdata.ui.bean.DaoSession;
import com.jzbwlkj.hengyangdata.ui.bean.UserBean;
import com.jzbwlkj.hengyangdata.utils.SharedPreferencesUtil;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by gaoyuan on 2018/1/7.
 */

public class BaseApp extends MultiDexApplication {

    private static BaseApp sInstance;
    public static String token;
    public static String phone;
    public static UserBean userBean;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        MultiDex.install(this);
        sInstance = this;

        SharedPreferencesUtil.init(this, "data", MODE_PRIVATE);

        String phone = SharedPreferencesUtil.getInstance().getString("phone");
        if (!TextUtils.isEmpty(phone)) {
            Set<String> strings = new HashSet<>();
            strings.add(phone);
            JPushInterface.setAliasAndTags(this, phone, strings, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {

                }
            });
        }

        setupDatabase();
    }

    public static BaseApp getsInstance() {
        return sInstance;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "GreenDao.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
