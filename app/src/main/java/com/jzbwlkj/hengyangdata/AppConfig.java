package com.jzbwlkj.hengyangdata;

import android.graphics.Color;

import com.jzbwlkj.hengyangdata.utils.AppUtils;
import com.jzbwlkj.hengyangdata.utils.FileUtils;

/**
 * Created by gaoyuan on 2018/1/7.
 */

public class AppConfig {

    public static final String BASE_URL = "http://hengyang.jzbwlkj.com/";
    public static String ALI = "1";
    public static String WX = "2";
    public static int REQUEST_CAMERA = 0x1001;
    public static int REQUEST_IMAGE = 0x1000;
    public static String PATH_DATA = FileUtils.createRootPath(BaseApp.getsInstance()) + "/cache";
    public static String PATH_EPUB = PATH_DATA + "/epub";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";

    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };
}
