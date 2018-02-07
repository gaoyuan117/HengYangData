package com.jzbwlkj.hengyangdata.ui.bean;

/**
 * Created by gaoyuan on 2018/1/8.
 */

public class TimerItem {
    //其他属性
    public String name;
    //倒计时长，单位毫秒
    public long expirationTime;
    public TimerItem(String name, long expirationTime) {
        this.name = name;
        this.expirationTime = expirationTime;
    }

}
